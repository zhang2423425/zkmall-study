/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.jwt;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zkmall zkmallvip@163.com
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class JwtAutoConfiguration {

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider();
    }

}
