/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.shop;

import com.shop.zkmall.app.page.banner.ShopBanner;
import com.shop.zkmall.app.page.settlement.SettlementShop;
import com.shop.zkmall.app.page.shop.Shop;
import com.shop.zkmall.app.page.shop.ShopClassify;
import com.shop.zkmall.app.page.shop.ShopIndex;
import com.shop.zkmall.app.param.shop.ShopParam;
import com.shop.zkmall.app.param.shop.ShopPosterParam;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.HttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 店铺模块
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
@Tag(name = "店铺模块", description = "店铺模块")
public class ShopController {

    private final CerePlatformShopService cerePlatformShopService;

    private final CereBuyerUserService cereBuyerUserService;


    /**
     * 店铺商品查询
     */
    @GetMapping("getShopProducts")
    @Operation(summary = "商品搜索查询")
    public Result<Shop> getShopProducts(ShopParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Shop shop = cerePlatformShopService.getShopProducts(param, user);
        return new Result<>(shop, CoReturnFormat.SUCCESS);
    }

    /**
     * 店铺搜索
     */
    @GetMapping("getShops")
    @Operation(summary = "店铺搜索")
    public Result<Page<SettlementShop>> getShops(ShopParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Page<SettlementShop> page = cerePlatformShopService.getShops(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询店铺分类
     */
    @GetMapping("getShopClassify")
    @Operation(summary = "查询店铺分类")
    public Result<List<ShopClassify>> getShopClassify(ShopParam param) {
        List<ShopClassify> list = cerePlatformShopService.getShopClassify(param);
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询店铺banner
     */
    @GetMapping("getShopBanner")
    @Operation(summary = "查询店铺banner")
    public Result<List<ShopBanner>> getShopBanner(ShopParam param) {
        List<ShopBanner> list = cerePlatformShopService.getShopBanner(param.getShopId());
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 店铺首页查询
     */
    @GetMapping("getIndex")
    @Operation(summary = "店铺首页查询")
    public Result<ShopIndex> getIndex(ShopParam param, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        ShopIndex index = cerePlatformShopService.getIndex(param, user);
        return new Result<>(index, CoReturnFormat.SUCCESS);
    }

    /**
     * 获取分享图片
     */
    @GetMapping("getSharePic")
    @Operation(summary = "获取分享图片")
    public void getSharePic(ShopPosterParam param, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        try {
            if (!EmptyUtils.isEmpty(token)) {
                //根据token查询用户信息
                user = cereBuyerUserService.findByToken(token);
            }
            HttpUtils.write(cerePlatformShopService.getSharePic(param, user), response);
        } catch (Exception exception) {
            log.error("生成店铺分享图错误:", exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("addBusinessBuyerUser")
    @Operation(summary = "添加客户访问商家的记录")
    public Result<Boolean> addBusinessBuyerUser() {
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

}
