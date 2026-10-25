/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.redis;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereRedisKeyService {

    void add(String key, String endTime);

    void deleteByKey(String key);

    void initializationRedis();

    String findByKey(String key);
}
