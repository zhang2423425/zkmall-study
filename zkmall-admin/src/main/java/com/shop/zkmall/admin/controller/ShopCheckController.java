/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.shop.Shop;
import com.shop.zkmall.admin.param.shopcheck.CheckDeleteParam;
import com.shop.zkmall.admin.param.shopcheck.CheckGetAllParam;
import com.shop.zkmall.admin.param.shopcheck.CheckGetByIdParam;
import com.shop.zkmall.admin.param.shopcheck.CheckHandleParam;
import com.shop.zkmall.admin.service.shop.CereShopCheckService;
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
 * 入驻申请
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("check")
@RequiredArgsConstructor
@Tag(name = "入驻申请模块", description = "入驻申请模块")
public class ShopCheckController {

    private final CereShopCheckService cereShopCheckService;

    /**
     * 入驻申请管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "入驻申请管理查询")
    public Result<Page<Shop>> getAll(@RequestBody CheckGetAllParam param) {
        Page<Shop> page = cereShopCheckService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 查看
     */
    @PostMapping(value = "getById")
    @Operation(summary = "查看")
    public Result<Shop> getById(@RequestBody CheckGetByIdParam param) {
        Shop shop = cereShopCheckService.getById(param.getShopId());
        return new Result<>(shop);
    }

    /**
     * 处理入驻申请
     */
    @PostMapping(value = "handle")
    @NoRepeatSubmit
    @Operation(summary = "处理入驻申请")
    @WebRequestLog
    public Result<String> handle(@RequestBody CheckHandleParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCheckService.handle(param, user);
        return new Result<>(user.getUsername(), "处理入驻申请", GsonUtil.objectToGson(param));
    }

    /**
     * 删除入驻审核记录
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除")
    @WebRequestLog
    public Result<String> delete(@RequestBody CheckDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCheckService.delete(param, user);
        return new Result<>(user.getUsername(), "删除入驻审核记录", GsonUtil.objectToGson(param));
    }
}
