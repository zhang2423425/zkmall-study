/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.notice.NoticeGetAllParam;
import com.shop.zkmall.admin.param.notice.NoticeIdParam;
import com.shop.zkmall.admin.param.notice.NoticeParam;
import com.shop.zkmall.admin.service.notice.CereNoticeService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息中心
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("notice")
@RequiredArgsConstructor
@Tag(name = "消息中心", description = "消息中心")
public class NoticeController {

    private final CereNoticeService cereNoticeService;

    /**
     * 消息推送
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "消息推送")
    @WebRequestLog
    public Result<String> save(@RequestBody NoticeParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereNoticeService.save(param, user);
        return new Result<>(user.getUsername(), "消息推送", GsonUtil.objectToGson(param));
    }

    /**
     * 删除消息
     *
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除消息")
    @WebRequestLog
    public Result<String> delete(@RequestBody NoticeIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereNoticeService.delete(param, user);
        return new Result<>(user.getUsername(), "删除消息", GsonUtil.objectToGson(param));
    }

    /**
     * 消息详情查询
     *
     */
    @PostMapping(value = "getById")
    @Operation(summary = "消息详情查询")
    public Result<CereNotice> getById(@RequestBody NoticeIdParam param) {
        CereNotice cereNotice = cereNoticeService.getById(param.getNoticeId());
        return new Result<>(cereNotice);
    }

    /**
     * 历史消息查询
     *
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "历史消息查询")
    public Result<Page<CereNotice>> getAll(@RequestBody NoticeGetAllParam param) {
        Page<CereNotice> page = cereNoticeService.getAll(param);
        return new Result<>(page);
    }
}
