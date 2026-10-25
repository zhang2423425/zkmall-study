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
@ConfigurationProperties(prefix = "wxwork")
public class WxWorkProperties {

    private String suiteId;

    private String suiteSecret;

    private String token;

    private String aesKey;

    private String corpId;

    private String corpSecret;
}
