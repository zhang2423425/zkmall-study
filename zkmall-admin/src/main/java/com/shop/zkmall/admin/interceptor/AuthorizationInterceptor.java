/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.admin.utils.ContextUtil;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
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

    private final CerePlatformUserService cerePlatformUserService;

    private final SecurityProperties securityProperties;

    private final UserRedisService userRedisService;

    private final EncodeUtil encodeUtil;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        Result<?> result;
        //设置跨域
        setHeader(request,response);
        //从header中获取内部token
        String token = request.getHeader(securityProperties.getHeader());
        log.info("token: {}", token);
        //header==undefined
        if (!EmptyUtils.isEmpty(token) && "undefined".equals(token)) {
            token = "";
        }
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(securityProperties.getHeader());
        }

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");

        //判断当前请求是否需要认证
        String path = request.getServletPath();
        log.info("uri: {}", path);
        // 由于 knife4j-openapi3-jakarta-spring-boot-starter 没办法设置请求的prefix 所以重定向
        if (path.startsWith("/v3/api-docs/swagger-config")) {
            request.getRequestDispatcher("/admin/getSwaggerConfig").forward(request, response);
        }
        ContextUtil.setUri(path);
        boolean needAuth = securityProperties.isNeedAuth(path);

        //token为空
        if (needAuth && ObjectUtil.isEmpty(token)) {
            try {
                writer = response.getWriter();
                result = new Result<>(CoReturnFormat.TOKEN_IS_NULL);
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
        CerePlatformUser user = null;
        if (ObjectUtil.isNotEmpty(token)) {
            try {
                //查询token信息
                Long userId = userRedisService.getBuyerUserIdByToken(token);
                if (ObjectUtil.isNotNull(userId)) {
                    user = cerePlatformUserService.findById(userId);
                }
            } catch (Exception ignored) {
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
            request.setAttribute(USER, user);
            //为每一个线程存储userId
            encodeUtil.setUserId(user.getPlatformUserId());
            if (user.getPlatformUserId() == 1) {
                ContextUtil.setAdmin();
            } else {
                ContextUtil.clearAdmin();
            }
            //token续期
            userRedisService.refreshToken(token);
        }

        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        //super.afterCompletion(request, response, handler, ex);
        ContextUtil.clearAdmin();
        ContextUtil.clearUri();
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
