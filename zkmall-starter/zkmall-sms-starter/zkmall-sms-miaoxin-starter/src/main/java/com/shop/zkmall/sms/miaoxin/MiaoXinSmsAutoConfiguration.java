/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.miaoxin;

import com.shop.zkmall.sms.strategy.SmsStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zkmall zkmallvip@163.com
 */
@EnableConfigurationProperties(MiaoXinProperties.class)
public class MiaoXinSmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SmsStrategy.class)
    public SmsStrategy smsStrategy(MiaoXinProperties miaoXinProperties) {
        return new MiaoXinSmsStrategy(miaoXinProperties);
    }

}
