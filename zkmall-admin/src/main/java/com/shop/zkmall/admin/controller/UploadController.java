/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.oss.FileStrategy;
import com.shop.zkmall.oss.Url;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("file")
@RequiredArgsConstructor
@Tag(name = "文件上传", description = "文件上传")
public class UploadController {

    private final FileStrategy fileStrategy;

    /**
     * 文件上传
     */
    @PostMapping("upload")
    @Operation(summary = "文件上传")
    public Result<Url> upload(MultipartFile file) throws Exception {
        Result<Url> result = new Result<>();
        if (null != file) {
            if (file.getOriginalFilename().contains("mp4")) {
                //如果上传的视频,校验大小不能超过2M
                if (file.getSize() > 2048 * 1024) {
                    return new Result<>(CoReturnFormat.MP4_FILE_NOT_2M);
                }
            }
            String url = fileStrategy.upload(file);
            result = new Result<>(new Url(url));
        }
        return result;
    }
}
