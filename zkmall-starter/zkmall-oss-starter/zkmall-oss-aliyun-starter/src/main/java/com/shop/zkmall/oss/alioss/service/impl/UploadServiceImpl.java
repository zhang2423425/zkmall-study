/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.shop.zkmall.oss.alioss.AliOssProperties;
import com.shop.zkmall.oss.alioss.listener.PutObjectProgressListener;
import com.shop.zkmall.oss.alioss.service.FileDownloadService;
import com.shop.zkmall.oss.alioss.service.GetFileListService;
import com.shop.zkmall.oss.alioss.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private GetFileListService getFileListService;

    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliOssProperties aliOssProperties;


    @Override
    public void uploadFile(File file) {
        // 创建OSSClient实例。
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, file).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public void uploadStream(File file) throws Exception {
        // 创建OSSClient实例。
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
        // 上传文件流。
        InputStream inputStream = new FileInputStream(file);
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, inputStream).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public void uploadStream(String filename, InputStream inputStream) {
        // 创建OSSClient实例。
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + filename);
        // 上传文件流。
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, inputStream).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public void uploadInternetStream(File file) throws Exception {
        // 创建OSSClient实例。
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
        // 上传网络流。
        InputStream inputStream = new URL("https://www.aliyun.com/").openStream();
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, inputStream).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public void uploadByte(File file) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
        // 上传Byte数组。
        byte[] content = "Hello OSS".getBytes();
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(content)).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null, 0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public String uploadByte(String filename, byte[] content) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + filename);
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(content)).
                withProgressListener(new PutObjectProgressListener(null, 0)));
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
        String prefix = fileUrl.substring(0, fileUrl.indexOf("."));
        List<OSSObjectSummary> list = getFileByPrefix(prefix);
        if (!CollectionUtils.isEmpty(list)) {
            URL url = getUrl(list.get(0).getKey());
            if ("http".equals(url.getProtocol())) {
                return url.getProtocol() + "s://" + url.getHost() + url.getPath();
            } else {
                return url.getProtocol() + "://" + url.getHost() + url.getPath();
            }
        }
        return null;
    }

    public List<OSSObjectSummary> getFileByPrefix(String prefix) {
        // 列举包含指定前缀的文件。默认列举100个文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).withPrefix(prefix));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    public URL getUrl(String key) {
        //设置过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 10000);
        //生成URL
        URL url = ossClient.generatePresignedUrl(aliOssProperties.getBucketName(), key, expiration);
        return url;
    }

    @Override
    public void uploadByte(String filename, byte[] content, HttpServletRequest request, long total) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + filename);
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(content)).
                withProgressListener(new PutObjectProgressListener(request, total)));
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public String uploadByteReturnUrl(String filename, byte[] content, HttpServletRequest request, long total) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + filename);
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(content)).
                withProgressListener(new PutObjectProgressListener(request, total)));
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
        String prefix = fileUrl.substring(0, fileUrl.indexOf("."));
        List<OSSObjectSummary> list = getFileListService.getFileByPrefix(prefix);
        if (!CollectionUtils.isEmpty(list)) {
            URL url = fileDownloadService.getUrl(list.get(0).getKey());
            return url.getProtocol() + "://" + url.getHost() + url.getPath();
        }
        return null;
    }

    @Override
    public void uploadString(File file) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
        String content = "Hello OSS";
        ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, new ByteArrayInputStream(content.getBytes())).
                withProgressListener(new PutObjectProgressListener(null, 0)));
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

    @Override
    public void uploadFile(String filename, InputStream input2) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileUrl = aliOssProperties.getUploadBaseDir() + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + filename);
        PutObjectResult result = ossClient.putObject(new PutObjectRequest(aliOssProperties.getBucketName(), fileUrl, input2).
                withProgressListener(new PutObjectProgressListener(null, 0)));
        ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
    }

}
