/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.redis.service;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface UserRedisService {

    /**
     * 保存用户id到redis中
     */
    void saveUser(String token, Long buyerUserId, Long time);

    /**
     * 根据token获取用户id
     */
    Long getBuyerUserIdByToken(String token);

    void refreshToken(String token);

    /**
     * 根据用户查询token
     */
    String getToken(Long userId);

    /**
     * 生成token并且保存到缓存
     *
     * @param username /
     * @param userId   /
     * @return token
     */
    String createToken(String username, Long userId);

    /**
     * 生成token并且以记住登录的方式保存到缓存
     *
     * @param username /
     * @param userId   /
     * @return token
     */
    String createToken4Remember(String username, Long userId);

}
