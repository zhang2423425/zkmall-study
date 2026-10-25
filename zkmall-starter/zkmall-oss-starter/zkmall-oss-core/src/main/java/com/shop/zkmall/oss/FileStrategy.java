/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件策略接口
 *
 * @author zkmall zkmallvip@163.com
 */
public interface FileStrategy {
    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件对象
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 文件上传
     *
     * @param fileName    文件名称
     * @param inputStream 输入流
     * @param size        文件大小
     * @return 文件对象
     */
    String upload(String fileName, InputStream inputStream, long size) throws Exception;

    /**
     * 文件上传
     *
     * @param fileName 文件名称
     * @param bytes    自己数组
     * @return 文件对象
     */
    String upload(String fileName, byte[] bytes) throws Exception;

    /**
     * 获取数据流
     */
    InputStream getInputStream(String url);

}
