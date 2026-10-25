/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;


import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.product.ProductClassify;
import com.shop.zkmall.admin.param.product.ClassifyDeleteParam;
import com.shop.zkmall.admin.param.product.ClassifyGetAllParam;
import com.shop.zkmall.admin.param.product.ClassifyGetByIdParam;
import com.shop.zkmall.admin.param.product.ClassifyLevelParam;
import com.shop.zkmall.admin.service.product.CereProductClassifyService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
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
 * 商品类别
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("classify")
@RequiredArgsConstructor
@Tag(name = "商品类别模块", description = "商品类别模块")
public class ClassifyController {

    private final CereProductClassifyService cereProductClassifyService;

    /**
     * 添加商品类别
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加商品类别")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated ClassifyLevelParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.save(param, user);
        return new Result<>(user.getUsername(), "添加商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商品类别
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改商品类别")
    @WebRequestLog
    public Result<String> update(@RequestBody ClassifyLevelParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.update(param, user);
        return new Result<>(user.getUsername(), "修改商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 删除商品类别
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除商品类别")
    @WebRequestLog
    public Result<String> delete(@RequestBody ClassifyDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.delete(param, user);
        return new Result<>(user.getUsername(), "删除商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 商品类别编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "商品类别编辑查询")
    public Result<ProductClassify> getById(@RequestBody ClassifyGetByIdParam param) {
        ProductClassify classify = cereProductClassifyService.getById(param.getOneClassifyId());
        return new Result<>(classify);
    }

    /**
     * 商品类别管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "商品类别管理查询")
    public Result<Page<CereProductClassify>> getAll(@RequestBody ClassifyGetAllParam param) {
        Page<CereProductClassify> page = cereProductClassifyService.getAll(param);
        return new Result<>(page);
    }
}
