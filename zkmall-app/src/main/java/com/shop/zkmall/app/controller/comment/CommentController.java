/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.comment;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.comment.Comment;
import com.shop.zkmall.app.page.comment.CommentCount;
import com.shop.zkmall.app.page.comment.WordComment;
import com.shop.zkmall.app.param.comment.CommentIdParam;
import com.shop.zkmall.app.param.comment.CommentParam;
import com.shop.zkmall.app.param.comment.CommentWorParam;
import com.shop.zkmall.app.param.comment.LikeParam;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 我的评价
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Tag(name = "我的评价", description = "我的评价")
public class CommentController {

    private final CereShopCommentService cereShopCommentService;

    /**
     * 我的评价列表查询
     */
    @GetMapping("getAll")
    @Operation(summary = "我的评价列表查询")
    public Result<CommentCount> getAll(CommentParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        CommentCount comment = cereShopCommentService.getAll(param, user);
        return new Result<>(comment, CoReturnFormat.SUCCESS);
    }

    /**
     * 我的评价列表查询-1.5
     */
    @GetMapping("getCommentList")
    @Operation(summary = "我的评价列表查询")
    public Result<CommentCount> getCommentList(CommentParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        CommentCount comment = cereShopCommentService.getCommentList(param, user);
        return new Result<>(comment, CoReturnFormat.SUCCESS);
    }

    /**
     * 商品评价列表查询
     */
    @GetMapping("getProductAll")
    @Operation(summary = "商品评价列表查询")
    public Result<Page<WordComment>> getProductAll(CommentWorParam param) throws Exception {
        Page<WordComment> page = cereShopCommentService.getProductAll(param);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 评论详情查询
     */
    @GetMapping("getById")
    @Operation(summary = "评论详情查询")
    public Result<Comment> getById(CommentIdParam param) {
        Comment comment = cereShopCommentService.getById(param.getCommentId());
        return new Result<>(comment, CoReturnFormat.SUCCESS);
    }

    /**
     * 点赞评论
     */
    @PostMapping("like")
    @Operation(summary = "点赞")
    @NoRepeatSubmit
    @WebRequestLog
    public Result<String> like(@RequestBody LikeParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCommentService.like(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "点赞评论", GsonUtil.objectToGson(param));
    }
}
