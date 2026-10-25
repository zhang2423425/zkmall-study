/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.group.Group;
import com.shop.zkmall.business.page.group.GroupDetail;
import com.shop.zkmall.business.page.group.GroupProduct;
import com.shop.zkmall.business.page.order.Product;
import com.shop.zkmall.business.param.group.*;
import com.shop.zkmall.business.service.shop.CereShopGroupService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
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

import java.util.List;

/**
 * 店铺商品分组
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("group")
@RequiredArgsConstructor
@Tag(name = "店铺商品分组模块", description = "店铺商品分组模块")
public class ShopGroupController {

    private final CereShopGroupService cereShopGroupService;

    /**
     * 添加分组
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加分组")
    @WebRequestLog
    public Result<String> save(@RequestBody ShopGroupSaveParam group, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        group.setShopId(ContextUtil.getShopId());
        cereShopGroupService.save(group, user);
        return new Result<>(user.getUsername(), "添加分组", GsonUtil.objectToGson(group));
    }

    /**
     * 获取满足条件的商品id
     */
    @PostMapping(value = "getProductIds")
    @NoRepeatSubmit
    @Operation(summary = "获取满足条件的商品id")
    public Result<List<Product>> getProductIds(@RequestBody ShopGroupSaveParam group) {
        group.setShopId(ContextUtil.getShopId());
        List<Product> list = cereShopGroupService.getProductIds(group);
        return new Result<>(list);
    }

    /**
     * 修改分组
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改分组")
    @WebRequestLog
    public Result<String> update(@RequestBody ShopGroupUpdateParam group, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        group.setShopId(ContextUtil.getShopId());
        cereShopGroupService.update(group, user);
        return new Result<>(user.getUsername(), "修改分组", GsonUtil.objectToGson(group));
    }

    /**
     * 删除分组
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除分组")
    @WebRequestLog
    public Result<String> delete(@RequestBody GroupDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopGroupService.delete(param, user);
        return new Result<>(user.getUsername(), "删除分组", GsonUtil.objectToGson(param));
    }

    /**
     * 分组修改查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "分组修改查询")
    public Result<GroupDetail> getById(@RequestBody GroupDeleteParam group) {
        GroupDetail detail = cereShopGroupService.getById(group.getShopGroupId());
        return new Result<>(detail);
    }

    /**
     * 分组管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "分组管理查询")
    public Result<Page<Group>> getAll(@RequestBody GroupGetAllParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<Group> page = cereShopGroupService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 手动添加商品查询
     */
    @PostMapping(value = "getProducts")
    @Operation(summary = "手动添加商品查询")
    public Result<Page<GroupProduct>> getProducts(@RequestBody GroupProductParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<GroupProduct> page = cereShopGroupService.getProducts(param);
        return new Result<>(page);
    }
}
