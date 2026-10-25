/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;


import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
public class ImageUtil {


    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param imgInputStream InputStream
     * @param quality        图片质量
     * @return InputStream
     */
    public static InputStream compressImg(InputStream imgInputStream, float quality) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            if (quality > 1) {
                quality = 1;
            } else if (quality <= 0) {
                quality = 0.5f;
            }
            Thumbnails.of(imgInputStream).scale(1f).outputQuality(quality).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(buf);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.compressImg error:", e);
            return null;
        }
    }


}
