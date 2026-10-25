/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.utils;

import com.shop.zkmall.app.redis.component.RedisDelayedQueue;
import com.shop.zkmall.app.redis.dto.TaskBodyDTO;
import com.shop.zkmall.app.redis.listener.DelayQueueConsumeListener;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisDelayedQueue redisDelayedQueue;

    private final CereRedisKeyService cereRedisKeyService;

    private void addDelayQueueInner(String code, String body, String invokeTime) {
        try {
            log.info("addDelayQueueInner start {} {} {}", code, body, invokeTime);
            cereRedisKeyService.add(code + "-" + body, invokeTime);
            Long mills = TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(), invokeTime);
            if (mills < 1) {
                mills = 1L;
            }
            TaskBodyDTO dto = new TaskBodyDTO();
            dto.setCode(code);
            dto.setBody(body);
            redisDelayedQueue.addQueue(dto, mills, TimeUnit.MILLISECONDS, DelayQueueConsumeListener.class.getName());
            log.info("addDelayQueueInner end {}", mills);
        } catch (Exception e) {
            log.error("addDelayQueueInner fail {}", e.getMessage(), e);
        }
    }

    public void addDelayQueue(String code, String body, String invokeTime) {
        addDelayQueueInner(code, body, invokeTime);
    }

    public void addDelayQueue(String code, Long body, String invokeTime) {
        addDelayQueue(code, String.valueOf(body), invokeTime);
    }

    public void deleteAndAddDelayQueue(String code, String body, String invokeTime) {
        removeFromQueue(body, code);
        addDelayQueueInner(code, body, invokeTime);
    }

    public void deleteAndAddDelayQueue(String code, Long body, String invokeTime) {
        deleteAndAddDelayQueue(code, String.valueOf(body), invokeTime);
    }

    public void removeFromQueue(String body, String code) {
        TaskBodyDTO dto = new TaskBodyDTO(code, body);
        redisDelayedQueue.removeFromQueue(dto, DelayQueueConsumeListener.class.getName());
        cereRedisKeyService.deleteByKey(code + "-" + body);
    }

    public void removeFromQueue(Long body, String code) {
        TaskBodyDTO dto = new TaskBodyDTO(code, String.valueOf(body));
        redisDelayedQueue.removeFromQueue(dto, DelayQueueConsumeListener.class.getName());
        cereRedisKeyService.deleteByKey(code + "-" + String.valueOf(body));
    }

    public void removeBatchFromQueue(String body, String... codeArr) {
        for (String code : codeArr) {
            TaskBodyDTO dto = new TaskBodyDTO(code, body);
            redisDelayedQueue.removeFromQueue(dto, DelayQueueConsumeListener.class.getName());
            cereRedisKeyService.deleteByKey(code + "-" + body);
        }
    }
}
