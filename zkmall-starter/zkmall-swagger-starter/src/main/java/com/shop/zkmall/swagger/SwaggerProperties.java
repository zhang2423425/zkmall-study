/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "zkmall.swagger";

    /**
     * 是否启用
     */
    private boolean enabled = true;

    private String title = null;

    private String description = null;

    private String termsOfService = null;

    private String licenseName = "Apache2.0";

    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.txt";

    private String version;

}
