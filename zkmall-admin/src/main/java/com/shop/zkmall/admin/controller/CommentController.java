/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.shop.CommentDetail;
import com.shop.zkmall.admin.page.shop.ShopComment;
import com.shop.zkmall.admin.param.comment.CommentDeleteParam;
import com.shop.zkmall.admin.param.comment.CommentGetAllParam;
import com.shop.zkmall.admin.param.comment.CommentGetByIdParam;
import com.shop.zkmall.admin.param.comment.CommentUpdateParam;
import com.shop.zkmall.admin.service.shop.CereShopCommentService;
import com.shop.zkmall.commons.domain.common.Page;
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
 * 评论管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Tag(name = "评论管理模块", description = "评论管理模块")
public class CommentController {

    private final CereShopCommentService cereShopCommentService;

    /**
     * 评论管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "评论管理查询")
    public Result<Page<ShopComment>> getAll(@RequestBody CommentGetAllParam param) {
        Page<ShopComment> page = cereShopCommentService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 评论详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "评论详情查询")
    public Result<CommentDetail> getById(@RequestBody CommentGetByIdParam param) throws Exception {
        CommentDetail detail = cereShopCommentService.getById(param.getCommentId());
        return new Result<>(detail);
    }

    /**
     * 修改评论
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改评论")
    @WebRequestLog
    public Result<String> update(@RequestBody CommentUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.update(param, user);
        return new Result<>(user.getUsername(), "修改评论", GsonUtil.objectToGson(param));
    }

    /**
     * 允许展示评论
     */
    @PostMapping(value = "allow")
    @NoRepeatSubmit
    @Operation(summary = "允许展示评论")
    @WebRequestLog
    public Result<String> allow(@RequestBody CommentUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.allow(param, user);
        return new Result<>(user.getUsername(), "允许展示评论", GsonUtil.objectToGson(param));
    }

    /**
     * 删除评论
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除评论")
    @WebRequestLog
    public Result<String> delete(@RequestBody CommentDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.delete(param, user);
        return new Result<>(user.getUsername(), "删除评论", GsonUtil.objectToGson(param));
    }
}
