package com.shop.zkmall.admin.redis.service.api.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.jwt.SecurityProperties;
import com.shop.zkmall.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl implements UserRedisService {
    private final RedisTemplate redisTemplate;

    private final SecurityProperties securityProperties;

    private final TokenProvider tokenProvider;


    @Override
    public void saveUser(String token, Long userId, Long time) {
        if (!securityProperties.isMultiLogin()){
            //同一个用户的不同设备是相同账号，查到老的token，删除
            String tokenKey = (String)redisTemplate.opsForValue().get(securityProperties.getUserLoginKey() + userId);
            if (ObjectUtil.isNotNull(tokenKey)){
                redisTemplate.delete(tokenKey);
            }
            //用户持有的token
            redisTemplate.opsForValue().set(securityProperties.getUserLoginKey()+userId,token);
            //一个token保持的用户
            redisTemplate.opsForValue().set(token,userId,time, TimeUnit.SECONDS);

        }

    }

    @Override
    public String getToken(Long platformUserId) {
        return (String) redisTemplate.opsForValue().get(securityProperties.getUserLoginKey()+platformUserId);
    }

    @Override
    public String createToken4Remember(String username, Long platformUserId) {
        String token = tokenProvider.createToken(username);
        saveUser(token,platformUserId,securityProperties.getRememberLoginValidity());
        return token;
    }

    @Override
    public String createToken(String username, Long platformUserId) {

        String token = tokenProvider.createToken(username);
        saveUser(token,platformUserId,securityProperties.getTokenValidityInSeconds());
        return token;
    }
}
