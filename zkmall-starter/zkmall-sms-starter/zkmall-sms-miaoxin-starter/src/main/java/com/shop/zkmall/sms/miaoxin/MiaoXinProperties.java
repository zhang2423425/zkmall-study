/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.miaoxin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "zkmall.sms.miaoxinyun")
public class MiaoXinProperties {

    /**
     * 秒信云账号app_id
     */
    private String account;

    /**
     * 秒信云密钥
     */
    private String secret;

    /**
     * 秒信云域名
     */
    private String domain;

    /**
     * 秒信云签名
     */
    private String signName;

    /**
     * 秒信云模板
     */
    private String template;

}
