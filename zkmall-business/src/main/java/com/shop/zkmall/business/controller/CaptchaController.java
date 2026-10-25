/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.commons.utils.HttpUtils;
import io.springboot.captcha.GifCaptcha;
import io.springboot.captcha.utils.CaptchaJakartaUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CaptchaController {

    private final RedisTemplate<String, Object> redisTemplate;

    public static final String CAPTCHA_PREFIX = "captcha:business:";

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 创建GifCaptcha对象
            GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
            // 输出验证码图片
            CaptchaJakartaUtil.out(gifCaptcha, request, response);
            // 获取验证码文本
            String verCode = gifCaptcha.text().toLowerCase();

            String ip = HttpUtils.getClientIp(request);
            redisTemplate.opsForValue().set(CAPTCHA_PREFIX + ip, verCode);
        } catch (Exception e) {
            log.error("getCaptcha error: {}", e.getMessage(), e);
        }
    }
}
