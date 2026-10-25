package com.shop.zkmall.app.config;

import com.shop.zkmall.app.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/code/captcha") // 验证码放行
                .excludePathPatterns("/api/code/captcha")
                .excludePathPatterns("/api/app/getCode")
                .excludePathPatterns("/**/*.html")
                .excludePathPatterns("/doc.html","/webjars/**")
                .excludePathPatterns("/ai/**")
                .excludePathPatterns("/v3/api-docs/swagger-config");

    }
}
