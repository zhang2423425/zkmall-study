/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.redis.listener;

import com.shop.zkmall.admin.service.product.CereShopProductService;
import com.shop.zkmall.admin.service.redis.CereRedisKeyService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.ProjectInvokeUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * @author zkmall zkmallvip@163.com
 */
//TODO
//@Component
@Slf4j
public class RedisListener extends KeyExpirationEventMessageListener {

    private final RedisTemplate<String, Object> redisTemplate;

    private final CereRedisKeyService cereRedisKeyService;

    private final CereShopProductService cereShopProductService;


    private final ProjectInvokeUtil projectInvokeUtil;

    @Value("${bond_payment_mode}")
    private Integer bondPaymentMode;


    public RedisListener(RedisMessageListenerContainer listenerContainer,
                         RedisTemplate<String, Object> redisTemplate,
                         CereRedisKeyService cereRedisKeyService,
                         CereShopProductService cereShopProductService,
                         ProjectInvokeUtil projectInvokeUtil) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
        this.cereRedisKeyService = cereRedisKeyService;
        this.cereShopProductService = cereShopProductService;
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
                    String code = split[0];
                    /*if(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode().equals(code)){
                        //替换用户token
                        changeUserToken(split[1],time);
                    }else*/
                    if (StringEnum.SHOP_SIGN_IN.getCode().equals(code)) {
                        //修改当前活动状态为报名进行中
                        updateActivityState(split[1], time, IntegerEnum.ACTIVITY_SIGN_ON.getCode());
                    } else if (StringEnum.SHOP_ACTIVITY_STAY.getCode().equals(code)) {
                        //修改当前活动状态为活动待开始
                        updateActivityState(split[1], time, IntegerEnum.ACTIVITY_STAY_START.getCode());
                    } else if (StringEnum.SHOP_ACTIVITY_IN.getCode().equals(code)) {
                        //修改当前活动状态为活动进行中
                        updateActivityState(split[1], time, IntegerEnum.ACTIVITY_START.getCode());
                    } else if (StringEnum.SHOP_ACTIVITY_END.getCode().equals(code)) {
                        //修改当前活动状态为活动已结束
                        updateActivityState(split[1], time, IntegerEnum.ACTIVITY_END.getCode());
                    } else if (StringEnum.THREE_DAY_REFUND_BOND.getCode().equals(code)) {
                        //报名失败3天后自动退款
                        signError(split[1], split[2]);
                    } else if (StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode().equals(code)) {
                        //活动结束15天后自动退款至商家微信
                        signRefund(split[1], split[2]);
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

    private void signRefund(String signId, String signType) {
        int type = Integer.parseInt(signType);

    }

    private void signError(String signId, String signType) {
        int type = Integer.parseInt(signType);
    }

    private void updateActivityState(String id, String time, Integer state) {
        Long activityId = Long.parseLong(id);
    }

    /**
     * 调用退款保证金请求，并且判断如果是支付宝的退款，同步处理
     */
    private void refundBond(CerePayLog bondPayLog, int signType) {

    }

}
