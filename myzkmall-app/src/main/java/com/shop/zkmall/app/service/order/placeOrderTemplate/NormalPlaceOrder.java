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
import com.shop.zkmall.app.page.shop.Shop;
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
import com.shop.zkmall.app.utils.RedisUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.ParamEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NormalPlaceOrder extends PlaceOrderTemplate{
    //未使用@RequiredRegsConstructor
    public NormalPlaceOrder(CereStockService cereStockService, CereProductSkuService cereProductSkuService,
                            WxPayService wxPayService, AliPayService aliPayService, CereOrderParentService
                                    cereOrderParentService, CereOrderProductService cereOrderProductService, CereShopOrderDAO cereShopOrderDAO, CereRedisKeyService cereRedisKeyService, CereOrderProductAttributeService cereOrderProductAttributeService, CereBuyerReceiveService cereBuyerReceiveService, CereShopCartService cereShopCartService, CereBuyerUserService cereBuyerUserService, CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO, RedisUtil redisUtil) {
        super(cereStockService, cereProductSkuService, wxPayService, aliPayService,
                cereOrderParentService, cereOrderProductService, cereShopOrderDAO,
                cereRedisKeyService, cereOrderProductAttributeService, cereBuyerReceiveService, cereShopCartService,
                cereBuyerUserService, cereSkuMemberRealInfoDAO, redisUtil);
    }

    @Override
    protected void check(OrderParam param) {
        if (EmptyUtils.isEmpty(param.getShops())) {
            throw new CoBusinessException(CoReturnFormat.PARAM_INVALID);
        }
    }

    @Override
    protected void createShopOrders(Long parentId, OrderParam param, CereBuyerUser user,
                                    String time, Map<Long, CartSku> map, Map<Long, BigDecimal> shopPriceMap,
                                    Map<Long, Object> discountMap, Map<Long, BigDecimal> shopDeductCreditAmountMap,
                                    Map<Long, BigDecimal> platformCouponDiscountMap, Map<Long, BigDecimal> pricingDiscountPriceMap,
                                    PayUrl payUrl) throws Exception {
        List<CartSku> skus =null;

        if (!EmptyUtils.isEmpty(map)){
            skus=map.keySet().stream().map(map::get).collect(Collectors.toList());
        }
        //查询产品属性
        List<OrderProductAttribute> attributes = cereOrderProductAttributeService.findBySkus(skus);
        //查询收货地址
        CereBuyerReceive receive = cereBuyerReceiveService.findById(param.getReceiveId());
        if (receive != null && skus != null) {
            for (OrderProductParam shop : param.getShops()) {
                //子订单
                CereShopOrder order = new CereShopOrder();
                order.setShopId(shop.getShopId());
                order.setParentId(parentId);
                order.setBuyerUserId(user.getBuyerUserId());
                order.setCreateTime(time);
                order.setUpdateTime(time);
                order.setCouponId(param.getCouponId());
                order.setReceiveName(receive.getReceiveName());
                order.setReceivePhone(receive.getReceivePhone());
                order.setReceiveAdress(receive.getReceiveAdress());
                order.setAddress(receive.getAddress());
                order.setCustomerName(user.getWechatName());
                order.setCustomerPhone(user.getPhone());
                order.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                order.setRemark(shop.getRemark());
                order.setLogisticsId(shop.getDistribution().getLogisticsId());
                order.setState(IntegerEnum.ORDER_STAY_PAY.getCode());
                order.setOrderFormid(RandomStringUtil.getRandomCode(15, 0));
                Long seckillId = shop.getSkus().stream().filter(sku -> sku.getPlatformSeckillId() != null).findAny()
                        .map(ProductSku::getPlatformSeckillId).orElse(null);
                Long discountId = shop.getSkus().stream().filter(sku -> sku.getPlatformDiscountId() != null).findAny()
                        .map(ProductSku::getPlatformDiscountId).orElse(null);

                order.setSeckillId(seckillId);
                order.setDiscountId(discountId);
                order.setNeedInvoice(param.isNeedInvoice());
                BigDecimal discountPrice = BigDecimal.ZERO;

                order.setDiscountPrice(discountPrice);
                if(!EmptyUtils.isEmpty(skus)){
                    order.setOrderPrice(shopPriceMap.get(shop.getShopId()).setScale(2, RoundingMode.HALF_UP));
                    //订单支付金额=订单总金额+运费-计算出来的优惠金额
                    order.setPrice(order.getOrderPrice().add(order.getLogisticsPrice()).subtract(order.getDiscountPrice()).setScale(2, RoundingMode.HALF_UP));
                    //设置定价捆绑优惠金额
                    order.setPricingPrice(pricingDiscountPriceMap.getOrDefault(shop.getShopId(), BigDecimal.ZERO));
                    order.setPaymentState(IntegerEnum.ORDER_PAY_STAY.getCode());
                    order.setPaymentMode(param.getPaymentMode());
                    order.setBuyerUserId(user.getBuyerUserId());
                    cereShopOrderDAO.insert(order);

                    addOrderProduct(skus,order.getOrderId(), shop.getShopId(),map,attributes);
                    redisUtil.addDelayQueue(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode(),order.getOrderId(), TimeUtils.getMinuteAfter(30));

                }

            }
            //批量更新库存数量
            //cereProductSkuService.updateBatch(skus);
            List<CartSku> dealedSkus = new ArrayList<>();
            if (!EmptyUtils.isEmpty(skus)) {
                for (CartSku sku : skus) {
                    try {
                        cereStockService.reduceStock(sku.getActivityType(), sku.getActivityId(), sku.getProductId(), sku.getSkuId(), sku.getNumber(), sku.getIfOversold());
                        dealedSkus.add(sku);
                    } catch (CoBusinessException cbe) {
                        log.error("reduceStock failed: activityType = {}, activityId = {}, productId = {}, skuId = {}, buyNumber = {}, ifOversold = {},",
                                sku.getActivityType(), sku.getActivityId(), sku.getProductId(), sku.getSkuId(), sku.getNumber(), sku.getIfOversold(), cbe);
                        for (CartSku dSku : dealedSkus) {
                            cereStockService.rollbackStock(dSku.getActivityType(), dSku.getActivityId(), dSku.getProductId(), dSku.getSkuId(), dSku.getNumber());
                        }
                    }
                }
            }
            //插入规格属性数据
            if (!EmptyUtils.isEmpty(attributes)) {
                cereOrderProductAttributeService.insertBatch(attributes);
            }

        }
        if (!EmptyUtils.isEmpty(skus) && ParamEnum.CART_SETTLEMENT.getCode().equals(param.getType())) {
            //删除购物车商品
            cereShopCartService.deleteSkus(skus, user.getBuyerUserId());
        }


    }

    @Override
    protected Map<Long, CartSku> getLongCartSkuMap(OrderParam param, CereBuyerUser user,
                                                   Map<Long, ProductSku> numberMap, AtomicBoolean flag,
                                                   AtomicReference<BigDecimal> skuDiscountPrice,
                                                    Map<Long, BigDecimal> shopDiscountPriceMap) {

        Map<Long,CartSku> map=new HashMap<>();

        //遍历每一件商品
        for (OrderProductParam shop : param.getShops()) {
            shopDiscountPriceMap.put(shop.getShopId(),BigDecimal.ZERO);

            if(!EmptyUtils.isEmpty(shop.getSkus())){

                List<CartSku> productSkus = cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                if (!EmptyUtils.isEmpty(productSkus)){
                    productSkus.forEach(sku->{
                        //参考sku
                        ProductSku paramSku = numberMap.get(sku.getSkuId());
                        //设置数量
                        sku.setNumber(paramSku.getNumber());
                        sku.setShopId(shop.getShopId());
                        //设置相关活动id
                        sku.setPlatformSeckillId(paramSku.getPlatformSeckillId());
                        sku.setPlatformDiscountId(paramSku.getPlatformDiscountId());
                        sku.setShopSeckillId(paramSku.getShopSeckillId());
                        sku.setShopDiscountId(paramSku.getShopDiscountId());
                        sku.setPriceId(paramSku.getPriceId());
                        sku.setComposeId(paramSku.getComposeId());
                        sku.setSceneId(paramSku.getSceneId());
                        sku.setUseMember(paramSku.isUseMember());
                        sku.setBuyerCouponId(paramSku.getBuyerCouponId());
                        sku.setBuyerShopCouponId(paramSku.getBuyerShopCouponId());
                        map.put(sku.getSkuId(),sku);
                    });

                }

                shop.getSkus().forEach(sku->{
                    //不允许超卖
                    if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                        int stockNumber = map.get(sku.getSkuId()).getStockNumber();
                        if (sku.getNumber() > stockNumber) {
                            flag.set(true);
                        }
                    }

                });

                for (CartSku sku : map.values()) {
                    Long priceId = sku.getPriceId();
                    if (priceId==null||priceId==0){
                        sku.setActualPrice(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                    }
                }
            }
        }
        skuDiscountPrice.set(shopDiscountPriceMap.values().stream().reduce(BigDecimal.ZERO,BigDecimal::add));
        return map;
    }
}