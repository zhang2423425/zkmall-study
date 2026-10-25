package com.shop.zkmall.app.redis.config;

public interface RedisDelayedQueueListener<T> {
    void invoke(T t);
}
