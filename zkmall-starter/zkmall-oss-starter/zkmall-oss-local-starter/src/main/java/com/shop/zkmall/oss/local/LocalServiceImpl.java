/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.local;

import cn.hutool.core.util.StrUtil;
import com.shop.zkmall.oss.FileStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zkmall zkmallvip@163.com
 */
public class LocalServiceImpl implements FileStrategy {

    public final static String DEFAULT_MONTH_FORMAT_SLASH = "yyyy/MM";

    private final LocalOssProperties localOssProperties;

    public LocalServiceImpl(LocalOssProperties localOssProperties) {
        this.localOssProperties = localOssProperties;
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        Objects.requireNonNull(originalFileName);
        originalFileName = originalFileName.replaceAll(" ", "");
        //生成文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + originalFileName;
        return upload(fileName, file.getBytes());
    }

    @Override
    public String upload(String fileName, InputStream inputStream, long size) throws Exception {
        return upload(fileName, IOUtils.toByteArray(inputStream));
    }

    @Override
    public String upload(String fileName, byte[] bytes) throws Exception {
        //生成文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "_" + fileName;

        //日期文件夹
        String relativePath = Paths.get("file", LocalDate.now().format(DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT_SLASH))).toString();
        // web服务器存放的绝对路径
        String absolutePath = Paths.get(localOssProperties.getStoragePath(), relativePath).toString();

        java.io.File outFile = new java.io.File(Paths.get(absolutePath, newFileName).toString());
        FileUtils.writeByteArrayToFile(outFile, bytes);

        String url = localOssProperties.getUriPrefix() + relativePath + "/" + newFileName;
        //替换掉windows环境的\路径
        url = StrUtil.replace(url, "\\\\", "/");
        url = StrUtil.replace(url, "\\", "/");
        return url;
    }

    @Override
    public InputStream getInputStream(String url) {
        try {
            return new FileInputStream(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
