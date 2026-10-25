/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service.impl;

import com.shop.zkmall.oss.FileStrategy;
import com.shop.zkmall.oss.alioss.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class AliServiceImpl implements FileStrategy {

    private final FileUploadService fileUploadService;

    public AliServiceImpl(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        return fileUploadService.uploadFile(file.getOriginalFilename(), file.getInputStream(), file.getSize());
    }

    @Override
    public String upload(String fileName, InputStream inputStream, long size) throws Exception {
        return fileUploadService.uploadFile(fileName, inputStream, size);
    }

    @Override
    public String upload(String fileName, byte[] bytes) throws Exception {
        return fileUploadService.uploadFile(fileName, bytes);
    }

    @Override
    public InputStream getInputStream(String url) {
        return fileUploadService.getInputStream(url);
    }
}
