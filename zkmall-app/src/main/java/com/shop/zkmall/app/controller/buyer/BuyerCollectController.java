/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.buyer;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.collect.CollectProduct;
import com.shop.zkmall.app.page.collect.CollectShop;
import com.shop.zkmall.app.param.collect.CollectGetAllParam;
import com.shop.zkmall.app.param.collect.CollectIdParam;
import com.shop.zkmall.app.param.collect.CollectParam;
import com.shop.zkmall.app.service.collect.CereBuyerCollectService;
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
 * 我的收藏
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("collect")
@RequiredArgsConstructor
@Tag(name = "我的收藏", description = "我的收藏")
public class BuyerCollectController {

    private final CereBuyerCollectService cereBuyerCollectService;

    /**
     * 收藏商品、店铺
     */
    @PostMapping("collect")
    @NoRepeatSubmit
    @Operation(summary = "收藏商品、店铺")
    @WebRequestLog
    public Result<String> collect(@RequestBody CollectParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCollectService.collect(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "收藏商品、店铺", GsonUtil.objectToGson(param));
    }

    /**
     * 取消收藏
     */
    @RequestMapping(value = "cancel", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "取消收藏")
    @WebRequestLog
    public Result<String> cancel(@RequestBody CollectIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCollectService.cancel(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "取消收藏", GsonUtil.objectToGson(param));
    }

    /**
     * 取消收藏
     */
    @RequestMapping(value = "new-cancel", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "取消收藏")
    @WebRequestLog
    public Result<String> newCancel(@RequestBody CollectParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCollectService.newCancel(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "取消收藏", GsonUtil.objectToGson(param));
    }

    /**
     * 选中收藏商品、店铺
     */
    @RequestMapping(value = "selected", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "选中收藏商品、店铺")
    @WebRequestLog
    public Result<String> selected(@RequestBody CollectIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCollectService.selected(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "选中收藏商品、店铺", GsonUtil.objectToGson(param));
    }

    /**
     * 删除收藏商品、店铺
     */
    @RequestMapping(value = "delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "删除")
    @WebRequestLog
    public Result<String> delete(@RequestBody CollectIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCollectService.delete(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "删除收藏商品、店铺", GsonUtil.objectToGson(param));
    }

    /**
     * 收藏商品查询
     */
    @GetMapping("getAllProduct")
    @Operation(summary = "收藏商品查询")
    public Result<Page<CollectProduct>> getAllProduct(CollectGetAllParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<CollectProduct> page = cereBuyerCollectService.getAllProduct(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 收藏店铺查询
     */
    @GetMapping("getAllShop")
    @Operation(summary = "收藏店铺查询")
    public Result<Page<CollectShop>> getAllShop(CollectGetAllParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<CollectShop> page = cereBuyerCollectService.getAllShop(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

}
