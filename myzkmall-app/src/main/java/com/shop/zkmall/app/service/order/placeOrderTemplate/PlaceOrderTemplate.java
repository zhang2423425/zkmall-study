/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order.placeOrderTemplate;

import com.shop.zkmall.app.dao.order.CereShopOrderDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.OrderProductAttribute;
import com.shop.zkmall.app.page.order.PayUrl;
import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.order.OrderProductParam;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.app.pay.alipay.service.AliPayService;
import com.shop.zkmall.app.pay.weixin.service.WxPayService;
import com.shop.zkmall.app.service.buyer.CereBuyerReceiveService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.cart.CereShopCartService;
import com.shop.zkmall.app.service.order.CereOrderParentService;
import com.shop.zkmall.app.service.order.CereOrderProductAttributeService;
import com.shop.zkmall.app.service.order.CereOrderProductService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.app.utils.ImageUtil;
import com.shop.zkmall.app.utils.RedisUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.ParamEnum;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.order.CereOrderParent;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
@RequiredArgsConstructor
public abstract class PlaceOrderTemplate {

    protected final CereStockService cereStockService;

    protected final CereProductSkuService cereProductSkuService;

    protected final WxPayService wxPayService;

    protected final AliPayService aliPayService;

    protected final CereOrderParentService cereOrderParentService;

    protected final CereOrderProductService cereOrderProductService;

    protected final CereShopOrderDAO cereShopOrderDAO;

    protected final CereRedisKeyService cereRedisKeyService;

    protected final CereOrderProductAttributeService cereOrderProductAttributeService;

    protected final CereBuyerReceiveService cereBuyerReceiveService;

    protected final CereShopCartService cereShopCartService;

    protected final CereBuyerUserService cereBuyerUserService;

    protected final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    protected final RedisUtil redisUtil;

    /**
     * 定义下单模板方法 流程如下
     */
    public final PayUrl placeOrder(OrderParam param, CereBuyerUser user, String ip, String time) throws Exception {

        check(param);
        Long buyerUserId = user.getBuyerUserId();
        //每种sku的详细信息
        Map<Long, ProductSku> paramSkuMap = getBuySkuMap(param);

        AtomicBoolean flag = new AtomicBoolean(false);

        AtomicReference<BigDecimal> skuDiscountPrice = new AtomicReference<>(BigDecimal.ZERO);
        //将数据库查出的和参数合并，检查库存
        Map<Long, CartSku> cartSkuMap = getLongCartSkuMap(param, user, paramSkuMap,flag,skuDiscountPrice,new HashMap<>());

        if(flag.get()){
            throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
        }
        //每个店铺的商品金额
        Map<Long,BigDecimal> shopPriceMap=new HashMap<>();
        //计算订单总金额
        BigDecimal totalPrice = calOrderPrice(cartSkuMap, paramSkuMap, shopPriceMap);
        //运费
        BigDecimal logisticPrice = calLogisticPrice(param);
        //减去优惠金额
        totalPrice.subtract(skuDiscountPrice.get());
        param.setDiscountPrice(BigDecimal.ZERO);

        totalPrice= totalPrice.add(logisticPrice);

        BigDecimal payPrice=totalPrice;
        param.setPrice(payPrice);
        if(EmptyUtils.isEmptyBigdecimal(payPrice)){
            //支付金额不为0
            throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
        }
        //分摊运费
        calcLogistics(param,cartSkuMap);

        //新增父订单
        CereOrderParent parent = createParentOrder(payPrice, time, logisticPrice, totalPrice);

        PayUrl payUrl = new PayUrl();
        payUrl.setMoney(parent.getPrice());
        payUrl.setOrderId(parent.getParentId());
        //生成二维码
        generatePayUrl(param,user.getWechatOpenId(),parent.getParentFormid(),ip,payUrl);
        //遍历商品数据，新增子订单,没检查库存,扣了数据库库存
        createShopOrders(parent.getParentId(),param,user,time,
                cartSkuMap,shopPriceMap,
                new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>(),payUrl);
        //商品下单人数
        List<Long> productIdList = cartSkuMap.values().stream().map(CartSku::getProductId).collect(Collectors.toList());
        List<Long> updateProductIdList=new ArrayList<>();
        for (Long productId: productIdList){
            int updateCount = cereShopOrderDAO.saveBuyerProductRecord(buyerUserId, productId);
            if (updateCount > 0) {
                updateProductIdList.add(productId);
            }
        }
        if(updateProductIdList.size()>0){
            cereSkuMemberRealInfoDAO.increSalesUserCount(updateProductIdList);
        }

        return payUrl;
    }

    /**
     * 计算运费
     */
    private void calcLogistics(OrderParam param, Map<Long, CartSku> skuMap) {
        for (OrderProductParam shop : param.getShops()) {
            if (shop.getDistribution() != null
                    && shop.getDistribution().getDistributionPrice() != null
                    && shop.getDistribution().getDistributionPrice().compareTo(BigDecimal.ZERO) != 0) {
                Integer chargeType = ParamEnum.getByName(shop.getDistribution().getDistributionName());
                int totalNum = shop.getSkus().stream().mapToInt(ProductSku::getNumber).sum();
                BigDecimal totalWeight = shop.getSkus().stream().map(sku -> {
                    CartSku cartSku = skuMap.get(sku.getSkuId());
                    if (cartSku != null && cartSku.getWeight() != null) {
                        return cartSku.getWeight().multiply(new BigDecimal(cartSku.getNumber()));
                    }
                    return BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal remainLogisticsPrice = shop.getDistribution().getDistributionPrice();

                if (ParamEnum.CHARGE_TYPE_NUMBER.getCode().equals(chargeType)) {
                    for (int i = 0; i < shop.getSkus().size(); i++) {
                        ProductSku sku = shop.getSkus().get(i);
                        CartSku cartSku = skuMap.get(sku.getSkuId());
                        if (cartSku == null) {
                            continue;
                        }
                        if (sku.getNumber() == totalNum) {
                            cartSku.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                            break;
                        }
                        double percent = sku.getNumber() / 1.0 / totalNum;
                        BigDecimal curLogisticsPrice = shop.getDistribution().getDistributionPrice().multiply(new BigDecimal(percent))
                                .setScale(2, RoundingMode.HALF_UP);

                        // 最后一件的时候，可能计算有偏差，直接将剩下的运费都给到最后一件
                        if (i == shop.getSkus().size() - 1) {
                            curLogisticsPrice = remainLogisticsPrice;
                        } else if (curLogisticsPrice.compareTo(remainLogisticsPrice) > 0) {
                            curLogisticsPrice = remainLogisticsPrice;
                        }
                        cartSku.setLogisticsPrice(curLogisticsPrice);
                        remainLogisticsPrice = remainLogisticsPrice.subtract(curLogisticsPrice);
                    }
                } else if (ParamEnum.CHARGE_TYPE_WEIGHT.getCode().equals(chargeType)) {
                    for (int i = 0; i < shop.getSkus().size(); i++) {
                        ProductSku sku = shop.getSkus().get(i);
                        CartSku cartSku = skuMap.get(sku.getSkuId());
                        if (cartSku == null) {
                            continue;
                        }
                        if (cartSku.getWeight() == null || cartSku.getWeight().compareTo(BigDecimal.ZERO) == 0) {
                            cartSku.setLogisticsPrice(BigDecimal.ZERO);
                            continue;
                        }
                        BigDecimal skuTotalWeight = cartSku.getWeight().multiply(new BigDecimal(cartSku.getNumber()));
                        if (skuTotalWeight.compareTo(totalWeight) == 0) {
                            cartSku.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                            break;
                        }
                        BigDecimal percent = skuTotalWeight.divide(totalWeight, 6, RoundingMode.HALF_UP);
                        BigDecimal curLogisticsPrice = shop.getDistribution().getDistributionPrice().multiply(percent)
                                .setScale(2, RoundingMode.HALF_UP);

                        // 最后一件的时候，可能计算有偏差，直接将剩下的运费都给到最后一件
                        if (i == shop.getSkus().size() - 1) {
                            curLogisticsPrice = remainLogisticsPrice;
                        } else if (curLogisticsPrice.compareTo(remainLogisticsPrice) > 0) {
                            curLogisticsPrice = remainLogisticsPrice;
                        }
                        cartSku.setLogisticsPrice(curLogisticsPrice);
                        remainLogisticsPrice = remainLogisticsPrice.subtract(curLogisticsPrice);
                    }
                }
            } else {
                for (ProductSku sku : shop.getSkus()) {
                    Long skuId = sku.getSkuId();
                    if (skuMap.get(skuId) != null) {
                        skuMap.get(skuId).setLogisticsPrice(BigDecimal.ZERO);
                    }
                }
            }
        }
        // 防止有些数据没有设置
        for (CartSku sku : skuMap.values()) {
            if (sku.getLogisticsPrice() == null) {
                sku.setLogisticsPrice(BigDecimal.ZERO);
            }
        }
    }

    /**
     * 校验数据抽象方法 不同下单方式校验不同
     *
     * @param param /
     */
    protected abstract void check(OrderParam param);

    /**
     * 创建子订单
     *
     * @param parentId    父订单id
     * @param param       结算请求实体
     * @param user        客户
     * @param time        时间
     * @param map         skuId与sku实体组成map
     * @param discountMap 店铺优惠券数据
     * @param payUrl      支付信息
     * @throws Exception /
     */
    protected abstract void createShopOrders(Long parentId, OrderParam param, CereBuyerUser user, String time, Map<Long, CartSku> map,
                                             Map<Long, BigDecimal> shopPriceMap, Map<Long, Object> discountMap,
                                             Map<Long, BigDecimal> shopDeductCreditAmountMap, Map<Long, BigDecimal> platformCouponDiscountMap,
                                             Map<Long, BigDecimal> pricingDiscountPriceMap, PayUrl payUrl) throws Exception;


    /**
     * 组装 sku信息map
     */
    protected abstract Map<Long, CartSku> getLongCartSkuMap(OrderParam param, CereBuyerUser user, Map<Long, ProductSku> numberMap,
                                                            AtomicBoolean flag, AtomicReference<BigDecimal> skuDiscountPrice,
                                                            Map<Long, BigDecimal> shopDiscountPriceMap);

    /**
     * 计算运费
     */
    private BigDecimal calLogisticPrice(OrderParam param) {
        BigDecimal totalLogisticsPrice = BigDecimal.ZERO;
        for (OrderProductParam shop : param.getShops()) {
            BigDecimal logisticsPrice = shop.getDistribution().getDistributionPrice();
            totalLogisticsPrice = totalLogisticsPrice.add(logisticsPrice);
        }
        return totalLogisticsPrice;
    }


    /**
     * 获取购买 sku数量
     *
     * @param param /
     * @return sku数量map
     */
    private Map<Long, ProductSku> getBuySkuMap(OrderParam param) {
        Map<Long, ProductSku> numberMap = new HashMap<>();
        param.getShops().forEach(shop -> {
            if (!EmptyUtils.isEmpty(shop.getSkus())) {
                shop.getSkus().forEach(sku -> {
                    numberMap.put(sku.getSkuId(), sku);
                });
            }
        });
        return numberMap;
    }


    /**
     * 计算订单总价格
     */
    private BigDecimal calOrderPrice(Map<Long, CartSku> map, Map<Long, ProductSku> numberMap, Map<Long, BigDecimal> shopPriceMap) {
        BigDecimal total = BigDecimal.ZERO;
        for (Long skuId : map.keySet()) {
            CartSku sku = map.get(skuId);
            Long shopId = sku.getShopId();
            BigDecimal num = new BigDecimal(numberMap.get(skuId).getNumber());
            BigDecimal price = sku.getPrice().multiply(num);
            total = total.add(price);
            shopPriceMap.put(shopId, shopPriceMap.getOrDefault(shopId, BigDecimal.ZERO).add(price));
        }
        return total;
    }


    private CereOrderParent createParentOrder(BigDecimal payPrice, String time, BigDecimal logisticPrice, BigDecimal orderPrice) {
        CereOrderParent parent = new CereOrderParent();
        parent.setCreateTime(time);
        parent.setLogisticsPrice(logisticPrice);
        //订单总价
        parent.setOrderPrice(orderPrice);
        parent.setPrice(payPrice);
        parent.setParentFormid(RandomStringUtil.getRandomCode(15, 0));
        cereOrderParentService.insert(parent);
        return parent;
    }

    /**
     * 根据不同支付类型生成不同的支付url
     */
    private void generatePayUrl(OrderParam orderParam, String openId, String parentFormId, String ip, PayUrl payUrl) throws Exception {
        Integer paymentMode = orderParam.getPaymentMode();
        Integer subPaymentMode = orderParam.getSubPaymentMode();
        if (paymentMode != null && paymentMode.equals(IntegerEnum.ORDER_PAY_WX.getCode())
                && subPaymentMode != null && subPaymentMode.equals(IntegerEnum.ORDER_SUB_PAYMENT_MODE_H5.getCode())) {
            // h5支付 前端重新调用统一下单
        } else if (paymentMode != null && paymentMode.equals(IntegerEnum.ORDER_PAY_WX.getCode())
                && subPaymentMode != null && subPaymentMode.equals(IntegerEnum.ORDER_SUB_PAYMENT_MODE_XCX.getCode())) {
            // 小程序支付 前端重新调用统一下单
        } else if (paymentMode != null && Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(paymentMode)) {
            // 支付宝支付 生成收款码
            String orderFormId = parentFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "PC";
            String code_url = aliPayService.getOrderCollectionCode(orderFormId, payUrl.getMoney(),
                    ip, null);
            if (!EmptyUtils.isEmpty(code_url)) {
                payUrl.setUrl(ImageUtil.genQrCodeBase64ByUrl(code_url));
            }
        } else {
            //生成收款码
            String formid = parentFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "XCX";
            String code_url = wxPayService.getOrderCollectionCode(formid, payUrl.getMoney(),
                    ip, WxPayEnum.PAY_TYPE_NATIVE.getCode());
            if (!EmptyUtils.isEmpty(code_url)) {
                payUrl.setUrl(ImageUtil.genQrCodeBase64ByUrl(code_url));
            }
        }
    }

    /**
     * 新增订单商品数据
     */
    protected void addOrderProduct(List<CartSku> skus, Long orderId, Long shopId,
                                   Map<Long, CartSku> map, List<OrderProductAttribute> attributes) {
        if (!EmptyUtils.isEmpty(skus)) {
            for (CartSku sku : skus) {
                if (sku.getShopId().equals(shopId)) {
                    CereOrderProduct orderProduct = new CereOrderProduct();
                    BeanUtils.copyProperties(sku, orderProduct);

                    orderProduct.setOrderId(orderId);
                    orderProduct.setProductPrice(sku.getPrice());
                    orderProduct.setActivityType(IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode());
                    //插入订单商品明细数据
                    cereOrderProductService.insert(orderProduct);
                    //更新库存数量
                    int stockNumber = map.get(sku.getSkuId()).getStockNumber();
                    sku.setStockNumber(stockNumber - sku.getNumber());
                    //根据规格id查询规格属性数据
                    if (!EmptyUtils.isEmpty(attributes)) {
                        attributes.forEach(a -> {
                            if (sku.getSkuId().equals(a.getSkuId())) {
                                a.setOrderProductId(orderProduct.getOrderProductId());
                            }
                        });
                    }
                }
            }
        }
    }

}
