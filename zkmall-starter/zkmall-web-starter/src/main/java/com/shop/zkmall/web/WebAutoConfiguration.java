/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.web;

import com.shop.zkmall.commons.dao.CerePlatformWebLogDAO;
import com.shop.zkmall.web.aspect.RepeatSubmitAspect;
import com.shop.zkmall.web.aspect.WebLogAspect;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zkmall zkmallvip@163.com
 */
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(WebProperties webProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(@NotNull InterceptorRegistry registry) {

            }
        };
    }

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect(RedissonClient redissonClient) {
        return new RepeatSubmitAspect(redissonClient);
    }

    @Bean
    public WebLogAspect webLogAspect(CerePlatformWebLogDAO cerePlatformWebLogDAO, WebProperties webProperties) {
        return new WebLogAspect(cerePlatformWebLogDAO, webProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "cors-enabled", havingValue = "true", matchIfMissing = true)
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
