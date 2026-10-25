package com.shop.zkmall.admin.redis.service;

public interface UserRedisService {
    void saveUser(String token,Long userId,Long time);

    String getToken(Long platformUserId);

    String createToken4Remember(String username, Long platformUserId);

    String createToken(String username, Long platformUserId);
}
