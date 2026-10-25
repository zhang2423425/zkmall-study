/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "zkmall.oss.local")
public class LocalOssProperties {

    private String storagePath;

    private String uriPrefix;

}
