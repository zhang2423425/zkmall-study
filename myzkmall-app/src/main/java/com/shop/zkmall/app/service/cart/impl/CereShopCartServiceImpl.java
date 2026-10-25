/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.cart.impl;

import com.shop.zkmall.app.dao.cart.CereShopCartDAO;
import com.shop.zkmall.app.dao.collect.CereBuyerCollectDAO;
import com.shop.zkmall.app.dao.product.CereProductStatsByDayDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.cart.ShopCart;
import com.shop.zkmall.app.page.product.ProductStockInfo;
import com.shop.zkmall.app.param.cart.*;
import com.shop.zkmall.app.service.cart.CereCartAttributeService;
import com.shop.zkmall.app.service.cart.CereShopCartService;
import com.shop.zkmall.app.service.log.CerePlatformLogService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.cart.CereCartAttribute;
import com.shop.zkmall.commons.domain.cart.CereShopCart;
import com.shop.zkmall.commons.domain.product.CereProductStatsByDay;
import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopCartServiceImpl implements CereShopCartService {

    private final CereShopCartDAO cereShopCartDAO;

    private final CereCartAttributeService cereCartAttributeService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereProductSkuService cereProductSkuService;

    private final CereStockService cereStockService;

    private final CereProductStatsByDayDAO cereProductStatsByDayDAO;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    private final CereBuyerCollectDAO cereBuyerCollectDAO;

    @Value("${querySkuRealInfo}")
    private boolean querySkuRealInfo;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void addCart(AddCartParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //查询购物车中是否已存在该商品
        CereShopCart shopCart = cereShopCartDAO.findShopCart(user.getBuyerUserId(), param.getSkuId());
        if (shopCart != null) {
            //在原有数据基础上加上数量
            shopCart.setNumber(shopCart.getNumber() + param.getNumber());
            shopCart.setUpdateTime(time);
            cereShopCartDAO.updateByPrimaryKeySelective(shopCart);
            //更新日度统计
            CereProductStatsByDay stats = new CereProductStatsByDay();
            stats.setShopId(shopCart.getShopId());
            stats.setCreateDate(TimeUtils.today());
            stats.setProductId(shopCart.getProductId());
            stats.setAddCartCount(param.getNumber());
            stats.setVisitCount(0);
            stats.setSalesVolume(0);
            cereProductStatsByDayDAO.insertOrUpdate(stats);
        } else {
            //新增购物车商品数据
            shopCart = cereShopCartDAO.findSku(param.getSkuId());
            shopCart.setCreateTime(time);
            shopCart.setUpdateTime(time);
            shopCart.setBuyerUserId(user.getBuyerUserId());
            shopCart.setNumber(param.getNumber());
            cereShopCartDAO.insertOrUpdate(shopCart);
            //查询规格属性值数据同步到购物车商品规格明细表中
            List<CereCartAttribute> attributes = cereShopCartDAO.findAttributes(param.getSkuId());
            if (attributes != null) {
                if (shopCart.getCartId() == null) {
                    CereShopCart cart = cereShopCartDAO.findShopCart(user.getBuyerUserId(), param.getSkuId());
                    shopCart.setCartId(cart.getCartId());
                }
                CereShopCart finalShopCart = shopCart;
                attributes.forEach(a -> a.setCartId(finalShopCart.getCartId()));
                cereCartAttributeService.insertBatch(attributes);
            }
            //更新日度统计
            CereProductStatsByDay stats = new CereProductStatsByDay();
            stats.setShopId(shopCart.getShopId());
            stats.setCreateDate(TimeUtils.today());
            stats.setProductId(shopCart.getProductId());
            stats.setAddCartCount(param.getNumber());
            stats.setVisitCount(0);
            stats.setSalesVolume(0);
            cereProductStatsByDayDAO.insertOrUpdate(stats);
        }
        //新增转化数据
        /*CereShopConversion cereShopConversion=new CereShopConversion();
        cereShopConversion.setShopId(shopCart.getShopId());
        cereShopConversion.setCreateTime(time);
        cereShopConversion.setType(ParamEnum.CONVERSION_CART.getCode());
        cereShopConversionService.insert(cereShopConversion);*/
        //新增日志
        cerePlatformLogService.addLog(user, "购物车模块", "客户端操作", "加入购物车", user.getBuyerUserId(), time);
    }

//    private void checkActivity(ActivityData data, CereShopCart shopCart) {
//        if (!EmptyUtils.isEmpty(data.getShopDiscountId())){
//            //限时折扣活动校验,查询限时折扣活动数据
//            CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(data.getShopDiscountId());
//            if(cereShopDiscount!=null) {
//                if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopDiscount.getIfLimit())) {
//                    //如果限时折扣活动限购,校验数量
//                    if (shopCart.getNumber() > cereShopDiscount.getLimitNumber()) {
//                        throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
//                    }
//                }
//                if (IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())) {
//                    int surplusNumber = 0;
//                    ProductStockInfo productStockInfo = cereStockService.getActivitySkuStock(shopCart.getSkuId(), IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode(), cereShopDiscount.getShopDiscountId());
//                    if (productStockInfo != null) {
//                        surplusNumber = productStockInfo.getStockNumber();
//                    } else {
//                        surplusNumber = cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),shopCart.getSkuId());
//                    }
//                    //如果限量,查询缓存中活动商品仅剩数量
//                    /*if (EmptyUtils.isEmpty(stringRedisService.get("限时折扣活动商品仅剩件数" + cereShopDiscount.getShopDiscountId() + shopCart.getSkuId()))) {
//                        //如果没有,取数据库限量库存数据
//                        surplusNumber=cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),shopCart.getSkuId());
//                    }else {
//                        //如果有,取缓存
//                        surplusNumber = (int) stringRedisService.get("限时折扣活动商品仅剩件数" + cereShopDiscount.getShopDiscountId() + shopCart.getSkuId());
//                    }*/
//                    //判断数量是否大于当前购买数量
//                    if (shopCart.getNumber() > surplusNumber) {
//                        throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT);
//                    }
//                }
//            }
//        }
//    }

    @Override
    public List<ShopCart> getCart(CereBuyerUser user) {
        Long buyerUserId = user.getBuyerUserId();
        //查询当前用户购物车店铺数据
        List<ShopCart> shopCarts = cereShopCartDAO.findCartByUserId(buyerUserId);
        if (!EmptyUtils.isEmpty(shopCarts)) {
            if (querySkuRealInfo) {
                Long memberLevelId = 0L;
                List<CartSku> cartSkuList = cereSkuMemberRealInfoDAO.findCartSku(buyerUserId, memberLevelId);
                if (!cartSkuList.isEmpty()) {
                    List<Long> cartIdList = cartSkuList.stream().map(CartSku::getCartId).collect(Collectors.toList());
                    Map<Long, CereCartAttribute> attrMap = cereCartAttributeService.selectCartSkuValueGroup(cartIdList)
                            .stream().collect(Collectors.toMap(CereCartAttribute::getCartId, Function.identity()));
                    Map<Long, List<CartSku>> cartSkuMap = cartSkuList.stream().collect(groupingBy(CartSku::getShopId));

                    List<Long> productIdList = cartSkuList.stream().map(CartSku::getProductId).distinct().collect(Collectors.toList());
                    List<Sku> skuList = cereSkuMemberRealInfoDAO.findSkuListByProductIdList(productIdList, memberLevelId);
                    Map<Long, List<Sku>> productIdSkuMap = skuList.stream().collect(Collectors.groupingBy(Sku::getProductId));

                    //设置定价捆绑 组合捆绑 营销活动 会员价
                    setOtherActivityInfo(user, shopCarts, cartSkuMap);

                    for (ShopCart cart : shopCarts) {
                        Long shopId = cart.getShopId();
                        List<CartSku> cartList = cartSkuMap.get(shopId);
                        if (CollectionUtils.isNotEmpty(cartList)) {
                            for (CartSku sku : cartList) {
                                String skuValueGroupConcat = attrMap.getOrDefault(sku.getCartId(), new CereCartAttribute()).getSkuValue();
                                sku.setValues(EmptyUtils.getImages(skuValueGroupConcat));
                                //设置stockNumber
                                if (IntegerEnum.CUSTUM_STOCK_ACTIVITY_TYPE_LIST.contains(sku.getActivityType())) {
                                    ProductStockInfo stockInfo = cereStockService.getActivitySkuStock(sku.getSkuId(), sku.getActivityType(), sku.getActivityId());
                                    if (stockInfo != null) {
                                        sku.setStockNumber(stockInfo.getStockNumber());
                                    }
                                }

                                List<Sku> proSkuList = productIdSkuMap.get(sku.getProductId());
                                if (proSkuList != null) {
                                    Map<String, Sku> proSkuMap = new HashMap<>();
                                    for (Sku item : proSkuList) {
                                        //设置库存
                                        if (item.getSkuId().equals(sku.getSkuId())) {
                                            item.setStockNumber(sku.getStockNumber());
                                        } else if (IntegerEnum.CUSTUM_STOCK_ACTIVITY_TYPE_LIST.contains(item.getActivityType())) {
                                            ProductStockInfo stockInfo = cereStockService.getActivitySkuStock(item.getSkuId(), item.getActivityType(), item.getActivityId());
                                            if (stockInfo != null) {
                                                item.setStockNumber(stockInfo.getStockNumber());
                                            }
                                        }

                                        String key = item.getValueCodes();
                                        if (StringUtils.isBlank(key)) {
                                            key = "单款项";
                                        }
                                        proSkuMap.put(key, item);
                                    }
                                    sku.setMap(proSkuMap);
                                }

                                //setPlatformCoupons(user, sku, platformCouponMap);
                                //setShopCoupons(user, sku, shopCouponMap);
                            }
                            cart.setSkus(cartList);
                        } else {
                            cart.setSkus(Collections.emptyList());
                        }
                    }
                }
            } else {
                //查询商品明细数据
                shopCarts.forEach(shopCart -> {
                    //根据店铺id查询商品明细
                    List<CartSku> cartSkus = cereShopCartDAO.findProductByShopId(shopCart.getShopId(), buyerUserId);
                    List<Long> productIdList = cartSkus.stream().map(CartSku::getProductId).collect(Collectors.toList());
                    List<Long> collectedProductIdList = cereBuyerCollectDAO.selectCollectedProduct(buyerUserId, productIdList);
                    Map<Long, Map<String, Sku>> productIdSkuMap = new HashMap<>();
                    if (!EmptyUtils.isEmpty(cartSkus)) {
                        cartSkus.forEach(a -> a.setShopId(shopCart.getShopId()));
                        cartSkus.forEach(a -> a.setValues(EmptyUtils.getImages(a.getValue())));
                        cartSkus.forEach(a -> a.setIfCollect(collectedProductIdList.contains(a.getProductId()) ? 1 : 0));
                        for (CartSku sku : cartSkus) {
                            Map<String, Sku> skuMap = productIdSkuMap.get(sku.getProductId());
                            if (skuMap == null) {
                                skuMap = getSkuMap(sku.getProductId());
                                sku.setMap(skuMap);
                                productIdSkuMap.put(sku.getProductId(), skuMap);
                            }
                        }
                        shopCart.setSkus(cartSkus);
                    }
                });
            }
        }
        return shopCarts;
    }

    /**
     * 设置定价捆绑 组合捆绑 营销活动
     */
    private void setOtherActivityInfo(CereBuyerUser user,
                                      List<ShopCart> shopCarts,
                                      Map<Long, List<CartSku>> cartSkuMap) {
        //查询其他活动的id
        List<Long> productIdList = new ArrayList<>();
        List<Long> shopIdList = new ArrayList<>();
        List<CartSku> normalSkuList = new ArrayList<>();
        for (ShopCart cart : shopCarts) {
            Long shopId = cart.getShopId();
            List<CartSku> cartList = cartSkuMap.get(shopId);
            if (CollectionUtils.isNotEmpty(cartList)) {
                cartList.stream()
                        .filter(obj -> obj.getActivityType() == null
                                || IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(obj.getActivityType())
                                || IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode().equals(obj.getActivityType()))
                        .forEach(item -> {
                            shopIdList.add(item.getShopId());
                            productIdList.add(item.getProductId());
                            normalSkuList.add(item);
                        });
            }
        }

        //匹配组合捆绑
//        List<CartSku> skus = normalSkuList.stream().filter(obj -> obj.getActivityType() == null
//                        || IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(obj.getActivityType())
//                        || IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode().equals(obj.getActivityType()))
//                .toList();
        /*Map<Long, List<Long>> composeIdMap = cereComposeProductService.selectComposeIdListByProductIdList(skus.stream().map(CartSku::getProductId).collect(Collectors.toList()));
        for (CartSku sku : skus) {
            List<Long> composeIdList = composeIdMap.get(sku.getProductId());
            if (composeIdList != null) {
                sku.setComposeIdList(composeIdList);
                sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_COMPOSE.getCode());
            }
        }*/

        //匹配营销活动
        //由于刷新 cere_sku_member_real_info 的时候，无法精确营销活动，而营销活动优先级高于会员价，
        // 所以这里要把会员价的商品取出来，重新匹配看是否满足营销活动
//        skus = skus.stream().filter(obj -> obj.getActivityType() == null
//                        || IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(obj.getActivityType())
//                        || IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode().equals(obj.getActivityType()))
//                .toList();
//        setSceneInfo(skus, user);

        //会员价 在 cere_sku_member_real_info 中已经有了
    }

    private Map<String, Sku> getSkuMap(Long productId) {
        //查询该商品所有组合规格数据封装到map
        List<Sku> skus = cereProductSkuService.findSkuByProductId(productId);
        if (!EmptyUtils.isEmpty(skus)) {
            Map<String, Sku> map = new HashMap<>();
            skus.forEach(sku -> {
                //查询规格所有规格值拼接字符串
                String key = cereProductSkuService.findValueBySkuId(sku.getSkuId());
                if (EmptyUtils.isEmpty(key)) {
                    key = "单款项";
                }
                map.put(key, sku);
            });
            return map;
        }
        return new HashMap<>();
    }

    //    private void setActivityInfo(CartSku sku) {
//
//        BigDecimal num = new BigDecimal(sku.getNumber());
//
//        //拼团
//        if (sku.getActivityType() != null && IntegerEnum.ACTIVITY_TYPE_SHOP_GROUP.getCode().equals(sku.getActivityType())) {
//            ActivityData data = cereShopGroupWorkDetailService.findPriceBySkuId(sku.getSkuId());
//            if (data != null) {
//                sku.setPrice(data.getPrice());
//                sku.setTotal(data.getPrice().multiply(num));
//            }
//        }
//
//        //平台折扣
//        if (sku.getActivityType() != null && IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode().equals(sku.getActivityType())) {
//            CerePlatformDiscount platformDiscount = cerePlatformDiscountService.findById(sku.getPlatformDiscountId());
//            if (platformDiscount != null && IntegerEnum.ACTIVITY_START.getCode().equals(platformDiscount.getState())) {
//                sku.setPrice(sku.getOriginalPrice().multiply(platformDiscount.getDiscount()).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP));
//                sku.setTotal(sku.getPrice().multiply(num));
//            }
//        }
//
//        //商家折扣
//        if (sku.getActivityType() != null && IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode().equals(sku.getActivityType())) {
//            CereShopDiscount cereShopDiscount = cereShopDiscountService.findById(sku.getShopDiscountId());
//            if (cereShopDiscount != null && IntegerEnum.TOOL_HAND.getCode().equals(cereShopDiscount.getState())) {
//                ActivityData data = cereShopDiscountDetailService.findPriceBySkuId(sku.getSkuId());
//                if (data != null) {
//                    sku.setPrice(data.getPrice());
//                    sku.setTotal(sku.getPrice().multiply(num));
//                }
//            }
//        }
//    }

    //    private void setShopActivityInfo(List<CartSku> cartSkus, CereBuyerUser user) {
//        //定价捆绑
//        List<CartSku> skus = cartSkus.stream().filter(cartSku -> cartSku.getPriceId() != null && cartSku.getPriceId() > 0
//                && IntegerEnum.YES.getCode().equals(cartSku.getShelveState())).collect(Collectors.toList());
//        Map<Long, List<CartSku>> priceIdMap = skus.stream().collect(groupingBy(CartSku::getPriceId));
//        for (Map.Entry<Long, List<CartSku>> entry:priceIdMap.entrySet()) {
//            Long priceId = entry.getKey();
//            List<CerePriceRule> ruleList = cerePriceRuleService.findRules(priceId);
//            //按数量倒序
//            Collections.reverse(ruleList);
//
//            List<CartSku> priceSkuList = entry.getValue();
//            priceSkuList.sort(new Comparator<CartSku>() {
//                @Override
//                public int compare(CartSku o1, CartSku o2) {
//                    return o2.getNumber().compareTo(o1.getNumber());
//                }
//            });
//
//            int priceTotalNum = priceSkuList.stream().mapToInt(CartSku::getNumber).sum();
//            for (CerePriceRule rule:ruleList) {
//                if (priceTotalNum >= rule.getNumber()) {
//                    // 可以优惠多少次
//                    int multiple = priceTotalNum/rule.getNumber();
//                    int calNum = 0;
//                    for (CartSku sku:priceSkuList) {
//                        sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_PRICE.getCode());
//                        calNum += sku.getNumber();
//                        //说明达到匹配条件了
//                        if (calNum >= multiple * rule.getNumber()) {
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//        }
//
//        //组合捆绑 先筛选出没有参加活动，再根据商品id查询组合捆绑信息
//        skus = cartSkus.stream().filter(cartSku -> (cartSku.getActivityType() == null || cartSku.getActivityType().equals(IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode()))
//                    && IntegerEnum.YES.getCode().equals(cartSku.getShelveState())).collect(Collectors.toList());
//        Map<Long, List<Long>> composeIdMap = cereComposeProductService.selectComposeIdListByProductIdList(skus.stream().map(CartSku::getProductId).collect(Collectors.toList()));
//        for (CartSku sku:skus) {
//            List<Long> composeIdList = composeIdMap.get(sku.getProductId());
//            if (composeIdList != null) {
//                sku.setComposeIdList(composeIdList);
//                sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_COMPOSE.getCode());
//            }
//        }
//        /*Map<Long, List<CartSku>> composeIdMap = skus.stream().collect(Collectors.groupingBy(CartSku::getComposeId));
//        for (Map.Entry<Long, List<CartSku>> entry:composeIdMap.entrySet()) {
//            Long composeId = entry.getKey();
//            List<Long> productIdList = cereComposeProductService.selectByComposeId(composeId);
//            List<Long> skuProductIdList = entry.getValue().stream().map(CartSku::getProductId).collect(Collectors.toList());
//            List<Long> interProductIdList = (List<Long>) CollectionUtil.intersection(productIdList, skuProductIdList);
//            // 取交集之后，和组合活动的商品数相同，说明符合条件
//            if (interProductIdList.size() == productIdList.size()) {
//                Map<Long, Integer> productIdBuyNumMap = new HashMap<>();
//                for (CartSku sku:entry.getValue()) {
//                    int buyNum = productIdBuyNumMap.getOrDefault(sku.getProductId(), 0);
//                    productIdBuyNumMap.put(sku.getProductId(), buyNum + sku.getNumber());
//                    sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_COMPOSE.getCode());
//                }
//                //最小购买数量代表 组合次数
//                int minSize = Collections.min(productIdBuyNumMap.values());
//            }
//        }*/
//
//        //场景营销
//        skus = cartSkus.stream()
//                .filter(cartSku -> cartSku.getActivityType() == null || IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(cartSku.getActivityType()))
//                .collect(Collectors.toList());
//
//        if (CollectionUtil.isNotEmpty(skus)) {
//            setSceneInfo(skus, user);
//        }
//
//        //会员价
//        skus = cartSkus.stream()
//                .filter(cartSku -> cartSku.getActivityType() == null || IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(cartSku.getActivityType()))
//                .collect(Collectors.toList());
//        if (CollectionUtil.isNotEmpty(skus)) {
//            setMemberInfo(skus, user);
//        }
//    }

    //    private void setSceneInfo(List<CartSku> skus, CereBuyerUser user) {
//        if (CollectionUtils.isEmpty(skus)) {
//            return;
//        }
//        List<CereShopScene> sceneList = cereShopSceneService.selectOnGoingMarketingByShopId(skus.get(0).getShopId());
//        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(sceneList)) {
//            Long memberLevelId = null;
//            if (user != null) {
//                memberLevelId = user.getMemberLevelId();
//            }
//            //没登录的情况下默认第一等级会员
//            if (memberLevelId == null) {
//                CerePlatformMemberLevel firstLevel = cerePlatformMemberLevelService.selectFirstLevel();
//                if (firstLevel != null) {
//                    memberLevelId = firstLevel.getMemberLevelId();
//                }
//            }
//            // 如果还没有初始化会员等级信息，直接返回商品详情
//            if (memberLevelId == null) {
//                return;
//            }
//            for (CereShopScene scene:sceneList) {
//                CereShopSceneMember sceneMember = cereShopSceneMemberService.selectSceneMemberList(scene.getSceneId(), memberLevelId);
//                //如果数据没维护好直接返回商品
//                if (sceneMember == null) {
//                    return;
//                }
//                BigDecimal discount = sceneMember.getDiscount();
//
//                boolean matched = SceneUtil.matchScene(scene.getSceneType(), scene.getSceneTimeType(), scene.getSceneTime(), user != null ? user.getBirthday() : null, false);
//                if (matched) {
//                    for (CartSku sku:skus) {
//                        sku.setSceneId(scene.getSceneId());
//                        sku.setPrice(sku.getPrice().multiply(discount).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP));
//                        sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_SCENE.getCode());
//                    }
//                }
//                break;
//            }
//        }
//    }

    //    private void setMemberInfo(List<CartSku> skus, CereBuyerUser user) {
//        Long memberLevelId = null;
//        if (user != null && user.getMemberLevelId() != null) {
//            memberLevelId = user.getMemberLevelId();
//        }
//        if (memberLevelId == null) {
//            CerePlatformMemberLevel firstLevel = cerePlatformMemberLevelService.selectFirstLevel();
//            if (firstLevel != null) {
//                memberLevelId = firstLevel.getMemberLevelId();
//            }
//        }
//        // 如果还没有初始化会员等级信息 或者sku信息为空，直接返回商品详情
//        if (memberLevelId == null) {
//            return;
//        }
//        for (CartSku sku:skus) {
//            CereProductMember productMember = cereProductMemberService.selectProductMember(memberLevelId, sku.getProductId(), sku.getSkuId());
//            if (productMember != null) {
//                sku.setActivityType(IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode());
//                int mode = productMember.getMode();
//                BigDecimal price = productMember.getPrice();
//                if (IntegerEnum.MEMBER_PRODUCT_MODE_DISCOUNT.getCode().equals(mode)) {
//                    sku.setPrice(sku.getPrice().multiply(price).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP));
//                } else {
//                    sku.setPrice(price);
//                }
//            }
//        }
//    }

    //    private void setPlatformCoupons(CereBuyerUser user, CartSku sku, Map<Long, List<ProductCoupon>> couponMap) {
//        if (sku.getIfCouponAdd() != null && IntegerEnum.NO.getCode().equals(sku.getIfCouponAdd())) {
//            return;
//        }
//        List<ProductCoupon> couponList = couponMap.get(sku.getProductId());
//        if (couponList == null) {
//            if(user!=null){
//                //过滤已使用和已过期的优惠券
//                couponList=cerePlatformActivityService.findCouponByProductIdAndUserId(user.getBuyerUserId(),sku.getProductId());
//            } else {
//                couponList=cerePlatformActivityService.findCouponByProductId(sku.getProductId());
//            }
//            couponMap.put(sku.getProductId(), couponList);
//        }
//        sku.setMarkTools(couponList);
//    }

    /**
     * 设置商家优惠券
     */
//    private void setShopCoupons(CereBuyerUser user, CartSku sku, Map<Long, List<ProductCoupon>> couponMap) {
//        if (sku.getIfCouponAdd() != null && IntegerEnum.NO.getCode().equals(sku.getIfCouponAdd())) {
//            return;
//        }
//        List<ProductCoupon> couponList = couponMap.get(sku.getProductId());
//        if (couponList == null) {
//            if(user!=null){
//                couponList=cereShopCouponService.findByProductIdAndUserId(user.getBuyerUserId(), sku.getShopId(), sku.getProductId());
//            }else {
//                couponList=cereShopCouponService.findByProductId(sku.getShopId(), sku.getProductId());
//            }
//            couponMap.put(sku.getProductId(), couponList);
//        }
//        sku.setShopMarkTools(couponList);
//    }
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(DeleteParam param, CereBuyerUser user) {
        if (!EmptyUtils.isEmpty(param.getIds())) {
            cereShopCartDAO.deleteByIds(param.getIds(), user.getBuyerUserId());
        }
    }

    @Override
    public void deleteAll(Long buyerUserId) {
        cereShopCartDAO.deleteAll(buyerUserId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void selected(SelectedParam param, CereBuyerUser user) {
        /*List<Long> select=new ArrayList<>();
        List<Long> unSelect=new ArrayList<>();
        //更新购物车选中状态
        if(!EmptyUtils.isEmpty(com.shop.zkmall.admin.param.getShopCarts())){
            com.shop.zkmall.admin.param.getShopCarts().forEach(shopCart -> {
                if(!EmptyUtils.isEmpty(shopCart.getSkus())){
                    shopCart.getSkus().forEach(sku -> {
                        if(IntegerEnum.YES.getCode().equals(sku.getSelected())){
                            select.add(sku.getSkuId());
                        }else {
                            unSelect.add(sku.getSkuId());
                        }
                    });
                }
            });
            if(!EmptyUtils.isEmpty(select)){
                //更新选中规格
                cereShopCartDAO.updateSelected(select,user.getBuyerUserId(),IntegerEnum.YES.getCode());
            }
            if(!EmptyUtils.isEmpty(unSelect)){
                //更新未选中规格
                cereShopCartDAO.updateSelected(unSelect,user.getBuyerUserId(),IntegerEnum.NO.getCode());
            }
        }else {
            //全选
            cereShopCartDAO.updateSelectedAll(user.getBuyerUserId());
        }*/
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateNumber(CartUpdateParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //查询活动价格
        //ActivityData data=cereShopCartDAO.findActivityData(com.shop.zkmall.admin.param.getSkuId());
        //查询购物车商品数据
        CereShopCart shopCart = cereShopCartDAO.findBySkuIdAndUser(param.getSkuId(), user.getBuyerUserId());
        if (shopCart != null) {
            shopCart.setNumber(param.getNumber());
            shopCart.setUpdateTime(time);
            /*if(data!=null){
                //校验活动限量和限购
                checkActivity(data,shopCart);
            }*/
            cereShopCartDAO.updateByPrimaryKeySelective(shopCart);
        }
    }

    @Override
    public void deleteSkus(List<CartSku> carts, Long buyerUserId) {
        cereShopCartDAO.deleteSkus(carts, buyerUserId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void addBatchCart(BatchCartParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(param.getCarts())) {
            for (AddCartParam cart : param.getCarts()) {
                //查询购物车中是否已存在该商品
                CereShopCart shopCart = cereShopCartDAO.findShopCart(user.getBuyerUserId(), cart.getSkuId());
                if (shopCart != null) {
                    //在原有数据基础上加上数量
                    shopCart.setNumber(shopCart.getNumber() + cart.getNumber());
                    shopCart.setUpdateTime(time);
                    cereShopCartDAO.updateByPrimaryKeySelective(shopCart);
                } else {
                    //新增购物车商品数据
                    shopCart = cereShopCartDAO.findSku(cart.getSkuId());
                    shopCart.setCreateTime(time);
                    shopCart.setBuyerUserId(user.getBuyerUserId());
                    shopCart.setNumber(cart.getNumber());
                    cereShopCartDAO.insert(shopCart);
                    //查询规格属性值数据同步到购物车商品规格明细表中
                    List<CereCartAttribute> attributes = cereShopCartDAO.findAttributes(cart.getSkuId());
                    if (attributes != null) {
                        CereShopCart finalShopCart = shopCart;
                        attributes.forEach(a -> a.setCartId(finalShopCart.getCartId()));
                        cereCartAttributeService.insertBatch(attributes);
                    }
                }
                //新增转化数据
//                CereShopConversion cereShopConversion=new CereShopConversion();
//                cereShopConversion.setShopId(shopCart.getShopId());
//                cereShopConversion.setCreateTime(time);
//                cereShopConversion.setType(ParamEnum.CONVERSION_CART.getCode());
//                cereShopConversionService.insert(cereShopConversion);
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void buyAgain(BuyerAgainParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //根据订单id查询订单商品数据
        List<CartSku> skus = cereShopCartDAO.findOrderProduct(param.getOrderId());
        if (!EmptyUtils.isEmpty(skus)) {
            List<CereShopCart> updates = new ArrayList<>();
            List<CereShopCart> adds = new ArrayList<>();
            skus.forEach(sku -> {
                //查询购物车是否有该商品
                CereShopCart shopCart = cereShopCartDAO.findShopCart(user.getBuyerUserId(), sku.getSkuId());
                if (shopCart != null) {
                    //加上该订单商品数量
                    shopCart.setNumber(shopCart.getNumber() + sku.getNumber());
                    shopCart.setUpdateTime(time);
                    shopCart.setSelected(IntegerEnum.YES.getCode());
                    updates.add(shopCart);
                } else {
                    //新增购物车商品数据
                    shopCart = new CereShopCart();
                    shopCart.setCreateTime(time);
                    shopCart.setBuyerUserId(user.getBuyerUserId());
                    shopCart.setNumber(sku.getNumber());
                    shopCart.setProductId(sku.getProductId());
                    shopCart.setSkuId(sku.getSkuId());
                    shopCart.setSKU(sku.getSKU());
                    shopCart.setImage(sku.getImage());
                    shopCart.setProductPrice(sku.getPrice());
                    shopCart.setWeight(sku.getWeight());
                    shopCart.setSelected(IntegerEnum.NO.getCode());
                    shopCart.setProductName(sku.getProductName());
                    shopCart.setShopId(sku.getShopId());
                    shopCart.setSelected(IntegerEnum.YES.getCode());
                    adds.add(shopCart);
                }
            });
            if (!EmptyUtils.isEmpty(adds)) {
                cereShopCartDAO.insertBatch(adds);
            }
            if (!EmptyUtils.isEmpty(updates)) {
                cereShopCartDAO.updateBatch(updates);
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateSku(UpdateSkuParam param, CereBuyerUser user) {
        //根据原来的规格id查询购物车商品数据
        CereShopCart cereShopCart = cereShopCartDAO.findShopCart(user.getBuyerUserId(), param.getSkuId());
        if (cereShopCart != null) {
            //查询修改后的商品数据
            CereShopCart sku = cereShopCartDAO.findSku(param.getNewSkuId());
            if (sku != null) {
                cereShopCart.setSkuId(param.getNewSkuId());
                cereShopCart.setProductId(sku.getProductId());
                cereShopCart.setProductName(sku.getProductName());
                cereShopCart.setProductPrice(sku.getProductPrice());
                cereShopCart.setWeight(sku.getWeight());
                cereShopCart.setSKU(sku.getSKU());
                cereShopCart.setImage(sku.getImage());
                cereShopCart.setNumber(param.getNumber());
                //查询活动价格
//                ActivityData data=cereShopGroupWorkDetailService.findPriceBySkuId(com.shop.zkmall.admin.param.getNewSkuId());
//                if(data==null){
//                    //查询限时折扣价
//                    data=cereShopDiscountDetailService.findPriceBySkuId(com.shop.zkmall.admin.param.getNewSkuId());
//                }
//                if(data!=null){
//                    //校验活动限购和限量
//                    checkActivity(data,cereShopCart);
//                }
                cereShopCartDAO.updateByPrimaryKeySelective(cereShopCart);
            }
        }
    }

    @Override
    public Integer findNumber(Long buyerUserId) {
        return cereShopCartDAO.findNumber(buyerUserId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereShopCartDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public Boolean clearInvalidSku(Long buyerUserId) {
        return cereShopCartDAO.clearInvalidSku(buyerUserId);
    }

}
