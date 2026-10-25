/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(WebProperties.PREFIX)
public class WebProperties {

    public final static String PREFIX = "zkmall.web";

    /**
     * 是否激活跨域
     */
    private boolean corsEnabled = true;

    /**
     * 日志端类型
     */
    private Integer webLogType;

}
