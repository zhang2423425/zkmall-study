/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.notice;

import com.shop.zkmall.app.page.notice.BuyerNotice;
import com.shop.zkmall.app.page.notice.NoticeDetail;
import com.shop.zkmall.app.param.notice.NoticeIdParam;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final CereBuyerUserService cereBuyerUserService;

    /**
     * 消息列表查询
     */
    @GetMapping("getAll")
    @Operation(summary = "消息列表查询")
    public Result<Page<BuyerNotice>> getAll(PageParam pageParam, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Page<BuyerNotice> page = cereNoticeService.getAll(pageParam, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 公告消息列表查询
     */
    @GetMapping("getGongGaoAll")
    @Operation(summary = "公告消息列表查询")
    public Result<List<BuyerNotice>> getGongGaoAll(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        List<BuyerNotice> list = cereNoticeService.getGongGaoAll(user);
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 公告详情查询
     */
    @GetMapping("getById")
    @Operation(summary = "公告详情查询")
    public Result<NoticeDetail> getById(NoticeIdParam param) {
        NoticeDetail detail = cereNoticeService.getById(param);
        return new Result<>(detail, CoReturnFormat.SUCCESS);
    }

    @PostMapping("readNotice")
    @Operation(summary = "已读某个消息")
    public Result<Boolean> readNotice(@RequestBody NoticeIdParam param, HttpServletRequest request) {
        CereBuyerUser cereBuyerUser = (CereBuyerUser) request.getAttribute("user");
        if (cereBuyerUser == null) {
            return new Result<>(false, CoReturnFormat.SUCCESS);
        }
        cereNoticeService.readNotice(param.getNoticeId(), cereBuyerUser.getBuyerUserId());
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

    @PostMapping("readAll")
    @Operation(summary = "全部已读")
    public Result<Boolean> readAll(HttpServletRequest request) {
        CereBuyerUser cereBuyerUser = (CereBuyerUser) request.getAttribute("user");
        if (cereBuyerUser == null) {
            return new Result<>(false, CoReturnFormat.SUCCESS);
        }
        cereNoticeService.readAll(cereBuyerUser.getBuyerUserId());
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

    @PostMapping("removeById")
    @Operation(summary = "删除消息")
    public Result<Boolean> removeById(@RequestBody NoticeIdParam param, HttpServletRequest request) {
        CereBuyerUser cereBuyerUser = (CereBuyerUser) request.getAttribute("user");
        if (cereBuyerUser == null) {
            return new Result<>(false, CoReturnFormat.SUCCESS);
        }
        cereNoticeService.removeById(param.getNoticeId(), cereBuyerUser.getBuyerUserId());
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }
}
