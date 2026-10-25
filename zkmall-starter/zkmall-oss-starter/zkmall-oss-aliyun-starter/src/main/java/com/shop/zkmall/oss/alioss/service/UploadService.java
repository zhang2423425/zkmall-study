/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.InputStream;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface UploadService {

    /**
     * 上传本地文件(文件上传)
     *
     * @param file 文件对象
     */
    void uploadFile(File file);

    /**
     * 上传文件流（流式上传）
     *
     * @param file 文件对象
     */
    void uploadStream(File file) throws Exception;

    /**
     * 上传文件流（流式上传）
     *
     * @param filename    文件名
     * @param inputStream 文件输入流
     */
    void uploadStream(String filename, InputStream inputStream);

    /**
     * 上传网络流（流式上传）
     *
     * @param file 文件对象
     */
    void uploadInternetStream(File file) throws Exception;

    /**
     * 上传Byte数组（流式上传）
     *
     * @param file 文件对象
     */
    void uploadByte(File file);

    /**
     * 上传Byte数组（流式上传）
     *
     * @param filename 文件名
     * @param content  文件字节数组
     */
    String uploadByte(String filename, byte[] content);

    /**
     * 上传Byte数组（流式上传带进度条）
     *
     * @param filename 文件名
     * @param content  文件字节数组
     * @param request  请求request
     * @param total    文件总大小
     */
    void uploadByte(String filename, byte[] content, HttpServletRequest request, long total);

    /**
     * 上传Byte数组（流式上传带进度条），返回文件url
     *
     * @param filename 文件名
     * @param content  文件字节数组
     * @param request  请求request
     * @param total    文件总大小
     */
    String uploadByteReturnUrl(String filename, byte[] content, HttpServletRequest request, long total);

    /**
     * 上传Byte数组（流式上传）
     *
     * @param file 文件对象
     */
    void uploadString(File file);

    /**
     * 上传图片到OSS
     *
     * @param filename 文件名
     * @param input2   文件流
     */
    void uploadFile(String filename, InputStream input2);

}
