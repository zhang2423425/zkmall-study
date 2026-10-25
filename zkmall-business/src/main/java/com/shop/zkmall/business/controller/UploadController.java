/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.util.IdUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.upload.UploadResult;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.oss.FileStrategy;
import com.shop.zkmall.oss.Url;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    private final WxMaService wxMaService;

    private final WxCpServiceImpl wxCpServiceImpl;

    /**
     * 文件上传
     */
    @PostMapping("upload")
    @Operation(summary = "文件上传")
    public Result<Url> upload(MultipartFile file) throws Exception {
        Result<Url> result = new Result<>();
        if (null != file) {
            String url = fileStrategy.upload(file);
            result = new Result<>(new Url(url));
        }
        return result;
    }

    /**
     * 企业微信媒体-文件上传
     */
    @PostMapping("uploadQyMedia")
    @Operation(summary = "企业微信-文件上传")
    public Result<UploadResult> uploadWorkMedia(MultipartFile file) throws Exception {
        Result<UploadResult> result = new Result<>();
        if (null != file) {
            String fileName = file.getOriginalFilename();
            log.info("fileName: {}", fileName);

            InputStream inputStream = file.getInputStream();

            WxMediaUploadResult uploadMediaResult = wxCpServiceImpl.getMediaService().upload(WxConsts.MediaFileType.IMAGE, transferToFile(file, fileName));
            if (uploadMediaResult.getMediaId() != null) {
                UploadResult data = new UploadResult();
                data.setMediaId(uploadMediaResult.getMediaId());

                String url = fileStrategy.upload(fileName, inputStream, inputStream.available());
                data.setUrl(url);

                result.setData(data);
                result.setCode(CoReturnFormat.SUCCESS);
                return result;
            }
        }
        return result;
    }

    /**
     * 微信媒体(当前配置小程序)-文件上传
     */
    @PostMapping("uploadWxMedia")
    @Operation(summary = "微信-文件上传")
    public Result<UploadResult> uploadWxMedia(MultipartFile file) throws Exception {
        Result<UploadResult> result = new Result<>();
        if (null != file) {
            String fileName = file.getOriginalFilename();
            log.info("fileName: {}", fileName);

            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            String ext = FilenameUtils.getExtension(fileName);

            assert fileName != null;
            WxMediaUploadResult uploadMediaResult = wxMaService.getMediaService().uploadMedia(WxConsts.MediaFileType.IMAGE, ext, inputStream);
            if (uploadMediaResult.getMediaId() != null) {
                UploadResult data = new UploadResult();
                data.setMediaId(uploadMediaResult.getMediaId());

                String url = fileStrategy.upload(fileName, inputStream, available);
                data.setUrl(url);

                result.setData(data);
                result.setCode(CoReturnFormat.SUCCESS);
                return result;
            }
        }
        return result;
    }

    private File transferToFile(MultipartFile multipartFile, String fileName) {
        File file;
        try {
            //String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = fileName.split("\\.");
            file = File.createTempFile(IdUtil.simpleUUID(), filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
