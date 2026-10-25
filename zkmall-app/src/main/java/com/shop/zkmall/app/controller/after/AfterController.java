/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.after;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.after.AfterDetail;
import com.shop.zkmall.app.page.after.Afters;
import com.shop.zkmall.app.param.after.AfterGetAllParam;
import com.shop.zkmall.app.param.after.AfterGetByIdParam;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.app.param.after.PlatformParam;
import com.shop.zkmall.app.param.common.CommonIdParam;
import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.app.service.dict.CerePlatformDictService;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * 售后模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("after")
@RequiredArgsConstructor
@Tag(name = "售后模块", description = "售后模块")
public class AfterController {

    private final CereOrderAfterService cereOrderAfterService;

    private final CerePlatformDictService cerePlatformDictService;

    /**
     * 获取要退款的订单金额
     */
    @PostMapping("getReturnPrice")
    @Operation(summary = "获取要退款的订单金额")
    public Result<String> getReturnPrice(@RequestBody AfterParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        BigDecimal returnPrice = cereOrderAfterService.getReturnPrice(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "获取要退款的订单金额", GsonUtil.objectToGson(returnPrice));
    }

    /**
     * 申请售后
     */
    @PostMapping("submit")
    @NoRepeatSubmit
    @Operation(summary = "申请售后")
    @WebRequestLog
    public Result<String> submit(@RequestBody AfterParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereOrderAfterService.submit(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "申请售后", GsonUtil.objectToGson(param));
    }

    /**
     * 申请平台介入
     */
    @PostMapping("platform")
    @NoRepeatSubmit
    @Operation(summary = "申请平台介入")
    @WebRequestLog
    public Result<String> platform(@RequestBody PlatformParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereOrderAfterService.platform(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "申请平台介入", GsonUtil.objectToGson(param));
    }

    /**
     * 售后列表查询
     */
    @GetMapping("getAll")
    @Operation(summary = "售后列表查询")
    public Result<Page<Afters>> getAll(AfterGetAllParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<Afters> page = cereOrderAfterService.getAll(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 售后详情查询
     */
    @GetMapping("getById")
    @Operation(summary = "售后列表查询")
    public Result<AfterDetail> getById(AfterGetByIdParam param) throws Exception {
        AfterDetail detail = cereOrderAfterService.getById(param.getAfterId());
        return new Result<>(detail, CoReturnFormat.SUCCESS);
    }

    /**
     * 撤销申请
     */
    @RequestMapping(value = "returnRefund", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "撤销申请")
    @WebRequestLog
    public Result<String> returnRefund(@RequestBody AfterGetByIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereOrderAfterService.returnRefund(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "撤销申请", GsonUtil.objectToGson(param));
    }

    /**
     * 撤销退货
     */
    @RequestMapping(value = "returnGoods", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "撤销退货")
    @WebRequestLog
    public Result<String> returnGoods(@RequestBody AfterGetByIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereOrderAfterService.returnGoods(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "撤销退货", GsonUtil.objectToGson(param));
    }

    /**
     * 选择退款原因查询
     */
    @GetMapping("getReasonSelect")
    @NoRepeatSubmit
    @Operation(summary = "选择退款原因查询")
    public Result<List<String>> getReasonSelect() {
        List<String> dicts = cerePlatformDictService.getReasonSelect();
        return new Result<>(dicts, CoReturnFormat.SUCCESS);
    }

    @PostMapping("deleteAfter")
    @NoRepeatSubmit
    @Operation(summary = "删除售后单")
    public Result<Boolean> deleteAfter(@RequestBody CommonIdParam param) {
        cereOrderAfterService.deleteAfterById(param.getId());
        return new Result<>(true);
    }
}
