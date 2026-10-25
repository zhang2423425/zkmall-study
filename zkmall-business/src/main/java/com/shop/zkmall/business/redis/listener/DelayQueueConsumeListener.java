/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.redis.listener;

import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.business.redis.dto.TaskBodyDTO;
import com.shop.zkmall.business.redis.service.api.UserRedisService;
import com.shop.zkmall.business.service.activity.CereActivitySignService;
import com.shop.zkmall.business.service.business.CerePlatformBusinessService;
import com.shop.zkmall.business.service.notice.CereNoticeService;
import com.shop.zkmall.business.service.redis.CereRedisKeyService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 监听器
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayQueueConsumeListener implements RedisDelayedQueueListener<TaskBodyDTO> {

    private final CerePlatformBusinessService cerePlatformBusinessService;

    private final CereRedisKeyService cereRedisKeyService;

    private final CereNoticeService cereNoticeService;

    private final CereActivitySignService cereActivitySignService;

    private final UserRedisService userRedisService;

    @Override
    public void invoke(TaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("invoke redis Listener {} {}", taskBodyDTO.getCode(), taskBodyDTO.getBody());
        String code = taskBodyDTO.getCode();
        String body = taskBodyDTO.getBody();
        String time = TimeUtils.yyMMddHHmmss();
        List<String> bodyList = new ArrayList<>();
        if (body.contains("-")) {
            bodyList = Stream.of(body.split("-")).collect(Collectors.toList());
        }
        try {
            if (StringEnum.NOTICE_OPERATE.getCode().equals(code)) {
                //手动计划运营计划定时发送消息通知
                addOperate(bodyList);
            } else if (StringEnum.NOTICE_OPERATE_AUTOMATIC.getCode().equals(code)) {
                //自动计划运营计划定时发送消息通知
                addAutomaticOperate(bodyList);
            } else if (StringEnum.OPERATE_STATE_STAY.getCode().equals(code)) {
                //修改运营计划状态为进行中
                updateOperateState(body, IntegerEnum.COUPON_STATE_START.getCode());
            } else if (StringEnum.OPERATE_STATE_STOP.getCode().equals(code)) {
                //修改运营计划状态为已结束
                updateOperateState(body, IntegerEnum.COUPON_STATE_END.getCode());
            } else if (StringEnum.SHOP_COUPON_IN.getCode().equals(code)) {
                //修改店铺优惠券状态为进行中
                updateShopCouponState(body, IntegerEnum.COUPON_STATE_START.getCode(), time);
            } else if (StringEnum.SHOP_COUPON_END.getCode().equals(code)) {
                //修改店铺优惠券状态为已结束
                updateShopCouponState(body, IntegerEnum.COUPON_STATE_END.getCode(), time);
            } else if (StringEnum.SHOP_GROUP_WORK_PRE_HOT.getCode().equals(code)) {
                //修改拼团活动状态为预热中
                log.info("redisListener shopGroupPreHot {}", body);
                updateShopGroupWorkState(body, IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
            } else if (StringEnum.SHOP_GROUP_WORK_IN.getCode().equals(code)) {
                //修改拼团活动状态为进行中
                updateShopGroupWorkState(body, IntegerEnum.COUPON_STATE_START.getCode(), time);
            } else if (StringEnum.SHOP_GROUP_WORK_END.getCode().equals(code)) {
                //修改拼团活动状态为已结束
                updateShopGroupWorkState(body, IntegerEnum.COUPON_STATE_END.getCode(), time);
            } else if (StringEnum.SHOP_SECKILL_PRE_HOT.getCode().equals(code)) {
                //修改秒杀活动状态为预热中
                log.info("redisListener shopSeckillPreHot {}", body);
                updateShopSeckillState(body, IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
            } else if (StringEnum.SHOP_SECKILL_IN.getCode().equals(code)) {
                //修改秒杀活动状态为进行中
                updateShopSeckillState(body, IntegerEnum.COUPON_STATE_START.getCode(), time);
            } else if (StringEnum.SHOP_SECKILL_END.getCode().equals(code)) {
                //修改秒杀活动状态为已结束
                updateShopSeckillState(body, IntegerEnum.COUPON_STATE_END.getCode(), time);
            } else if (StringEnum.SHOP_DISCOUNT_PRE_HOT.getCode().equals(code)) {
                //修改限时折扣活动状态为预热中
                log.info("redisListener shopDiscountPreHot {}", body);
                updateShopDiscountState(body, IntegerEnum.COUPON_STATE_PRE_HOT.getCode(), time);
            } else if (StringEnum.SHOP_DISCOUNT_IN.getCode().equals(code)) {
                //修改限时折扣活动状态为进行中
                updateShopDiscountState(body, IntegerEnum.COUPON_STATE_START.getCode(), time);
            } else if (StringEnum.SHOP_DISCOUNT_END.getCode().equals(code)) {
                //修改限时折扣活动状态为已结束
                updateShopDiscountState(body, IntegerEnum.COUPON_STATE_END.getCode(), time);
            } else if (StringEnum.SIGN_NOT_PAY_DELETE.getCode().equals(code)) {
                //报名未支付,删除报名数据
                deleteSign(body);
            } else if (StringEnum.SCENE_START.getCode().equals(code)) {
                //修改场景营销活动状态为进行中
                updateSceneState(body, IntegerEnum.SCEME_STATE_START.getCode(), time);
            } else if (StringEnum.SCENE_END.getCode().equals(code)) {
                //修改场景营销活动状态为已停止
                updateSceneState(body, IntegerEnum.SCEME_STATE_STOP.getCode(), time);
            } else if (StringEnum.COMPOSE_START.getCode().equals(code)) {
                //修改组合捆绑活动状态为进行中
                updateComposeState(body, IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(), time);
            } else if (StringEnum.COMPOSE_END.getCode().equals(code)) {
                //修改组合捆绑活动状态为已停止
                updateComposeState(body, IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(), time);
            } else if (StringEnum.PRICE_START.getCode().equals(code)) {
                //修改定价捆绑活动状态为进行中
                updatePriceState(body, IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(), time);
            } else if (StringEnum.PRICE_END.getCode().equals(code)) {
                //修改定价捆绑活动状态为已停止
                updatePriceState(body, IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(), time);
            } else if (StringEnum.CHANNEL_ACTIVITY_START.getCode().equals(code)) {
                //修改渠道券活动为已开始
                updateChannelActivityState(body, IntegerEnum.TOOL_HAND.getCode(), time);
            } else if (StringEnum.CHANNEL_ACTIVITY_END.getCode().equals(code)) {
                //修改修改渠道券活动为已结束
                updateChannelActivityState(body, IntegerEnum.TOOL_END.getCode(), time);
            }
            //删除失效的key
            //redisTemplate.delete(expiredKey);
            //删除redis延时任务记录
            cereRedisKeyService.deleteByKey(code + "-" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChannelActivityState(String id, Integer state, String time) {
        Long channelActivityId = Long.parseLong(id);
    }

    private void updateSceneState(String id, Integer state, String time) {
        Long sceneId = Long.parseLong(id);
    }

    private void updateComposeState(String id, Integer state, String time) {
        Long composeId = Long.parseLong(id);
        //查询组合捆绑数据
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
        //查询拼团活动数据
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
        //查询店铺优惠券数据
    }

    private void addOperate(List<String> bodyList) {
        Long shopOperateId = Long.parseLong(bodyList.get(0));
        Long shopCrowdId = Long.parseLong(bodyList.get(1));
        Long shopId = Long.parseLong(bodyList.get(2));
        //查询运营计划优惠券数据
        cereNoticeService.addOperate(shopCrowdId, null, shopId, shopOperateId);
    }

    private void addAutomaticOperate(List<String> bodyList) {
        Long shopOperateId = Long.parseLong(bodyList.get(0));
        Long shopCrowdId = Long.parseLong(bodyList.get(1));
        Long shopId = Long.parseLong(bodyList.get(2));
        cereNoticeService.addOperate(shopCrowdId, null, shopId, shopOperateId);
    }

    private void changeBusinessToken(String businessUserId, String time) throws Exception {
        Long userId = Long.valueOf(businessUserId);
        Business business = cerePlatformBusinessService.getById(userId);
        userRedisService.createToken(business.getUsername(), userId);
    }

}