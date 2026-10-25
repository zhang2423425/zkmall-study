/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.sensitive.SensitiveSaveParam;
import com.shop.zkmall.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.zkmall.admin.service.sensitive.CerePlatformSensitiveService;
import com.shop.zkmall.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 敏感词管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("sensitive")
@RequiredArgsConstructor
@Tag(name = "敏感词管理模块", description = "敏感词管理模块")
public class SensitiveController {

    private final CerePlatformSensitiveService cerePlatformSensitiveService;

    /**
     * 添加敏感词设置
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加敏感词设置")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated SensitiveSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSensitiveService.save(param, user);
        return new Result<>(user.getUsername(), "添加敏感词设置", GsonUtil.objectToGson(param));
    }

    /**
     * 修改敏感词设置
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改敏感词设置")
    @WebRequestLog
    public Result<String> update(@RequestBody SensitiveUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSensitiveService.update(param, user);
        return new Result<>(user.getUsername(), "修改敏感词设置", GsonUtil.objectToGson(param));
    }

    /**
     * 敏感词查询
     */
    @PostMapping(value = "get")
    @Operation(summary = "敏感词查询")
    public Result<List<CerePlatformSensitive>> get() {
        List<CerePlatformSensitive> sensitive = cerePlatformSensitiveService.get();
        return new Result<>(sensitive);
    }
}
