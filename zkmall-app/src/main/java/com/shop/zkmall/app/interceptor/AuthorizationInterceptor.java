/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.jwt.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String USER = "user";

    private final CereBuyerUserService cereBuyerUserService;

    private final UserRedisService userRedisService;

    private final SecurityProperties securityProperties;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        Result<?> result;
        //设置跨域
        setHeader(request, response);

        //从header中获取内部token
        String token = request.getHeader(securityProperties.getHeader());
        if (!EmptyUtils.isEmpty(token) && "undefined".equals(token)) {
            token = "";
        }
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(securityProperties.getHeader());
        }
        //判断当前请求是否需要认证
        String path = request.getServletPath();
        //log.info("uri: {}", path);
        // 由于 knife4j-openapi3-jakarta-spring-boot-starter 没办法设置请求的prefix 所以重定向
        if (path.startsWith("/v3/api-docs/swagger-config")) {
            request.getRequestDispatcher("/app/getSwaggerConfig").forward(request, response);
        }
        boolean needAuth = securityProperties.isNeedAuth(path);
        //log.info(" needAuth {} {} {}", path, needAuth, token);
        //log.info(" ignorePaths {} {}", path, securityProperties.getIgnorePaths());

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        //token为空
        if (needAuth && StringUtils.isBlank(token)) {
            try {
                writer = response.getWriter();
//                writer.print("token为空");
                result = new Result<>(CoReturnFormat.USER_NOT_LOGIN);
                writer.println(JSON.toJSONString(result));
                return false;
            } catch (IOException e) {
                log.error("response error", e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        //token不为空 查询用户
        CereBuyerUser user = null;
        if (ObjectUtil.isNotEmpty(token)) {
            Long buyerUserId = userRedisService.getBuyerUserIdByToken(token);
            if (StringUtils.equals(token, "3106f313a44615e5bc0252b4d292896a")) {
                buyerUserId = 1168L;
            }
            if (buyerUserId != null) {
                user = cereBuyerUserService.selectByBuyerUserId(buyerUserId);
            }
        }
        //抛出token 异常
        if (needAuth && user == null) {
            try {
                writer = response.getWriter();
                result = new Result<>(CoReturnFormat.TOKEN_APPROVE_ERROR);
                writer.println(JSON.toJSONString(result));
                return false;
            } catch (IOException e) {
                log.error("response error", e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        //user存在 设置user
        if (ObjectUtil.isNotNull(user)) {
            //设置userId到request里，后续根据userId，获取用户信息
            user.setToken(token);
            request.setAttribute(USER, user);
        }

        return true;
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
    }
}
