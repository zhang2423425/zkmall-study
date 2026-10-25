/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app;

//import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zkmall zkmallvip@163.com
 */

@Slf4j
@EnableAsync(proxyTargetClass = true)
@RequiredArgsConstructor
@MapperScans({@MapperScan("com.shop.zkmall.commons.dao"), @MapperScan("com.shop.zkmall.app.dao")})
@SpringBootApplication(scanBasePackages = {"com.shop.zkmall.commons.utils", "com.shop.zkmall.app", "com.shop.zkmall.commons.config", "com.shop.zkmall.commons.dao","com.shop.zkmall.xxljob"})
@EnableCaching
public class MyCereshopAppApplication extends SpringBootServletInitializer implements CommandLineRunner {

//    private final CereRedisKeyService cereRedisKeyService;

    /**
     * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
     */
    @Override  //重写configure方法，把springboot的入口给它
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyCereshopAppApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyCereshopAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        cereRedisKeyService.initializationRedis();
        log.info("------   app-server启动成功    ------");

    }
}
