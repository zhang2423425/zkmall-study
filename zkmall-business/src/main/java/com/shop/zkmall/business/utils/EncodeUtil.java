/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.utils;

import com.shop.zkmall.commons.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EncodeUtil {

    static ThreadLocal<Long> userIdTL = new ThreadLocal<>();

    private final RedisTemplate<String, Object> redisTemplate;

    public void setUserId(Long userId) {
        userIdTL.set(userId);
    }

    private static final String PRIVACY_SHOW_SWITCH = "business_privacy_show_switch_";

    public String encodePhone(String phone) {
        Long privacyShowSwitch = (Long) redisTemplate.opsForValue().get(PRIVACY_SHOW_SWITCH + userIdTL.get());
        if (privacyShowSwitch != null && privacyShowSwitch > 0) {
            return phone;
        }
        return StringUtils.encodePhone(phone);
    }

    public String encodeInfo(String info) {
        Long privacyShowSwitch = (Long) redisTemplate.opsForValue().get(PRIVACY_SHOW_SWITCH + userIdTL.get());
        if (privacyShowSwitch != null && privacyShowSwitch > 0) {
            return info;
        }
        return StringUtils.encodeInfo(info);
    }

}
