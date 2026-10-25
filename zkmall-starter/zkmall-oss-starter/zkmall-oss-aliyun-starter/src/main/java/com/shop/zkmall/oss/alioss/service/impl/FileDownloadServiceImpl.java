/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.shop.zkmall.oss.alioss.AliOssProperties;
import com.shop.zkmall.oss.alioss.listener.GetObjectProgressListener;
import com.shop.zkmall.oss.alioss.service.FileDownloadService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

public class FileDownloadServiceImpl implements FileDownloadService {

    private final AliOssProperties aliOssProperties;

    private final OSSClient ossClient;

    public FileDownloadServiceImpl(OSSClient ossClient, AliOssProperties aliOssProperties) {
        this.aliOssProperties = aliOssProperties;
        this.ossClient = ossClient;
    }

    @Override
    public void downloadThis(String filename, String fileUrl) {
        fileUrl = fileUrl + "/" + filename.substring(filename.lastIndexOf("-") + 1);
        ossClient.getObject(new GetObjectRequest(aliOssProperties.getBucketName(), filename).
                        withProgressListener(new GetObjectProgressListener()),
                new File(fileUrl));
    }

    @Override
    public void downloadByStream(String filename) throws Exception {
        OSSObject ossObject = ossClient.getObject(aliOssProperties.getBucketName(), filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
        }
        reader.close();
    }

    @Override
    public URL getUrl(String key) {
        Date expiration = new Date(new Date().getTime() + 3600 * 10000);
        URL url = ossClient.generatePresignedUrl(aliOssProperties.getBucketName(), key, expiration);
        return url;
    }
}
