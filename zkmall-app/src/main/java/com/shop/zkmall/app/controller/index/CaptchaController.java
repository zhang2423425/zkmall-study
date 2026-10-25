/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.index;

import com.shop.zkmall.commons.cache.constants.CacheKeyConstants;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import io.springboot.captcha.GifCaptcha;
import io.springboot.captcha.utils.CaptchaJakartaUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/code","/api/code"})
public class CaptchaController {

    private final RedisTemplate<String, Object> redisTemplate;


    @Resource
    private CaptchaService captchaService;

    @PostMapping("/get")
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.get(data);
    }

    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.check(data);
    }

    @PostMapping("/verify")
    public ResponseModel verify(@RequestBody CaptchaVO data) {
        return captchaService.verification(data);
    }

    public static String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        if (StringUtils.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        }
        return null;
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            String captchaKey = request.getParameter("captchaKey");
            // 创建GifCaptcha对象
            GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
            // 输出验证码图片
            CaptchaJakartaUtil.out(gifCaptcha, request, response);
            // 获取验证码文本
            String verCode = gifCaptcha.text().toLowerCase();
            // 将验证码文本保存到session中
            //request.getSession().setAttribute("CAPTCHA", verCode);

            String ip = HttpUtils.getClientIp(request);
            String key = StringUtils.isNotBlank(captchaKey) ? CacheKeyConstants.CAPTCHA_PREFIX + captchaKey : CacheKeyConstants.CAPTCHA_PREFIX + ip;
            redisTemplate.opsForValue().set(key, verCode, Duration.ofMinutes(3));
        } catch (Exception e) {
            log.error("getCaptcha error: {}", e.getMessage(), e);
        }
    }
}
