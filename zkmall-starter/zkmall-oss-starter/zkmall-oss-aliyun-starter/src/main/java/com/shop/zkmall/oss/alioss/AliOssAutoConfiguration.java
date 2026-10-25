/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss;

import com.aliyun.oss.OSSClient;
import com.shop.zkmall.oss.FileStrategy;
import com.shop.zkmall.oss.alioss.service.FileDownloadService;
import com.shop.zkmall.oss.alioss.service.FileUploadService;
import com.shop.zkmall.oss.alioss.service.GetFileListService;
import com.shop.zkmall.oss.alioss.service.UploadService;
import com.shop.zkmall.oss.alioss.service.impl.AliServiceImpl;
import com.shop.zkmall.oss.alioss.service.impl.FileDownloadServiceImpl;
import com.shop.zkmall.oss.alioss.service.impl.FileUploadServiceImpl;
import com.shop.zkmall.oss.alioss.service.impl.GetFileListServiceImpl;
import com.shop.zkmall.oss.alioss.service.impl.UploadServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zkmall zkmallvip@163.com
 */
@AutoConfiguration
@EnableConfigurationProperties(AliOssProperties.class)
public class AliOssAutoConfiguration {

    private final AliOssProperties aliOssProperties;

    public AliOssAutoConfiguration(AliOssProperties aliOssProperties) {
        this.aliOssProperties = aliOssProperties;
    }

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(aliOssProperties.getEndpoint(), aliOssProperties.getKeyId(), aliOssProperties.getKeySecret());
    }

    @Bean
    @ConditionalOnMissingBean(UploadService.class)
    public UploadService uploadService() {
        return new UploadServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(FileStrategy.class)
    public FileStrategy fileStrategy(FileUploadService fileUploadService) {
        return new AliServiceImpl(fileUploadService);
    }

    @Bean
    @ConditionalOnMissingBean(GetFileListService.class)
    public GetFileListService getFileListService(OSSClient ossClient) {
        return new GetFileListServiceImpl(ossClient, aliOssProperties);
    }

    @Bean
    @ConditionalOnMissingBean(FileUploadService.class)
    public FileUploadService fileUploadService(OSSClient ossClient) {
        return new FileUploadServiceImpl(ossClient, aliOssProperties);
    }

    @Bean
    @ConditionalOnMissingBean(FileDownloadService.class)
    public FileDownloadService fileDownloadService(OSSClient ossClient) {
        return new FileDownloadServiceImpl(ossClient, aliOssProperties);
    }

}
