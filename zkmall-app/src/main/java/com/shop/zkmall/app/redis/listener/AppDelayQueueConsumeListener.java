/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.redis.listener;

import com.shop.zkmall.app.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.utils.OrderUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.dao.redis.AppTaskBodyDTO;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 监听器
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AppDelayQueueConsumeListener implements RedisDelayedQueueListener<AppTaskBodyDTO> {

    private final OrderUtil orderUtil;

    private final CereRedisKeyService cereRedisKeyService;

    @Override
    public void invoke(AppTaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("invoke app redis Listener {} {}", taskBodyDTO.getCode(), taskBodyDTO.getBody());
        String code = taskBodyDTO.getCode();
        String body = taskBodyDTO.getBody();
        String time = TimeUtils.yyMMddHHmmss();
        try {

            if (StringEnum.ORDER_CONFIRM_DILEVERY.getCode().equals(code)) {
                //修改订单状态为已完成
                orderUtil.updateOrderState(body, time, IntegerEnum.ORDER_FINISH.getCode());
            }
            //删除redis延时任务记录
            cereRedisKeyService.deleteByKey(code + "-" + body);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}