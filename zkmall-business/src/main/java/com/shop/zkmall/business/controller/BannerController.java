/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.shop.ShopBanner;
import com.shop.zkmall.business.param.banner.BannerGetByIdParam;
import com.shop.zkmall.business.param.banner.BannerSaveParam;
import com.shop.zkmall.business.param.banner.BannerUpdateParam;
import com.shop.zkmall.business.service.shop.CereShopBannerService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
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
 * 店铺banner
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("banner")
@RequiredArgsConstructor
@Tag(name = "店铺banner模块", description = "店铺banner模块")
public class BannerController {

    private final CereShopBannerService cereShopBannerService;

    /**
     * 添加banner
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加banner")
    @WebRequestLog
    public Result<String> save(@RequestBody BannerSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBannerService.save(param, user);
        return new Result<>(user.getUsername(), "添加banner", GsonUtil.objectToGson(param));
    }

    /**
     * 修改banner
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改banner")
    public Result<String> update(@RequestBody BannerUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBannerService.update(param, user);
        return new Result<>(user.getUsername(), "修改banner", GsonUtil.objectToGson(param));
    }

    /**
     * banner编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "banner编辑查询")
    public Result<ShopBanner> getById(@RequestBody BannerGetByIdParam param) {
        ShopBanner shopBanner = cereShopBannerService.getById(param.getBannerId());
        return new Result<>(shopBanner);
    }
}
