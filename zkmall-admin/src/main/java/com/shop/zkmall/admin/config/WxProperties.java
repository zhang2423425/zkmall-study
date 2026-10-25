/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
@Data
@ConfigurationProperties(prefix = "weixin")
public class WxProperties {

    private String appId;

    private String secret;

    private String mchid;

    private String key;

    private String certurl;

    private String apiV3Key;

    private String privateKeyPath;

    private String privateCertPath;

}
