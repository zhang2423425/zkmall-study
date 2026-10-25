package com.shop.zkmall.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScans({@MapperScan("com.shop.zkmall.commons.dao"), @MapperScan("com.shop.zkmall.admin.dao")})
@SpringBootApplication(scanBasePackages = {"com.shop.zkmall.commons.utils", "com.shop.zkmall.admin", "com.shop.zkmall.commons.config"})
public class ZkmallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkmallAdminApplication.class, args);
        log.info("----启动成功----");
    }
}
