/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.redis.impl;

import com.shop.zkmall.app.dao.redis.CereRedisKeyDAO;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.thread.RedisKeyTask;
import com.shop.zkmall.app.utils.RedisUtil;
import com.shop.zkmall.commons.domain.redis.CereRedisKey;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.SpringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereRedisKeyServiceImpl implements CereRedisKeyService {

    private final CereRedisKeyDAO cereRedisKeyDAO;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void add(String key, String endTime) {
        CereRedisKey cereRedisKey = new CereRedisKey();
        cereRedisKey.setRedisKey(key);
        cereRedisKey.setEndTime(endTime);
        cereRedisKey.setModule(CereRedisKey.MODULE_APP);
        String time = TimeUtils.yyMMddHHmmss();
        cereRedisKey.setCreateTime(time);
        cereRedisKeyDAO.insertDelayQueue(cereRedisKey);
    }


    @Override
    public void deleteByKey(String key) {
        cereRedisKeyDAO.deleteByKey(key);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void initializationRedis() {
        //查询所有任务
        List<CereRedisKey> list = cereRedisKeyDAO.findAll();
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        if (!EmptyUtils.isEmpty(list)) {
            //将数据拆分成多个小的集合，开启多线程设置
            List<List<CereRedisKey>> split = EmptyUtils.split(list, 50);
            if (!EmptyUtils.isEmpty(split)) {
                split.forEach(keys -> {
                    new Thread(new RedisKeyTask(keys, redisTemplate, redisUtil)).start();
                });
            }
        }
    }

    @Override
    public String findByKey(String key) {
        return cereRedisKeyDAO.findByKey(key);
    }
}
