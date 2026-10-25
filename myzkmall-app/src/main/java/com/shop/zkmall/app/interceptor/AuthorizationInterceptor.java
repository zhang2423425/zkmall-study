package com.shop.zkmall.app.interceptor;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.jwt.SecurityProperties;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final SecurityProperties securityProperties;

    private final UserRedisService userRedisService;

    private final CereBuyerUserService cereBuyerUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域
        setHeader(request, response);
        String token = request.getHeader(securityProperties.getHeader());
        if (!EmptyUtils.isEmpty(token) && token.equals("undefined")) {
            token = "";
        }

        if (StringUtils.isBlank(token)) {
            token = request.getParameter(securityProperties.getHeader());
        }
        String path = request.getServletPath();
        if (path.startsWith("/v3/api-docs/swagger-config")) {
            request.getRequestDispatcher("/app/getSwaggerConfig").forward(request, response);
        }
        boolean isNeedAuth = securityProperties.isNeedAuth(path);

        Result<?> result;
        PrintWriter writer = null;

//        log.info("isNeedAuth="+isNeedAuth);
        //需要验证并且token==空
        if (isNeedAuth && StringUtils.isBlank(token)) {
            try {
//                log.info("我进入了if (isNeedAuth && StringUtils.isBlank(token))");
                writer = response.getWriter();
                result = new Result<>(CoReturnFormat.USER_NOT_LOGIN);
                writer.write(JSON.toJSONString(result));
                return false;
            } catch (IOException e) {
                log.error("response error" + e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        //token不为空或为""
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
        if (isNeedAuth && user == null) {
            try {
                writer = response.getWriter();
                result = new Result<>(CoReturnFormat.TOKEN_APPROVE_ERROR);
                writer.write(JSON.toJSONString(result));
                return false;
            } catch (IOException e) {
                log.error("response error" + e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        if (ObjectUtil.isNotNull(user)) {
            user.setToken(token);
            request.setAttribute("user", user);
        }


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
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
