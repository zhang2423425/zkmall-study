/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shop.zkmall.app.dao.order.CereShopOrderDAO;
import com.shop.zkmall.app.dao.product.CereProductSkuDAO;
import com.shop.zkmall.app.dao.product.CereShopProductDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.param.product.RefreshRealInfoDTO;
import com.shop.zkmall.app.service.product.CereSkuMemberRealInfoService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import com.shop.zkmall.commons.domain.product.CereSkuMemberRealInfo;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * sku当前销量和当前活动信息更新服务
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereSkuMemberRealInfoServiceImpl implements CereSkuMemberRealInfoService {

    private final CereShopProductDAO cereShopProductDAO;

    private final CereProductSkuDAO cereProductSkuDAO;

    private final CereShopOrderDAO cereShopOrderDAO;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    /**
     * 刷新商品实时活动信息
     */
    @Override
    public void refreshSkuRealInfo(RefreshRealInfoDTO refreshRealInfoDTO) {
        //log.info("refreshSkuRealInfo {}", JSON.toJSONString(refreshRealInfoDTO));
        Long productId = refreshRealInfoDTO.getProductId();
        Long paramSkuId = refreshRealInfoDTO.getSkuId();
        List<Long> skuIdList = new ArrayList<>();
        if (paramSkuId == null) {
            skuIdList = cereProductSkuDAO.selectSkuIdListByProductId(productId);
        } else {
            skuIdList.add(paramSkuId);
        }
        List<Long> memberLevelIdList = new ArrayList<>();
        memberLevelIdList.add(0L);

        for (Long skuId : skuIdList) {
            RefreshSkuRealInfoSourceEnum sourceEnum = refreshRealInfoDTO.getSourceEnum();
            CereSkuMemberRealInfo skuMemberRealInfo = cereSkuMemberRealInfoDAO.selectByPrimaryKey(skuId, 0L);
            if (skuMemberRealInfo == null) {
                if (sourceEnum.equals(RefreshSkuRealInfoSourceEnum.PUT_OFF_SHELVE)) {
                    cereSkuMemberRealInfoDAO.deleteByProductId(productId);
                } else {
                    refreshInner(productId, skuId, null, true, refreshRealInfoDTO.getFictitiousNumber(), memberLevelIdList);
                }
            } else {
                switch (sourceEnum) {
                    case NORMAL:
                    case PUT_ON_SHELVE:
                    case SCENE_END:
                        refreshInner(productId, skuId, skuMemberRealInfo, false, refreshRealInfoDTO.getFictitiousNumber(), memberLevelIdList);
                        break;
                    case PUT_OFF_SHELVE:
                        cereSkuMemberRealInfoDAO.deleteByProductId(productId);
                        break;
                    case EDIT_PRODUCT:
                        refreshPrice(productId, skuId, skuMemberRealInfo);
                        break;
                    case GROUP_PRE_HOT:
                    case GROUP_START:
                    case SHOP_SECKILL_PRE_HOT:
                    case SHOP_SECKILL_START:
                    case SHOP_DISCOUNT_PRE_HOT:
                    case SHOP_DISCOUNT_START:
                    case SECKILL_START:
                    case DISCOUNT_START:
                        refreshActivityPreHotOrStart(productId, skuId, sourceEnum);
                        break;
                    case GROUP_END:
                    case SHOP_SECKILL_END:
                    case SHOP_DISCOUNT_END:
                    case SECKILL_END:
                    case DISCOUNT_END:
                        refreshActivityEnd(productId, skuId);
                        break;
                    case SCENE_START:
                        //在refreshSkuRealInfoForActivity方法中刷新
                        break;
                    case EDIT_MEMBER_PRODUCT:
                        List<Integer> canUpdateMemberList = Arrays.asList(IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode(),
                                IntegerEnum.ACTIVITY_TYPE_PRICE.getCode(), IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode());
                        if (canUpdateMemberList.contains(skuMemberRealInfo.getActivityType())) {
                            refreshMember(productId, skuId);
                        }
                        break;
                    case CLEAR_MEMBER_PRODUCT:
                        //场景营销优先于 会员价 并且会设置 cere_sku_member_real_info 所以这里不能清除
                        if (!IntegerEnum.ACTIVITY_TYPE_SCENE.getCode().equals(skuMemberRealInfo.getActivityType())) {
                            cereSkuMemberRealInfoDAO.clearMemberProductInfo(skuMemberRealInfo.getSkuId());
                        }
                        break;
                    case EDIT_FICTITIOUS_NUMBER:
                        refreshFictitiousNumber(productId, refreshRealInfoDTO.getFictitiousNumber());
                        break;
                }
            }
        }
    }

    /**
     * 刷新虚拟销量处理
     */
    private void refreshFictitiousNumber(Long productId, Integer updateFictitiousNumber) {
        CereShopProduct product = cereShopProductDAO.selectById(productId);
        Integer fictitiousNumber = product.getFictitiousNumber();
        Integer addedNumber = updateFictitiousNumber - fictitiousNumber;
        cereSkuMemberRealInfoDAO.increSalesVolumeBy(productId, addedNumber);
    }

    private void refreshMember(Long productId, Long skuId) {
        //log.info("refreshMember {} {} {}", productId, skuId, productMemberList.size());
        CereProductSku sku = cereProductSkuDAO.selectById(skuId);
        String now = TimeUtils.yyMMddHHmmss();

    }

    private void refreshSceneStart(Long activityId) {

    }

    /**
     * 刷新活动结束的情况
     */
    private void refreshActivityEnd(Long productId, Long skuId) {
        CereShopProduct product = cereShopProductDAO.selectById(productId);
        CereProductSku sku = cereProductSkuDAO.selectByPrimaryKey(skuId);
        boolean queryEnd = false;
        String now = TimeUtils.yyMMddHHmmss();
        Integer activityType = IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode();
        Long activityId = 0L;
        BigDecimal curPrice = sku.getPrice();
        BigDecimal curOriginalPrice = sku.getOriginalPrice();
        //场景营销

        //会员价

        //定价捆绑 和 组合捆绑 在购物车才有必要处理
        /*if (!queryEnd) {
            Long priceId = cereShopPriceService.selectPriceByProductId(productId);
            if (priceId != null) {
                activityType = IntegerEnum.ACTIVITY_TYPE_PRICE.getCode();
                activityId = priceId;
            }
        }*/
        CereSkuMemberRealInfo updater = new CereSkuMemberRealInfo();
        updater.setSkuId(skuId);
        updater.setMemberLevelId(0L);
        updater.setCurPrice(curPrice);
        updater.setCurOriginalPrice(curOriginalPrice);
        updater.setActivityType(activityType);
        updater.setActivityId(activityId);
        updater.setStartTime("");
        updater.setEndTime("");
        //不管是平台活动还是商家活动 状态都改成0
        updater.setState(IntegerEnum.TOOL_NOT_START.getCode());
        updater.setIfAdd(IntegerEnum.NO.getCode());
        updater.setUpdateTime(now);
        cereSkuMemberRealInfoDAO.updateSelective(updater, true);
    }

    /**
     * 刷新活动预热或开始的情况
     */
    private void refreshActivityPreHotOrStart(Long productId, Long skuId, RefreshSkuRealInfoSourceEnum sourceEnum) {
        CereProductSku sku = cereProductSkuDAO.selectByPrimaryKey(skuId);
        if (sku == null) {
            return;
        }
        boolean deleteMember = true;
        String now = TimeUtils.yyMMddHHmmss();
        Date nowDate = new Date();
        Integer activityType = IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode();
        Long activityId = 0L;
        BigDecimal curPrice = null;
        BigDecimal curOriginalPrice = null;

        Integer ifEnable = IntegerEnum.ENABLE_STOP.getCode();
        Integer ifAdd = IntegerEnum.NO.getCode();
        Integer person = 0;
        Integer enableTime = 0;
        String realStartTime = "";
        String realEndTime = "";
        int state = 0;

        Integer salesVolume = null;
        Integer limitNumber = null;
        try {
            if (sourceEnum.equals(RefreshSkuRealInfoSourceEnum.SHOP_DISCOUNT_PRE_HOT) || sourceEnum.equals(RefreshSkuRealInfoSourceEnum.SHOP_DISCOUNT_START)) {
            }
            if (sourceEnum.equals(RefreshSkuRealInfoSourceEnum.DISCOUNT_START)) {

            }
            CereSkuMemberRealInfo updater = new CereSkuMemberRealInfo();
            updater.setSkuId(skuId);
            updater.setMemberLevelId(0L);
            updater.setActivityType(activityType);
            updater.setActivityId(activityId);
            updater.setCurPrice(curPrice);
            updater.setCurOriginalPrice(curOriginalPrice);
            updater.setSalesVolume(salesVolume);
            updater.setLimitNumber(limitNumber);
            updater.setIfEnable(ifEnable);
            updater.setStartTime(realStartTime);
            updater.setEndTime(realEndTime);
            updater.setState(state);
            updater.setEnableTime(enableTime);
            updater.setIfAdd(ifAdd);
            updater.setPerson(person);
            updater.setUpdateTime(now);

            cereSkuMemberRealInfoDAO.updateSelective(updater, salesVolume == null);

            /*if (deleteMember) {
                List<RefreshSkuRealInfoSourceEnum> productLevelList = Arrays.asList(RefreshSkuRealInfoSourceEnum.SECKILL_START,
                        RefreshSkuRealInfoSourceEnum.DISCOUNT_START);
                if (productLevelList.contains(sourceEnum)) {
                    cereSkuMemberRealInfoDAO.deleteByProductId(productId);
                } else {
                    cereSkuMemberRealInfoDAO.deleteBySkuId(skuId);
                }
            }*/
        } catch (Exception e) {
            log.error("refreshActivityPreHotOrStart fail: {}", e.getMessage(), e);
        }
    }

    /**
     * 更新价格相关的字段
     */
    private void refreshPrice(Long productId, Long skuId, CereSkuMemberRealInfo skuRealInfo) {
        List<CereProductSku> skuList = cereProductSkuDAO.selectList(Wrappers.<CereProductSku>lambdaQuery().eq(CereProductSku::getProductId, productId));
        if (CollectionUtils.isNotEmpty(skuList)) {
            skuRealInfo.setMinPrice(skuList.stream().map(CereProductSku::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
            skuRealInfo.setMaxPrice(skuList.stream().map(CereProductSku::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
            CereProductSku sku = skuList.stream().filter(obj -> obj.getSkuId().equals(skuId)).findFirst().orElse(null);
            if (sku != null) {
                //更活动信息相关的列表
                List<Integer> updateActivityList = Arrays.asList(IntegerEnum.ACTIVITY_TYPE_SHOP_GROUP.getCode(),
                        IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode(), IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode(),
                        IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode(), IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode());
                List<Integer> updateMemberList = Arrays.asList(IntegerEnum.ACTIVITY_TYPE_SCENE.getCode(),
                        IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode());
                if (updateActivityList.contains(skuRealInfo.getActivityType())) {
                    skuRealInfo.setCurOriginalPrice(sku.getPrice());
                } else if (updateMemberList.contains(skuRealInfo.getActivityType())) {
                    skuRealInfo.setCurPrice(sku.getPrice());
                    skuRealInfo.setCurOriginalPrice(sku.getOriginalPrice());

                    CereSkuMemberRealInfo memberRealInfo = new CereSkuMemberRealInfo();
                    memberRealInfo.setSkuId(skuId);
                    memberRealInfo.setCurOriginalPrice(sku.getPrice());
                    cereSkuMemberRealInfoDAO.updateSkuPrice(memberRealInfo);
                } else {
                    skuRealInfo.setCurPrice(sku.getPrice());
                    skuRealInfo.setCurOriginalPrice(sku.getOriginalPrice());
                }

                CereSkuMemberRealInfo updater = new CereSkuMemberRealInfo();
                updater.setSkuId(skuId);
                updater.setMemberLevelId(0L);
                updater.setMinPrice(skuRealInfo.getMinPrice());
                updater.setMaxPrice(skuRealInfo.getMaxPrice());
                updater.setCurPrice(skuRealInfo.getCurPrice());
                updater.setCurOriginalPrice(skuRealInfo.getCurOriginalPrice());

                cereSkuMemberRealInfoDAO.updateByPrimaryKeySelective(updater);
            }
        }
    }

    private void refreshInner(Long productId, Long skuId, CereSkuMemberRealInfo skuMemberRealInfo,
                              boolean needInsert, Integer fictitiousNumber,
                              List<Long> memberLevelIdList) {
        String now = TimeUtils.yyMMddHHmmss();
        if (needInsert) {
            skuMemberRealInfo = new CereSkuMemberRealInfo();
            skuMemberRealInfo.setSkuId(skuId);
            skuMemberRealInfo.setProductId(productId);
            skuMemberRealInfo.setCreateTime(now);
        }
        Date nowDate = new Date();
        boolean queryEnd = false;
        try {
            skuMemberRealInfo.setUpdateTime(now);

            CereShopProduct product = cereShopProductDAO.selectById(productId);

            CereProductSku sku = cereProductSkuDAO.selectById(skuId);

            List<CereProductSku> skuList = cereProductSkuDAO.selectList(Wrappers.<CereProductSku>lambdaQuery().eq(CereProductSku::getProductId, productId));
            if (CollectionUtils.isNotEmpty(skuList)) {
                skuMemberRealInfo.setMinPrice(skuList.stream().map(CereProductSku::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
                skuMemberRealInfo.setMaxPrice(skuList.stream().map(CereProductSku::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
            }

            Integer activityType = IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode();
            Long activityId = 0L;
            BigDecimal curPrice = sku.getPrice();
            BigDecimal curOriginalPrice = sku.getOriginalPrice();

            Integer ifEnable = IntegerEnum.ENABLE_STOP.getCode();
            Integer ifAdd = IntegerEnum.NO.getCode();
            Integer person = 0;
            Integer enableTime = 0;

            int limitNumber = 0;
            int salesUserCount = 0;
            Integer salesVolume = -1;
            int skuSalesVolume = 0;
            int productSalesVolume = 0;

            String realStartTime = "";
            String realEndTime = "";
            int state = 0;

            //拼团
            /*CereShopGroupWork cereShopGroupWork = cereShopGroupWorkDAO.selectByProductId(productId);
            if (cereShopGroupWork != null) {
                List<CereShopGroupWorkDetail> groupWorkDetailList = cereShopGroupWorkDetailDAO
                        .selectList(Wrappers.<CereShopGroupWorkDetail>lambdaQuery()
                                .eq(CereShopGroupWorkDetail::getShopGroupWorkId, cereShopGroupWork.getShopGroupWorkId())
                                .eq(CereShopGroupWorkDetail::getSkuId, skuId));
                if (groupWorkDetailList.size() > 0) {
                    Date startTime = TimeUtils.parseDate(cereShopGroupWork.getStartTime());
                    Date endTime = TimeUtils.parseDate(cereShopGroupWork.getEndTime());
                    realStartTime = cereShopGroupWork.getStartTime();
                    realEndTime = cereShopGroupWork.getEndTime();
                    ifAdd = cereShopGroupWork.getIfAdd();
                    person = cereShopGroupWork.getPerson();
                    boolean bizStart = false;
                    if (nowDate.after(startTime) && nowDate.before(endTime)) {
                        bizStart = true;
                    } else {
                        if (IntegerEnum.ENABLE_START.getCode().equals(cereShopGroupWork.getIfEnable())) {
                            ifEnable = IntegerEnum.ENABLE_START.getCode();
                            enableTime = cereShopGroupWork.getEnableTime();
                            String preHotTime = TimeUtils.headDate(cereShopGroupWork.getStartTime(), cereShopGroupWork.getEnableTime());
                            Date preHotDate = TimeUtils.parseDate(preHotTime);
                            //预热中
                            if (nowDate.after(preHotDate) && nowDate.before(startTime)) {
                                bizStart = true;
                            }
                        }
                    }
                    if (bizStart) {
                        state = IntegerEnum.TOOL_HAND.getCode();
                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopGroupWork.getIfLimit())) {
                            limitNumber = cereShopGroupWork.getLimitNumber();
                        }
                        curPrice = groupWorkDetailList.get(0).getPrice();
                        curOriginalPrice = sku.getOriginalPrice();
                        activityType = IntegerEnum.ACTIVITY_TYPE_SHOP_GROUP.getCode();
                        activityId = cereShopGroupWork.getShopGroupWorkId();
                        queryEnd = true;
                    }
                }
            }*/

            //平台秒杀
            /*if (!queryEnd) {
                SeckillRelateProductInfo relateProductInfoSeckill = cerePlatformSeckillDAO.selectRelateInfoByProductId(productId);
                if (relateProductInfoSeckill != null) {
                    realStartTime = relateProductInfoSeckill.getStartTime();
                    realEndTime = relateProductInfoSeckill.getEndTime();
                    ifAdd = relateProductInfoSeckill.getIfAdd();
                    state = IntegerEnum.ACTIVITY_START.getCode();
                    curPrice = sku.getOriginalPrice().subtract(relateProductInfoSeckill.getSeckillMoney());
                    curOriginalPrice = sku.getOriginalPrice();
                    if (relateProductInfoSeckill.getTotal() != null && relateProductInfoSeckill.getNumber() != null) {
                        salesVolume = relateProductInfoSeckill.getTotal() - relateProductInfoSeckill.getNumber();
                    }
                    if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(relateProductInfoSeckill.getIfLimit())) {
                        limitNumber = relateProductInfoSeckill.getLimitNumber();
                    }
                    activityType = IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode();
                    activityId = relateProductInfoSeckill.getSeckillId();
                    queryEnd = true;
                }
            }*/

            //平台折扣
            /*if (!queryEnd) {
                DiscountRelateProductInfo relateProductInfoDiscount = cerePlatformDiscountDAO.selectRelateInfoByProductId(productId);
                if (relateProductInfoDiscount != null) {
                    realStartTime = relateProductInfoDiscount.getStartTime();
                    realEndTime = relateProductInfoDiscount.getEndTime();
                    ifAdd = relateProductInfoDiscount.getIfAdd();
                    state = IntegerEnum.ACTIVITY_START.getCode();
                    BigDecimal discount = relateProductInfoDiscount.getDiscount().divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
                    curPrice = sku.getOriginalPrice().multiply(discount).setScale(2, RoundingMode.HALF_UP);
                    curOriginalPrice = sku.getOriginalPrice();
                    if (relateProductInfoDiscount.getTotal() != null && relateProductInfoDiscount.getNumber() != null) {
                        salesVolume = relateProductInfoDiscount.getTotal() - relateProductInfoDiscount.getNumber();
                    }
                    if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(relateProductInfoDiscount.getIfLimit())) {
                        limitNumber = relateProductInfoDiscount.getLimitNumber();
                    }
                    activityType = IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode();
                    activityId = relateProductInfoDiscount.getDiscountId();
                    queryEnd = true;
                }
            }*/

            //商家秒杀
            /*if (!queryEnd) {
                SeckillRelateProductInfo shopSeckillRelateProductInfo = cereShopSeckillDAO.selectRelateInfoBySkuId(skuId);
                if (shopSeckillRelateProductInfo != null) {
                    //判断是否预热中或者活动进行中
                    Date startTime = TimeUtils.parseDate(shopSeckillRelateProductInfo.getStartTime());
                    Date endTime = TimeUtils.parseDate(shopSeckillRelateProductInfo.getEndTime());
                    realStartTime = shopSeckillRelateProductInfo.getStartTime();
                    realEndTime = shopSeckillRelateProductInfo.getEndTime();
                    ifAdd = shopSeckillRelateProductInfo.getIfAdd();
                    boolean bizStart = false;
                    if (nowDate.after(startTime) && nowDate.before(endTime)) {
                        bizStart = true;
                    } else {
                        if (IntegerEnum.ENABLE_START.getCode().equals(shopSeckillRelateProductInfo.getIfEnable())) {
                            ifEnable = IntegerEnum.ENABLE_START.getCode();
                            enableTime = shopSeckillRelateProductInfo.getEnableTime();
                            String preHotTime = TimeUtils.headDate(shopSeckillRelateProductInfo.getStartTime(), shopSeckillRelateProductInfo.getEnableTime());
                            Date preHotDate = TimeUtils.parseDate(preHotTime);
                            //预热中
                            if (nowDate.after(preHotDate) && nowDate.before(startTime)) {
                                bizStart = true;
                            }
                        }
                    }
                    if (bizStart) {
                        state = IntegerEnum.TOOL_HAND.getCode();
                        curPrice = shopSeckillRelateProductInfo.getSeckillMoney();
                        curOriginalPrice = sku.getOriginalPrice();
                        if (shopSeckillRelateProductInfo.getTotal() != null && shopSeckillRelateProductInfo.getNumber() != null) {
                            salesVolume = shopSeckillRelateProductInfo.getTotal() - shopSeckillRelateProductInfo.getNumber();
                        }
                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(shopSeckillRelateProductInfo.getIfLimit())) {
                            limitNumber = shopSeckillRelateProductInfo.getLimitNumber();
                        }
                        activityType = IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode();
                        activityId = shopSeckillRelateProductInfo.getSeckillId();
                        queryEnd = true;
                    }
                }
            }*/

            //商家折扣
            /*if (!queryEnd) {
                DiscountRelateProductInfo shopDiscountRelateProductInfo = cereShopDiscountDAO.selectRelateInfoBySkuId(skuId);
                if (shopDiscountRelateProductInfo != null) {
                    //判断是否预热中或者活动进行中
                    Date startTime = TimeUtils.parseDate(shopDiscountRelateProductInfo.getStartTime());
                    Date endTime = TimeUtils.parseDate(shopDiscountRelateProductInfo.getEndTime());
                    realStartTime = shopDiscountRelateProductInfo.getStartTime();
                    realEndTime = shopDiscountRelateProductInfo.getEndTime();
                    ifAdd = shopDiscountRelateProductInfo.getIfAdd();
                    boolean bizStart = false;
                    if (nowDate.after(startTime) && nowDate.before(endTime)) {
                        bizStart = true;
                    } else {
                        if (IntegerEnum.ENABLE_START.getCode().equals(shopDiscountRelateProductInfo.getIfEnable())) {
                            ifEnable = IntegerEnum.ENABLE_START.getCode();
                            enableTime = shopDiscountRelateProductInfo.getEnableTime();
                            String preHotTime = TimeUtils.headDate(shopDiscountRelateProductInfo.getStartTime(), shopDiscountRelateProductInfo.getEnableTime());
                            Date preHotDate = TimeUtils.parseDate(preHotTime);
                            //预热中
                            if (nowDate.after(preHotDate) && nowDate.before(startTime)) {
                                bizStart = true;
                            }
                        }
                    }
                    if (bizStart) {
                        state = IntegerEnum.TOOL_HAND.getCode();
                        BigDecimal discount = shopDiscountRelateProductInfo.getDiscount().divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
                        curPrice = sku.getOriginalPrice().multiply(discount).setScale(2, RoundingMode.HALF_UP);
                        curOriginalPrice = sku.getOriginalPrice();
                        if (shopDiscountRelateProductInfo.getTotal() != null && shopDiscountRelateProductInfo.getNumber() != null) {
                            salesVolume = shopDiscountRelateProductInfo.getTotal() - shopDiscountRelateProductInfo.getNumber();
                        }
                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(shopDiscountRelateProductInfo.getIfLimit())) {
                            limitNumber = shopDiscountRelateProductInfo.getLimitNumber();
                        }
                        activityType = IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode();
                        activityId = shopDiscountRelateProductInfo.getDiscountId();
                        queryEnd = true;
                    }
                }
            }*/

            skuMemberRealInfo.setCurPrice(curPrice);
            skuMemberRealInfo.setCurOriginalPrice(curOriginalPrice);
            skuMemberRealInfo.setIfEnable(ifEnable);
            skuMemberRealInfo.setIfAdd(ifAdd);
            skuMemberRealInfo.setPerson(person);
            skuMemberRealInfo.setEnableTime(enableTime);
            skuMemberRealInfo.setLimitNumber(limitNumber);

            skuMemberRealInfo.setStartTime(realStartTime);
            skuMemberRealInfo.setEndTime(realEndTime);
            skuMemberRealInfo.setState(state);

            boolean matchMemberLevel = false;
            List<CereSkuMemberRealInfo> memberLevelList = new ArrayList<>();

            //场景营销
            /*if (!queryEnd) {
                List<CereShopScene> sceneList = cereShopSceneDAO.selectOnGoingMarketingByShopId(product.getShopId());
                if (CollectionUtils.isNotEmpty(sceneList)) {
                    //因为当前场景营销，一个商家只会存在一个营销，所以只取第一个
                    CereShopScene scene = sceneList.get(0);
                    Integer sceneType = scene.getSceneType();
                    Integer sceneTimeType = scene.getSceneTimeType();
                    String sceneTime = scene.getSceneTime();
                    boolean matched = SceneUtil.matchScene(sceneType, sceneTimeType, sceneTime, null, false);
                    if (matched) {
                        if (IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(sceneType)
                                || IntegerEnum.SCENE_TYPE_MEMBER.getCode().equals(sceneType)) {
                            List<CereShopSceneMember> sceneMemberList = cereShopSceneMemberDAO.selectList(Wrappers.<CereShopSceneMember>lambdaQuery()
                                    .eq(CereShopSceneMember::getSceneId, scene.getSceneId()));
                            activityType = IntegerEnum.ACTIVITY_TYPE_SCENE.getCode();
                            activityId = scene.getSceneId();
                            for (CereShopSceneMember sceneMember : sceneMemberList) {
                                BigDecimal discount = sceneMember.getDiscount().divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
                                BigDecimal innerCurPrice = sku.getPrice().multiply(discount).setScale(2, RoundingMode.HALF_UP);
                                BigDecimal innerCurOriginalPrice = sku.getPrice();
                                CereSkuMemberRealInfo memberRealInfo = new CereSkuMemberRealInfo();
                                BeanUtils.copyProperties(skuMemberRealInfo, memberRealInfo);
                                memberRealInfo.setSkuId(skuId);
                                memberRealInfo.setProductId(productId);
                                memberRealInfo.setMemberLevelId(sceneMember.getMemberLevelId());
                                memberRealInfo.setCurPrice(innerCurPrice);
                                memberRealInfo.setCurOriginalPrice(innerCurOriginalPrice);
                                memberRealInfo.setCreateTime(now);
                                memberRealInfo.setUpdateTime(now);

                                memberLevelList.add(memberRealInfo);
                                //cereSkuMemberRealInfoDAO.insertOrUpdate(memberRealInfo);
                            }
                            matchMemberLevel = true;
                            queryEnd = true;
                        }
                    }
                }
            }*/

            //会员价
            /*if (!queryEnd) {
                List<CereProductMember> productMemberList = cereProductMemberDAO.selectList(Wrappers.<CereProductMember>lambdaQuery()
                        .eq(CereProductMember::getSkuId, skuId));
                for (CereProductMember productMember : productMemberList) {
                    activityType = IntegerEnum.ACTIVITY_TYPE_MEMBER.getCode();
                    int mode = productMember.getMode();
                    BigDecimal innerCurPrice = productMember.getPrice();
                    if (IntegerEnum.MEMBER_PRODUCT_MODE_DISCOUNT.getCode().equals(mode)) {
                        BigDecimal discount = productMember.getPrice().divide(BigDecimal.TEN, RoundingMode.HALF_UP);
                        innerCurPrice = sku.getPrice().multiply(discount).setScale(2, RoundingMode.HALF_UP);
                    }
                    BigDecimal innerCurOriginalPrice = sku.getPrice();
                    CereSkuMemberRealInfo memberRealInfo = new CereSkuMemberRealInfo();
                    BeanUtils.copyProperties(skuMemberRealInfo, memberRealInfo);
                    memberRealInfo.setSkuId(skuId);
                    memberRealInfo.setProductId(productId);
                    memberRealInfo.setMemberLevelId(productMember.getMemberLevelId());
                    memberRealInfo.setCurPrice(innerCurPrice);
                    memberRealInfo.setCurOriginalPrice(innerCurOriginalPrice);
                    memberRealInfo.setCreateTime(now);
                    memberRealInfo.setUpdateTime(now);

                    memberLevelList.add(memberRealInfo);
                    //cereSkuMemberRealInfoDAO.insertOrUpdate(memberRealInfo);
                    matchMemberLevel = true;
                }
            }*/

            skuMemberRealInfo.setActivityType(activityType);
            skuMemberRealInfo.setActivityId(activityId);

            //定价捆绑 和 组合捆绑 在购物车才有必要处理
            /*if (!queryEnd) {
                Long priceId = cereShopPriceService.selectPriceByProductId(productId);
                if (priceId != null) {
                    activityType = IntegerEnum.ACTIVITY_TYPE_PRICE.getCode();
                    activityId = priceId;
                }
            }*/

            /*if (matchMemberLevel) {
                memberLevelIdList.clear();
                memberLevelIdList.add(0L);
            }*/

            if (needInsert) {
                salesUserCount = cereShopOrderDAO.selectSalesUserCount(productId);
                skuSalesVolume = cereShopOrderDAO.selectSalesVolume(productId, skuId);
                productSalesVolume = cereShopOrderDAO.selectSalesVolume(productId, null);
                if (salesVolume == -1) {
                    salesVolume = productSalesVolume;
                }
                if (fictitiousNumber == null) {
                    fictitiousNumber = product.getFictitiousNumber();
                }
                salesVolume += fictitiousNumber;
                skuSalesVolume += fictitiousNumber;
                productSalesVolume += fictitiousNumber;
                skuMemberRealInfo.setSalesUserCount(salesUserCount);
                skuMemberRealInfo.setSalesVolume(salesVolume);
                skuMemberRealInfo.setSkuSalesVolume(skuSalesVolume);
                skuMemberRealInfo.setProductSalesVolume(productSalesVolume);

                for (Long memberLevelId : memberLevelIdList) {
                    skuMemberRealInfo.setMemberLevelId(memberLevelId);
                    cereSkuMemberRealInfoDAO.insertOrUpdate(skuMemberRealInfo);
                }

                if (matchMemberLevel) {
                    for (CereSkuMemberRealInfo memberRealInfo : memberLevelList) {
                        memberRealInfo.setActivityType(skuMemberRealInfo.getActivityType());
                        memberRealInfo.setActivityId(skuMemberRealInfo.getActivityId());
                        //TODO 更新4个销量 如果并发较高，insertOrUpdate走的是 on duplicate key 更新这4个值可能存在销量不准确的情况
                        memberRealInfo.setSalesUserCount(salesUserCount);
                        memberRealInfo.setSalesVolume(salesVolume);
                        memberRealInfo.setSkuSalesVolume(skuSalesVolume);
                        memberRealInfo.setProductSalesVolume(productSalesVolume);
                        cereSkuMemberRealInfoDAO.insertOrUpdate(memberRealInfo);
                    }
                }
            } else {
                CereSkuMemberRealInfo updater = new CereSkuMemberRealInfo();
                BeanUtils.copyProperties(skuMemberRealInfo, updater);
                updater.setSalesUserCount(null);
                updater.setSkuSalesVolume(null);
                updater.setProductSalesVolume(null);
                updater.setSalesVolume(null);

                for (Long memberLevelId : memberLevelIdList) {
                    updater.setMemberLevelId(memberLevelId);
                    cereSkuMemberRealInfoDAO.updateByPrimaryKeySelective(updater);
                }

                if (matchMemberLevel) {
                    for (CereSkuMemberRealInfo memberRealInfo : memberLevelList) {
                        CereSkuMemberRealInfo levelUpdater = new CereSkuMemberRealInfo();
                        BeanUtils.copyProperties(skuMemberRealInfo, levelUpdater);

                        levelUpdater.setSalesUserCount(null);
                        levelUpdater.setSkuSalesVolume(null);
                        levelUpdater.setProductSalesVolume(null);
                        levelUpdater.setSalesVolume(null);

                        levelUpdater.setMemberLevelId(memberRealInfo.getMemberLevelId());
                        levelUpdater.setCurOriginalPrice(memberRealInfo.getCurOriginalPrice());
                        levelUpdater.setCurPrice(memberRealInfo.getCurPrice());

                        cereSkuMemberRealInfoDAO.updateByPrimaryKeySelective(levelUpdater);
                    }
                }
            }
        } catch (Exception e) {
            log.error("refreshSkuRealInfo fail: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean refreshAllProductRealInfo() {
        List<CereShopProduct> productList = cereShopProductDAO.selectList(Wrappers.<CereShopProduct>lambdaQuery()
                .eq(CereShopProduct::getShelveState, IntegerEnum.YES.getCode()));
        log.info("refreshAllProduct size {}", productList.size());
        for (CereShopProduct product : productList) {
            List<CereProductSku> skuList = cereProductSkuDAO.selectList(Wrappers.<CereProductSku>lambdaQuery()
                    .eq(CereProductSku::getProductId, product.getProductId()));
            try {
                for (CereProductSku sku : skuList) {
                    RefreshRealInfoDTO dto = new RefreshRealInfoDTO();
                    dto.setProductId(product.getProductId());
                    dto.setSkuId(sku.getSkuId());
                    dto.setSourceEnum(RefreshSkuRealInfoSourceEnum.NORMAL);
                    this.refreshSkuRealInfo(dto);
                }
            } catch (Exception e) {
                log.error("refreshAllProductRealInfo {} fail: {}", product.getProductId(), e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    public void refreshSkuRealInfoForActivity(RefreshRealInfoDTO refreshRealInfoDTO) {
        RefreshSkuRealInfoSourceEnum sourceEnum = refreshRealInfoDTO.getSourceEnum();
        Long activityId = refreshRealInfoDTO.getActivityId();
        switch (sourceEnum) {
            case SCENE_START:
                refreshSceneStart(activityId);
                break;
            case SCENE_END:
                refreshSceneEnd(activityId);
                break;
            case DISCOUNT_START:
            case DISCOUNT_END, GROUP_PRE_HOT, GROUP_START, GROUP_END, SHOP_SECKILL_PRE_HOT, SHOP_SECKILL_START,
                 SHOP_DISCOUNT_PRE_HOT, SHOP_DISCOUNT_START, SHOP_DISCOUNT_END:
                break;
        }
    }

    private void refreshSceneEnd(Long activityId) {

    }

}
