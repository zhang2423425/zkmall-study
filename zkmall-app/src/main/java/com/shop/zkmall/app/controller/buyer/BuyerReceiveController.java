/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.buyer;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.param.buyer.ReceiveParam;
import com.shop.zkmall.app.service.buyer.CereBuyerReceiveService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
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

import java.util.List;

/**
 * 收货地址模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("receive")
@RequiredArgsConstructor
@Tag(name = "收货地址模块", description = "收货地址模块")
public class BuyerReceiveController {

    private final CereBuyerReceiveService cereBuyerReceiveService;

    /**
     * 添加收货地址
     */
    @PostMapping("save")
    @NoRepeatSubmit
    @Operation(summary = "添加收货地址")
    @WebRequestLog
    public Result<CereBuyerReceive> addCart(@RequestBody CereBuyerReceive receive, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        CereBuyerReceive cereBuyerReceive = cereBuyerReceiveService.save(receive, user);
        return new Result<>(cereBuyerReceive, CoReturnFormat.SUCCESS, user.getWechatName(), "添加收货地址", GsonUtil.objectToGson(receive));
    }

    /**
     * 修改收货地址
     */
    @RequestMapping(value = "update", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "修改收货地址")
    @WebRequestLog
    public Result<String> update(@RequestBody CereBuyerReceive receive, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerReceiveService.update(receive, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "修改收货地址", GsonUtil.objectToGson(receive));
    }

    /**
     * 删除收货地址
     */
    @RequestMapping(value = "delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "删除收货地址")
    @WebRequestLog
    public Result<String> delete(@RequestBody ReceiveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerReceiveService.delete(param.getReceiveId(), user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "删除收货地址", GsonUtil.objectToGson(param));
    }

    /**
     * 修改收货地址查询
     */
    @GetMapping("getById")
    @Operation(summary = "修改收货地址查询")
    public Result<CereBuyerReceive> getById(ReceiveParam param) {
        CereBuyerReceive receive = cereBuyerReceiveService.getById(param.getReceiveId());
        return new Result<>(receive, CoReturnFormat.SUCCESS);
    }

    /**
     * 收货地址管理查询
     */
    @GetMapping("getAll")
    @Operation(summary = "收货地址列表查询")
    public Result<Page<CereBuyerReceive>> getAll(PageParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<CereBuyerReceive> page = cereBuyerReceiveService.getAll(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 选择收货地址查询
     */
    @GetMapping("getSelect")
    @Operation(summary = "选择收货地址查询")
    public Result<List<CereBuyerReceive>> getSelect(HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<CereBuyerReceive> list = cereBuyerReceiveService.getSelect(user.getBuyerUserId());
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }
}
