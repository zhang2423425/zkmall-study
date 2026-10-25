/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_redis_key  redis的过期键信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereRedisKey implements Serializable {
    /**
     * redis的延时任务key
     */
    private String redisKey;

    /**
     * 结束时间（到这个时间就需要业务处理）
     */
    private String endTime;

    /**
     * 所属模块 1-admin 2-business 3-app
     */
    private Integer module;

    private String createTime;

    public static final int MODULE_ADMIN = 1;

    public static final int MODULE_BUSINESS = 2;

    public static final int MODULE_APP = 3;

}
