/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.message.*;
import com.shop.zkmall.admin.service.message.CerePlatfromMessageService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;
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

/**
 * 短信账号配置
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("message")
@RequiredArgsConstructor
@Tag(name = "短信账号配置模块", description = "短信账号配置模块")
public class MessageController {

    private final CerePlatfromMessageService cerePlatfromMessageService;

    /**
     * 添加短信模板配置
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加短信模板配置")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated MessageSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.save(param, user);
        return new Result<>(user.getUsername(), "添加短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 修改短信模板配置
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改短信模板配置")
    @WebRequestLog
    public Result<String> update(@RequestBody MessageUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.update(param, user);
        return new Result<>(user.getUsername(), "修改短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 删除短信模板配置
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除短信模板配置")
    @WebRequestLog
    public Result<String> delete(@RequestBody MessageDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.delete(param, user);
        return new Result<>(user.getUsername(), "删除短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 短信模板配置修改查询
     */
    @PostMapping(value = "getById")
    @NoRepeatSubmit
    @Operation(summary = "短信模板配置修改查询")
    public Result<CerePlatfromMessage> getById(@RequestBody MessageGetByIdParam param) {
        CerePlatfromMessage cerePlatfromMessage = cerePlatfromMessageService.getById(param.getMessageId());
        return new Result<>(cerePlatfromMessage);
    }

    /**
     * 短信模板配置管理查询
     */
    @PostMapping(value = "getAll")
    @NoRepeatSubmit
    @Operation(summary = "短信模板配置管理查询")
    public Result<Page<CerePlatfromMessage>> getAll(@RequestBody MessageGetAllParam param) {
        Page<CerePlatfromMessage> page = cerePlatfromMessageService.getAll(param);
        return new Result<>(page);
    }
}
