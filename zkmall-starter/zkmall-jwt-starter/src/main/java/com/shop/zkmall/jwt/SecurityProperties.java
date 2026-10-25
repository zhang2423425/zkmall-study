/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.jwt;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * Jwt参数配置
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "security.jwt")
@Component
public class SecurityProperties {

    /**
     * Request Headers ： Authorization
     */
    private String header;

    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenStartWith;

    /**
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String base64Secret;

    /**
     * 令牌过期时间 此处单位/秒
     */
    private Long tokenValidityInSeconds;

    /**
     * refreshToken 过期时间 此处单位/秒
     */
    private Long refreshTokenValidityInSeconds;

    /**
     * 记住我登录 令牌过期时间
     */
    private Long rememberLoginValidity;

    /**
     * 在线用户 key，根据 key 查询 redis 中在线用户的数据
     */
    private String onlineKey;

    /**
     * 验证码 key
     */
    private String codeKey;

    /**
     * token 续期检查
     */
    private Long detect;

    /**
     * 续期时间
     */
    private Long renew;

    /**
     * 用户登录存放redis key
     */
    private String userLoginKey;

    /**
     * 一个账户是否允许多个客户端登录
     */
    private boolean multiLogin;

    /**
     * 登录拦截器放行请求路径
     */
    private List<String> ignorePaths;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }

    /**
     * 判断当前路径是否需要认证
     *
     * @param path 路径
     */
    public boolean isNeedAuth(String path) {

        boolean needAuth = true;
        PathMatcher matcher = new AntPathMatcher();
        if (ObjectUtil.isNotEmpty(this.getIgnorePaths())) {
            for (String ignorePath : this.getIgnorePaths()) {
                if (matcher.match(ignorePath, path)) {
                    needAuth = false;
                }
            }
        }
        return needAuth;
    }

}
