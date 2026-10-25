/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.shop.zkmall.oss.alioss.AliOssProperties;
import com.shop.zkmall.oss.alioss.service.GetFileListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFileListServiceImpl implements GetFileListService {

    private final OSSClient ossClient;

    private final AliOssProperties aliOssProperties;

    public GetFileListServiceImpl(OSSClient ossClient, AliOssProperties aliOssProperties) {
        this.ossClient = ossClient;
        this.aliOssProperties = aliOssProperties;
    }

    @Override
    public List<OSSObjectSummary> getDefaultFiles() {
        ObjectListing objectListing = ossClient.listObjects(aliOssProperties.getBucketName());
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFilesByKeys(int maxKeys) {
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFileByPrefix(String prefix) {
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).withPrefix(prefix));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFilesNyMarker(String marker) {
        // 列举指定marker之后的文件。默认列举100个文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).withMarker(marker));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFilesPaging(int maxKeys) {
        String nextMarker = null;
        ObjectListing objectListing;
        List<OSSObjectSummary> sums = null;
        do {
            objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).withMarker(nextMarker).withMaxKeys(maxKeys));
            sums = objectListing.getObjectSummaries();
            nextMarker = objectListing.getNextMarker();

        } while (objectListing.isTruncated());
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFilesPagingByPrefix(String prefix, int maxKeys) {
        String nextMarker = null;
        ObjectListing objectListing;
        List<OSSObjectSummary> sums = null;
        do {
            objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssProperties.getBucketName()).
                    withPrefix(prefix).withMarker(nextMarker).withMaxKeys(maxKeys));

            sums = objectListing.getObjectSummaries();
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());
        return sums;
    }

    @Override
    public List<OSSObjectSummary> getFolderFiles() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(aliOssProperties.getBucketName());
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        return listing.getObjectSummaries();
    }

    @Override
    public List<OSSObjectSummary> getFilesByFolder(String folder) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(aliOssProperties.getBucketName());
        listObjectsRequest.setPrefix(folder);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        return listing.getObjectSummaries();
    }

    @Override
    public List<OSSObjectSummary> getFilesChildFolderByFolder(String folder) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(aliOssProperties.getBucketName());
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(folder);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        return listing.getObjectSummaries();
    }
}
