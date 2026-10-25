/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.local;

import com.shop.zkmall.oss.FileStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zkmall zkmallvip@163.com
 */
@AutoConfiguration
@EnableConfigurationProperties(LocalOssProperties.class)
public class LocalOssAutoConfiguration {

    @Autowired
    private LocalOssProperties localOssProperties;

    @Bean
    @ConditionalOnMissingBean(FileStrategy.class)
    public FileStrategy fileStrategy() {
        return new LocalServiceImpl(localOssProperties);
    }

}
