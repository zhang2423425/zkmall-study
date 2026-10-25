/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.web.aspect;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RepeatSubmitAspect {

    private final RedissonClient redissonClient;

    @Pointcut("@annotation(com.shop.zkmall.web.annotation.NoRepeatSubmit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return pjp.proceed();
        }
        HttpServletRequest request = attributes.getRequest();
        Assert.notNull(request, "request can not null");
        // 此处可以用token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            token = "default";
        }
        String key = "zkmall:repeat:" + token + ":" + request.getServletPath();
        NoRepeatSubmit annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(NoRepeatSubmit.class);
        assert annotation != null;
        long expire = annotation.value();
        //加锁
        RLock lock = redissonClient.getLock(key);
        boolean islock = lock.tryLock(expire, TimeUnit.MILLISECONDS);
        if (islock) {
            Object result;
            try {
                result = pjp.proceed();
            } finally {
                //解锁
                lock.unlock();
            }
            return result;
        } else {
            return new Result<>(CoReturnFormat.REPEAT_REQUEST);
        }
    }
}
