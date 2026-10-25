/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.redis.listener;

import com.shop.zkmall.admin.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.admin.redis.dto.TaskBodyDTO;
import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.admin.service.redis.CereRedisKeyService;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    private final CerePlatformUserService cerePlatformUserService;

    private final CereRedisKeyService cereRedisKeyService;

    private final UserRedisService userRedisService;

    @Value("${bond_payment_mode}")
    private Integer bondPaymentMode;

    @Override
    public void invoke(TaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("invoke redis Listener {} {}", taskBodyDTO.getCode(), taskBodyDTO.getBody());
        String code = taskBodyDTO.getCode();
        String body = taskBodyDTO.getBody();
        String time = TimeUtils.yyMMddHHmmss();
        List<String> bodyList = new ArrayList<>();
        if (body.contains("-")) {
            bodyList = Stream.of(body.split("-")).toList();
        }
        try {
            /*if (StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode().equals(code)) {
                //替换用户token
                changeUserToken(body, time);
            } else*/
            if (StringEnum.SHOP_SIGN_IN.getCode().equals(code)) {
                //修改当前活动状态为报名进行中
                updateActivityState(body, time, IntegerEnum.ACTIVITY_SIGN_ON.getCode());
            } else if (StringEnum.SHOP_ACTIVITY_STAY.getCode().equals(code)) {
                //修改当前活动状态为活动待开始
                updateActivityState(body, time, IntegerEnum.ACTIVITY_STAY_START.getCode());
            } else if (StringEnum.SHOP_ACTIVITY_IN.getCode().equals(code)) {
                //修改当前活动状态为活动进行中
                updateActivityState(body, time, IntegerEnum.ACTIVITY_START.getCode());
            } else if (StringEnum.SHOP_ACTIVITY_END.getCode().equals(code)) {
                //修改当前活动状态为活动已结束
                updateActivityState(body, time, IntegerEnum.ACTIVITY_END.getCode());
            } else if (StringEnum.THREE_DAY_REFUND_BOND.getCode().equals(code)) {
                //报名失败3天后自动退款
                signError(bodyList.get(0), bodyList.get(1));
            } else if (StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode().equals(code)) {
                //活动结束15天后自动退款至商家微信
                signRefund(bodyList.get(0), bodyList.get(1));
            }
            //删除失效的key
            //redisTemplate.delete(expiredKey);
            //删除redis延时任务记录
            cereRedisKeyService.deleteByKey(code + "-" + body);
            //redisUtil.removeFromQueue(body, code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void signRefund(String signId, String signType) {
        int type = Integer.parseInt(signType);
    }

    private void signError(String signId, String signType) {
        int type = Integer.parseInt(signType);
    }

    private void updateActivityState(String id, String time, Integer state) {
        Long activityId = Long.parseLong(id);
        //查询活动数据
    }

    private void changeUserToken(String platformUserId, String time) {
        long userId = Long.parseLong(platformUserId);
        CerePlatformUser platformUser = cerePlatformUserService.findById(userId);
        userRedisService.createToken(platformUser.getUsername(), userId);
    }

    /**
     * 调用退款保证金请求，并且判断如果是支付宝的退款，同步处理
     */
    private void refundBond(CerePayLog bondPayLog, int signType) {

    }

}