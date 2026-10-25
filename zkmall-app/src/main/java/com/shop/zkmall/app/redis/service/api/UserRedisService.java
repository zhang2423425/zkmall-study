/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.redis.service.api;

import com.shop.zkmall.app.page.login.TokenInfoBo;

public interface UserRedisService {

    /**
     * 保存用户id到redis中
     */
    void saveUser(String token, String refreshToken, Long buyerUserId);

    /**
     * 根据token获取用户id
     */
    Long getBuyerUserIdByToken(String token);

    /**
     * 根据refreshToken获取用户id
     */
    Long getBuyerUserIdByRefreshToken(String refreshToken);

    /**
     * 刷新token
     */
    TokenInfoBo refreshToken(String token);

    /**
     * 生成token并且保存到缓存
     *
     * @param username /
     * @param userId   /
     * @return token
     */
    TokenInfoBo createToken(String username, Long userId);

}
