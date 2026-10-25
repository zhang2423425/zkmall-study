/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.redis.config;

/**
 * 队列事件监听接口，需要实现这个方法
 *
 * @param <T>
 * @author zkmall zkmallvip@163.com
 */
public interface RedisDelayedQueueListener<T> {
    /**
     * 执行方法
     */
    void invoke(T t);
}