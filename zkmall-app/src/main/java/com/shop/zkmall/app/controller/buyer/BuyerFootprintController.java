/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.buyer;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.collect.BuyerFootprint;
import com.shop.zkmall.app.param.collect.FootprintIdParam;
import com.shop.zkmall.app.service.collect.CereBuyerFootprintService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 浏览足迹
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("footprint")
@RequiredArgsConstructor
@Tag(name = "浏览足迹", description = "浏览足迹")
public class BuyerFootprintController {

    private final CereBuyerFootprintService cereBuyerFootprintService;

    /**
     * 删除足迹
     */
    @RequestMapping(value = "delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "删除足迹")
    @WebRequestLog
    public Result<String> delete(@RequestBody FootprintIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerFootprintService.delete(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "删除足迹", GsonUtil.objectToGson(param));
    }

    /**
     * 选中足迹
     */
    @RequestMapping(value = "selected", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "选中足迹")
    @WebRequestLog
    public Result<String> selected(@RequestBody FootprintIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerFootprintService.selected(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "选中足迹", GsonUtil.objectToGson(param));
    }

    /**
     * 我的足迹查询
     */
    @GetMapping("getAll")
    @Operation(summary = "我的足迹查询")
    public Result<Page<BuyerFootprint>> getAll(PageParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<BuyerFootprint> list = cereBuyerFootprintService.getAll(param, user.getBuyerUserId());
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }
}
