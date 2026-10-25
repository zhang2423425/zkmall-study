/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin;

import com.shop.zkmall.admin.service.redis.CereRedisKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RequiredArgsConstructor
@EnableAsync
@MapperScans({@MapperScan("com.shop.zkmall.commons.dao"), @MapperScan("com.shop.zkmall.admin.dao")})
@SpringBootApplication(scanBasePackages = {"com.shop.zkmall.commons.utils", "com.shop.zkmall.admin", "com.shop.zkmall.commons.config"})
public class CereshopAdminApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private final CereRedisKeyService cereRedisKeyService;

    /**
     * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
     */
    @Override  //重写configure方法，把springboot的入口给它
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CereshopAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CereshopAdminApplication.class, args);
    }

    @Override
    public void run(String... args) {
        cereRedisKeyService.initializationRedis();
        log.info("------   admin-server启动成功    ------");
    }
}
