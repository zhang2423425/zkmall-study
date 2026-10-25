/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
public class Ip2regionUtils implements InitializingBean {

    private static Searcher SEARCHER;

    public static void init(Resource resource) throws IOException {
        SEARCHER = Searcher.newWithBuffer(resource.getContentAsByteArray());
    }

    public static String getRegion(String ip) {
        if (SEARCHER == null) {
            log.warn("未初始化IP库!");
            throw new RuntimeException("未初始化IP库数据!");
        }
        try {
            return SEARCHER.search(ip);
        } catch (Exception e) {
            log.error("无法解析{}", ip, e);
        }
        return "未知" + ip;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource resource = new ClassPathResource("ip2region.xdb");
        log.info("初始化IP库:{}", resource.getPath());
        Ip2regionUtils.init(resource);
    }
}
