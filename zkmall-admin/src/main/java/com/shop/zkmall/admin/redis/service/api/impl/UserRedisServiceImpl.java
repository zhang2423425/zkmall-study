/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.redis.service.api.impl;

import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.jwt.SecurityProperties;
import com.shop.zkmall.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl implements UserRedisService {

    private final RedisTemplate redisTemplate;

    private final SecurityProperties securityProperties;

    private final TokenProvider tokenProvider;

    @Override
    public void saveUser(String token, Long userId, Long time) {
        if (!securityProperties.isMultiLogin()) {
            //如果是单点登录
            Object oldTokenKey = redisTemplate.opsForValue().get(securityProperties.getUserLoginKey() + userId);
            //找到用户已经有的tokenkey
            if (oldTokenKey != null) {
                redisTemplate.delete(oldTokenKey);
            }
        }

        redisTemplate.opsForValue().set(token, userId, time, TimeUnit.SECONDS);


        redisTemplate.opsForValue().set(securityProperties.getUserLoginKey() + userId, token);
    }

    @Override
    public Long getBuyerUserIdByToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            Object obj = redisTemplate.opsForValue().get(token);
            if (obj != null) {
                return Long.valueOf(obj.toString());
            }
        }
        return null;
    }

    @Override
    public void refreshToken(String token) {
        redisTemplate.expire(token, securityProperties.getRenew(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String getToken(Long userId) {
        return (String) redisTemplate.opsForValue().get(securityProperties.getUserLoginKey() + userId);
    }

    @Override
    public String createToken(String username, Long userId) {
        String token = tokenProvider.createToken(username);

        //token保存到redis
        this.saveUser(token, userId, securityProperties.getTokenValidityInSeconds());
        return token;
    }

    @Override
    public String createToken4Remember(String username, Long userId) {
        String token = tokenProvider.createToken(username);

        //token保存到redis
        this.saveUser(token, userId, securityProperties.getRememberLoginValidity());
        return token;
    }
}
