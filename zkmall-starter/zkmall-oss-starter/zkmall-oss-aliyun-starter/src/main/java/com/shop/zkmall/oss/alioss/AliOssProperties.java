/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "zkmall.oss.aliyun")
public class AliOssProperties {

    /**
     * 阿里不同的服务器，地址不同
     */
    private String endpoint;

    /**
     * OSS控制台获取
     */
    private String keyId;

    /**
     * OSS控制台获取
     */
    private String keySecret;

    /**
     * 文件上传路径
     */
    private String uploadBaseDir;

    /**
     * 这个自己创建bucket时的命名，控制台创建也行，代码创建也行
     */
    private String bucketName;

}
