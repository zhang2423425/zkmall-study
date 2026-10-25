/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.redis.listener;

import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.redis.service.api.UserRedisService;
import com.shop.zkmall.business.service.activity.CereActivitySignService;
import com.shop.zkmall.business.service.business.CerePlatformBusinessService;
import com.shop.zkmall.business.service.notice.CereNoticeService;
import com.shop.zkmall.business.service.product.CereShopProductService;
import com.shop.zkmall.business.service.redis.CereRedisKeyService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.ProjectInvokeUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
//@Component
public class RedisListener extends KeyExpirationEventMessageListener {

    private RedisTemplate<String, Object> redisTemplate;

    private CerePlatformBusinessService cerePlatformBusinessService;

    private CereRedisKeyService cereRedisKeyService;

    private CereNoticeService cereNoticeService;

    private CereActivitySignService cereActivitySignService;

    private UserRedisService userRedisService;

    private ProjectInvokeUtil projectInvokeUtil;

    public RedisListener(RedisMessageListenerContainer listenerContainer,
                         RedisTemplate<String, Object> redisTemplate, CerePlatformBusinessService cerePlatformBusinessService
            , CereRedisKeyService cereRedisKeyService, CereNoticeService cereNoticeService
            , CereShopProductService cereShopProductService
            , CereActivitySignService cereActivitySignService,
                         ProjectInvokeUtil projectInvokeUtil) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
        this.cerePlatformBusinessService = cerePlatformBusinessService;
        this.cereRedisKeyService = cereRedisKeyService;
        this.cereNoticeService = cereNoticeService;
        this.cereActivitySignService = cereActivitySignService;
        this.projectInvokeUtil = projectInvokeUtil;
    }

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("RedisListener key={}", expiredKey);
        String time = TimeUtils.yyMMddHHmmss();
        try {
            if (!EmptyUtils.isEmpty(expiredKey)) {
                if (expiredKey.contains("-")) {
                    String[] split = expiredKey.split("-");
                    String operation = split[0];
                    /*if(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode().equals(operation)){
                        //替换用户token
                        changeBusinessToken(split[1],time);
                    }else */
                    if (StringEnum.NOTICE_OPERATE.getCode().equals(operation)) {
                        //手动计划运营计划定时发送消息通知
                        addOperate(split);
                    } else if (StringEnum.NOTICE_OPERATE_AUTOMATIC.getCode().equals(operation)) {
                        //自动计划运营计划定时发送消息通知
                        addAutomaticOperate(split);
                    } else if (StringEnum.OPERATE_STATE_STAY.getCode().equals(operation)) {
                        //修改运营计划状态为进行中
                        updateOperateState(split[1], IntegerEnum.COUPON_STATE_START.getCode());
                    } else if (StringEnum.OPERATE_STATE_STOP.getCode().equals(operation)) {
                        //修改运营计划状态为已结束
                        updateOperateState(split[1], IntegerEnum.COUPON_STATE_END.getCode());
                    } else if (StringEnum.SHOP_COUPON_IN.getCode().equals(operation)) {
                        //修改店铺优惠券状态为进行中
                        updateShopCouponState(split[1], IntegerEnum.COUPON_STATE_START.getCode(), time);
                    } else if (StringEnum.SHOP_COUPON_END.getCode().equals(operation)) {
                        //修改店铺优惠券状态为已结束
                        updateShopCouponState(split[1], IntegerEnum.COUPON_STATE_END.getCode(), time);
                    } else if (StringEnum.SHOP_GROUP_WORK_PRE_HOT.getCode().equals(operation)) {
                        //修改拼团活动状态为预热中
                        log.info("redisListener shopGroupPreHot {}", split[1]);
                        updateShopGroupWorkState(split[1], IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
                    } else if (StringEnum.SHOP_GROUP_WORK_IN.getCode().equals(operation)) {
                        //修改拼团活动状态为进行中
                        updateShopGroupWorkState(split[1], IntegerEnum.COUPON_STATE_START.getCode(), time);
                    } else if (StringEnum.SHOP_GROUP_WORK_END.getCode().equals(operation)) {
                        //修改拼团活动状态为已结束
                        updateShopGroupWorkState(split[1], IntegerEnum.COUPON_STATE_END.getCode(), time);
                    } else if (StringEnum.SHOP_SECKILL_PRE_HOT.getCode().equals(operation)) {
                        //修改秒杀活动状态为预热中
                        log.info("redisListener shopSeckillPreHot {}", split[1]);
                        updateShopSeckillState(split[1], IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
                    } else if (StringEnum.SHOP_SECKILL_IN.getCode().equals(operation)) {
                        //修改秒杀活动状态为进行中
                        updateShopSeckillState(split[1], IntegerEnum.COUPON_STATE_START.getCode(), time);
                    } else if (StringEnum.SHOP_SECKILL_END.getCode().equals(operation)) {
                        //修改秒杀活动状态为已结束
                        updateShopSeckillState(split[1], IntegerEnum.COUPON_STATE_END.getCode(), time);
                    } else if (StringEnum.SHOP_DISCOUNT_PRE_HOT.getCode().equals(operation)) {
                        //修改限时折扣活动状态为预热中
                        log.info("redisListener shopDiscountPreHot {}", split[1]);
                        updateShopDiscountState(split[1], IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
                    } else if (StringEnum.SHOP_DISCOUNT_IN.getCode().equals(operation)) {
                        //修改限时折扣活动状态为进行中
                        updateShopDiscountState(split[1], IntegerEnum.COUPON_STATE_START.getCode(), time);
                    } else if (StringEnum.SHOP_DISCOUNT_END.getCode().equals(operation)) {
                        //修改限时折扣活动状态为已结束
                        updateShopDiscountState(split[1], IntegerEnum.COUPON_STATE_END.getCode(), time);
                    } else if (StringEnum.SIGN_NOT_PAY_DELETE.getCode().equals(operation)) {
                        //报名未支付,删除报名数据
                        deleteSign(split[1]);
                    } else if (StringEnum.SCENE_START.getCode().equals(operation)) {
                        //修改场景营销活动状态为进行中
                        updateSceneState(split[1], IntegerEnum.SCEME_STATE_START.getCode(), time);
                    } else if (StringEnum.SCENE_END.getCode().equals(operation)) {
                        //修改场景营销活动状态为已停止
                        updateSceneState(split[1], IntegerEnum.SCEME_STATE_STOP.getCode(), time);
                    } else if (StringEnum.COMPOSE_START.getCode().equals(operation)) {
                        //修改组合捆绑活动状态为进行中
                        updateComposeState(split[1], IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(), time);
                    } else if (StringEnum.COMPOSE_END.getCode().equals(operation)) {
                        //修改组合捆绑活动状态为已停止
                        updateComposeState(split[1], IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(), time);
                    } else if (StringEnum.PRICE_START.getCode().equals(operation)) {
                        //修改定价捆绑活动状态为进行中
                        updatePriceState(split[1], IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(), time);
                    } else if (StringEnum.PRICE_END.getCode().equals(operation)) {
                        //修改定价捆绑活动状态为已停止
                        updatePriceState(split[1], IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(), time);
                    } else if (StringEnum.CHANNEL_ACTIVITY_START.getCode().equals(operation)) {
                        //修改渠道券活动为已开始
                        updateChannelActivityState(split[1], IntegerEnum.TOOL_HAND.getCode(), time);
                    } else if (StringEnum.CHANNEL_ACTIVITY_END.getCode().equals(operation)) {
                        //修改修改渠道券活动为已结束
                        updateChannelActivityState(split[1], IntegerEnum.TOOL_END.getCode(), time);
                    }
                    //删除失效的key
                    redisTemplate.delete(expiredKey);
                    //删除redis延时任务记录
                    cereRedisKeyService.deleteByKey(expiredKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChannelActivityState(String id, Integer state, String time) {
        Long channelActivityId = Long.parseLong(id);
    }

    private void updateSceneState(String id, Integer state, String time) {
        Long sceneId = Long.parseLong(id);
        //查询场景营销活动数据
    }

    private void updateComposeState(String id, Integer state, String time) {
        Long composeId = Long.parseLong(id);
    }

    private void updatePriceState(String id, Integer state, String time) {
        Long priceId = Long.parseLong(id);
        //查询定价捆绑数据
    }

    private void deleteSign(String id) {
        long signId = Long.parseLong(id);
        //删除操作日志
        cereActivitySignService.deleteLogBySignId(signId);
        //根据报名id删除报名数据和明细数据
        cereActivitySignService.deleteById(signId);
        cereActivitySignService.deleteDetailsBySignId(signId);
    }

    private void updateOperateState(String shopOperateId, Integer state) {
        String time = TimeUtils.yyMMddHHmmss();
    }

    private void updateShopGroupWorkState(String id, Integer state, String time) {
        Long shopGroupWorkId = Long.parseLong(id);
    }

    private void updateShopSeckillState(String id, Integer state, String time) {
        Long shopSeckillId = Long.parseLong(id);
        //查询秒杀活动数据
    }

    private void updateShopDiscountState(String id, Integer state, String time) {
        Long shopDiscountId = Long.parseLong(id);
        //查询限时折扣活动数据
    }

    private void updateShopCouponState(String id, Integer state, String time) {
        Long shopCouponId = Long.parseLong(id);
    }

    private void addOperate(String[] split) {
        Long shopOperateId = Long.parseLong(split[1]);
        Long shopCrowdId = Long.parseLong(split[2]);
        Long shopId = Long.parseLong(split[3]);
        cereNoticeService.addOperate(shopCrowdId, null, shopId, shopOperateId);
    }

    private void addAutomaticOperate(String[] split) {
        Long shopOperateId = Long.parseLong(split[1]);
        Long shopCrowdId = Long.parseLong(split[2]);
        Long shopId = Long.parseLong(split[3]);
        //查询运营计划优惠券数据
        cereNoticeService.addOperate(shopCrowdId, null, shopId, shopOperateId);
    }

    private void changeBusinessToken(String businessUserId, String time) throws Exception {
        Long userId = Long.valueOf(businessUserId);
        Business business = cerePlatformBusinessService.getById(userId);
        userRedisService.createToken(business.getUsername(), userId);
    }
}
