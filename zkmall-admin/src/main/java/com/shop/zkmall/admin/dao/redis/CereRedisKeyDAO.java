/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.redis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.redis.CereRedisKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereRedisKeyDAO extends BaseMapper<CereRedisKey> {

    //int insertSelective(CereRedisKey record);

    //void updateByKey(CereRedisKey cereRedisKey);

    void deleteByKey(@Param("key") String key);

    List<CereRedisKey> findAll();

    CereRedisKey findByKey(@Param("key") String key);

    void insertDelayQueue(CereRedisKey cereRedisKey);
}
