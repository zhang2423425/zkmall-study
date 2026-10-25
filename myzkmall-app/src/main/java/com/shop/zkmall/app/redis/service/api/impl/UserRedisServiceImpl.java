/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.redis.service.api.impl;

import com.shop.zkmall.app.page.login.TokenInfoBo;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.jwt.SecurityProperties;
import com.shop.zkmall.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zkmall zkmallvip@163.com
 */
@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl implements UserRedisService {

    private final RedisTemplate redisTemplate;

    private final SecurityProperties securityProperties;

    private final TokenProvider tokenProvider;

    @Override
    public void saveUser(String token, String refreshToken, Long buyerUserId) {
        String tokenKey = securityProperties.getUserLoginKey() + buyerUserId;
        //由于旧的key对应的是字符串，所以需要做个兼容
        if (redisTemplate.type(tokenKey).equals(DataType.STRING)) {
            redisTemplate.delete(tokenKey);
        }
        if (!securityProperties.isMultiLogin()) {
            Set<String> combineTokenSet = redisTemplate.opsForSet().members(tokenKey);
            if (CollectionUtils.isNotEmpty(combineTokenSet)) {
                for (String combineToken : combineTokenSet) {
                    String[] arr = combineToken.split(":");
                    redisTemplate.delete(arr[0]);
                    redisTemplate.delete(arr[1]);
                }
            }
        }
        redisTemplate.opsForValue().set(token, buyerUserId, securityProperties.getTokenValidityInSeconds(), TimeUnit.SECONDS);

        redisTemplate.opsForValue().set(refreshToken, buyerUserId, securityProperties.getRefreshTokenValidityInSeconds(), TimeUnit.SECONDS);

        //如果有对应的accessToken 或者 refreshToken过期，需要删除掉
        Set<String> combineTokenSet = redisTemplate.opsForSet().members(tokenKey);
        if (CollectionUtils.isNotEmpty(combineTokenSet)) {
            for (String combineToken : combineTokenSet) {
                String[] arr = combineToken.split(":");
                if (!(redisTemplate.hasKey(arr[0]) || redisTemplate.hasKey(arr[1]))) {
                    redisTemplate.opsForSet().remove(tokenKey, combineToken);
                }
            }
        }
        redisTemplate.opsForSet().add(tokenKey, token + ":" + refreshToken);
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
    public Long getBuyerUserIdByRefreshToken(String refreshToken) {
        if (StringUtils.isNotBlank(refreshToken)) {
            Object obj = redisTemplate.opsForValue().get(refreshToken);
            if (obj != null) {
                return Long.valueOf(obj.toString());
            }
        }
        return null;
    }

    @Override
    public TokenInfoBo refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new CoBusinessException(CoReturnFormat.REFRESH_TOKEN_APPROVE_ERROR);
        }
        Object obj = redisTemplate.opsForValue().get(refreshToken);
        if (obj != null) {
            Long buyerUserId = Long.parseLong(obj.toString());
            redisTemplate.delete(refreshToken);
            return createToken(buyerUserId.toString(), buyerUserId);
        } else {
            throw new CoBusinessException(CoReturnFormat.REFRESH_TOKEN_APPROVE_ERROR);
        }
    }

    @Override
    public TokenInfoBo createToken(String username, Long userId) {
        String token = tokenProvider.createToken(username);
        String refreshToken = tokenProvider.createRefreshToken(username);
        this.saveUser(token, refreshToken, userId);
        return new TokenInfoBo(userId, token, refreshToken);
    }
}
