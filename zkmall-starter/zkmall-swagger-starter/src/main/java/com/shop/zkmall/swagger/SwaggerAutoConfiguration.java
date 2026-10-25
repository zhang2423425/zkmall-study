/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.swagger;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Knife4j整合Swagger3 Api接口文档配置类
 *
 * @author zkmall zkmallvip@163.com
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(swaggerProperties.getTitle())
                        .description(swaggerProperties.getDescription())
                        .version(swaggerProperties.getVersion())
                        .license(new License().name(swaggerProperties.getLicenseName()).url(swaggerProperties.getLicenseUrl())));
    }

    @Bean("knife4jOpenApiCustomizer")
    public Knife4jOpenApiCustomizer knife4jOpenApiCustomizer(Knife4jProperties knife4jProperties, SpringDocConfigProperties docProperties) {
        return new MyKnife4jOpenApiCustomizer(knife4jProperties, docProperties);
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
                // 添加静态资源映射规则
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                //配置 knife4j 的静态资源请求映射地址
                registry.addResourceHandler("/doc.html")
                        .addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }


}
 