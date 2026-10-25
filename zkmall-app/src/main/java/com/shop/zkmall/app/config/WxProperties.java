/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "weixin")
public class WxProperties {

    private String appid;

    private String secret;

    private String mchid;

    private String key;

    private String certurl;

    private String apiV3Key;

    private String privateKeyPath;

    private String privateCertPath;

    private String liveTemplateId;

    private String liveAppId;
}
