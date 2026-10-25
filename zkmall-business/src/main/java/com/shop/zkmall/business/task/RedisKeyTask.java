/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.task;

import com.shop.zkmall.business.utils.RedisUtil;
import com.shop.zkmall.commons.domain.redis.CereRedisKey;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * redis延时任务线程类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@Slf4j
public class RedisKeyTask implements Runnable {

    public RedisKeyTask(List<CereRedisKey> keyList, RedisTemplate<String, Object> redisTemplate, RedisUtil redisUtil) {
        this.keyList = keyList;
        this.redisTemplate = redisTemplate;
        this.redisUtil = redisUtil;
    }

    /**
     * 延时数据
     */
    private List<CereRedisKey> keyList;

    /**
     * redis执行
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis延迟队列
     */
    private RedisUtil redisUtil;

    @Override
    public void run() {
        if (!EmptyUtils.isEmpty(keyList)) {
            for (CereRedisKey cereRedisKey : keyList) {
                try {
                    String redisKey = cereRedisKey.getRedisKey();
                    int idx = redisKey.indexOf("-");
                    if (idx != -1) {
                        String code = redisKey.substring(0, idx);
                        String body = redisKey.substring(idx + 1);
                        if (TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(), cereRedisKey.getEndTime())) {
                            //如果当前时间在截至时间之后,需要重新设置延时任务,10秒后立即执行
                            redisUtil.deleteAndAddDelayQueue(code, body, cereRedisKey.getEndTime());
                        } else {
                            //设置延时任务
                            redisUtil.deleteAndAddDelayQueue(code, body, cereRedisKey.getEndTime());
                        }

                            /*if (TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(), cereRedisKey.getEndTime())) {
                                //如果当前时间在截至时间之后,需要重新设置延时任务,10秒后立即执行
                                redisTemplate.opsForValue().set(cereRedisKey.getRedisKey(), 1, 10000);
                            } else {
                                //设置延时任务
                                redisTemplate.opsForValue().set(cereRedisKey.getRedisKey(), 1,
                                        TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(), cereRedisKey.getEndTime()));
                            }*/
                    } else {
                        log.warn("RedisKeyTask redisKey abnormal");
                    }
                } catch (Exception e) {
                    log.error("RedisKeyTask run fail {}", cereRedisKey, e);
                }
            }
        }

    }
}
