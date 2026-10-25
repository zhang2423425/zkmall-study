/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.shop.zkmall.commons.utils.ImageUtil;
import com.shop.zkmall.oss.alioss.AliOssProperties;
import com.shop.zkmall.oss.alioss.listener.PutObjectProgressListener;
import com.shop.zkmall.oss.alioss.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final OSSClient ossClient;

    private final AliOssProperties aliOssProperties;

    public FileUploadServiceImpl(OSSClient ossClient, AliOssProperties aliOssProperties) {
        this.ossClient = ossClient;
        this.aliOssProperties = aliOssProperties;
    }

    @Override
    public String uploadFile(String filename, InputStream in, long size) throws Exception {
        Objects.requireNonNull(filename);
        filename = filename.replaceAll(" ", "");
        String fileName = aliOssProperties.getUploadBaseDir();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = fileName + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "_" + filename);
        InputStream inputStream = in;
        if (size > 5 * 1024 * 1024) {
            if (filename.contains("jpg") || filename.contains("png")) {
                inputStream = ImageUtil.compressImg(in, 1.1f);
            }
        }
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, inputStream).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));

        if (inputStream != null) {
            inputStream.close();
        }
        if (in != null) {
            in.close();
        }
        return "https://" + aliOssProperties.getBucketName() + "." + aliOssProperties.getEndpoint() + "/" + fileUrl;
    }

    @Override
    public String uploadFile(String filename, byte[] bytes) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "_" + filename);
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(bytes)).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));

        return "https://" + aliOssProperties.getBucketName() + "." + aliOssProperties.getEndpoint() + "/" + fileUrl;
    }

    @Override
    public InputStream getInputStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }
        } catch (IOException e) {
            log.error("获取网络图片出现异常，图片路径为：{}", url, e);
            throw new RuntimeException(e);
        }
        return null;

    }

}
