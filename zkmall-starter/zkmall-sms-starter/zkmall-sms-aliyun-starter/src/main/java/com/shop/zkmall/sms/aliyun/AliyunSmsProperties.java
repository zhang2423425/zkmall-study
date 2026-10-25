/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "zkmall.sms.aliyun")
public class AliyunSmsProperties {

    /**
     * 阿里云区域id
     */
    private String regionId;

    /**
     * 阿里云产品名称
     */
    private String name;

    /**
     * 阿里云产品域名
     */
    private String domain;

    /**
     * 阿里云产品keyid
     */
    private String keyId;

    /**
     * 阿里云产品密钥
     */
    private String keySecret;

    /**
     * 阿里云签名
     */
    private String signName;

    /**
     * 阿里云模板编号
     */
    private String template;

}
