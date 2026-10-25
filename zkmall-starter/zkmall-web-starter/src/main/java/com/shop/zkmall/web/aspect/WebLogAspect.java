/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.web.aspect;

import com.shop.zkmall.commons.dao.CerePlatformWebLogDAO;
import com.shop.zkmall.commons.domain.log.CerePlatformWebLog;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.commons.utils.Ip2regionUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import com.shop.zkmall.web.WebProperties;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WebLogAspect {

    private final CerePlatformWebLogDAO cerePlatformWebLogDAO;

    private final WebProperties webProperties;

    /**
     * 定义接口请求开始时间
     */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    @Pointcut("@annotation(com.shop.zkmall.web.annotation.WebRequestLog)")
    public void webLog() {
    }

    /**
     * 设置接口请求起始时间
     */
    @Before("webLog()")
    public void before() {
        START_TIME.set(System.currentTimeMillis());
    }


    @AfterReturning(returning = "result", pointcut = "webLog()")
    public Object after(Result<?> result) throws Throwable {
        try {
            //封装请求记录
            CerePlatformWebLog webLog = new CerePlatformWebLog();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                webLog.setIp(HttpUtils.getClientIp(request));
                webLog.setUrl(request.getServletPath());
                //获取浏览器信息
                String ua = request.getHeader("User-Agent");
                //转成UserAgent对象
                UserAgent userAgent = UserAgent.parseUserAgentString(ua);
                //获取浏览器信息
                Browser browser = userAgent.getBrowser();
                //浏览器名称
                String browserName = browser.getName();
                webLog.setBrowserName(browserName);
            }
            webLog.setCreateTime(TimeUtils.yyMMddHHmmss());
            webLog.setType(webProperties.getWebLogType());
            webLog.setParams(result.getJson());
            webLog.setName(result.getName());
            webLog.setDescribe(result.getDescribe());
            webLog.setIpSource(Ip2regionUtils.getRegion(webLog.getIp()));
            webLog.setTime((int) (System.currentTimeMillis() - START_TIME.get()));
            cerePlatformWebLogDAO.insert(webLog);
        } finally {
            START_TIME.remove();
        }
        return result;
    }
}
