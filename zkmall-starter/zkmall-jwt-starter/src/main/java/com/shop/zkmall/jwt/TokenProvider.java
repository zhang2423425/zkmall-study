/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.jwt;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Configuration
public class TokenProvider implements InitializingBean {

    @Autowired
    private SecurityProperties properties;

//    @Autowired
//    private  RedisTemplate<String, Object> redisTemplate;

    public static final String AUTHORITIES_KEY = "user";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;


    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     * 创建Token 设置永不过期，
     *
     * @param username /
     * @return /
     */
    public String createToken(String username) {
        return properties.getTokenStartWith() + jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                .claim(AUTHORITIES_KEY, username)
                .setSubject(username)
                .compact();
    }

    /**
     * 创建refreshToken
     */
    public String createRefreshToken(String username) {
        return properties.getTokenStartWith() + jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                .claim(AUTHORITIES_KEY, username)
                .setSubject(username)
                .compact();
    }


    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    String getTokenInfo(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

//    /**
//     * @com.shop.zkmall.admin.param token 需要检查的token
//     */
//    public void checkRenewal(String token) {
//        // 判断是否续期token,计算token的过期时间
//        long time = redisTemplate.getExpire(properties.getOnlineKey() + token) * 1000;
//        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
//        // 判断当前时间与过期时间的时间差
//        long differ = expireDate.getTime() - System.currentTimeMillis();
//        // 如果在续期检查的范围内，则续期
//        if (differ <= properties.getDetect()) {
//            long renew = time + properties.getRenew();
//            redisTemplate.expire(properties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
//        }
//    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

    public String getToken(String token) {
        if (token != null && token.startsWith(properties.getTokenStartWith())) {
            return token.substring(7);
        }
        return null;
    }
}
