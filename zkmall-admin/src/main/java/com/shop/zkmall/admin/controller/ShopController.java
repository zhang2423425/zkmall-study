/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.shop.ShopGetAll;
import com.shop.zkmall.admin.param.shop.*;
import com.shop.zkmall.admin.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商家
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
@Tag(name = "商家模块", description = "商家模块")
public class ShopController {

    private final CerePlatformShopService cerePlatformShopService;

    /**
     * 新建商家
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "新建商家")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated ShopSaveParam param, HttpServletRequest request) {
        //校验商家名称是否存在
        CerePlatformShop shop = cerePlatformShopService.findByShopName(param.getShopName());
        if (shop != null) {
            return new Result<>(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop = cerePlatformShopService.findByPhone(param.getShopPhone());
        if (platformShop != null) {
            return new Result<>(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.save(param, user);
        return new Result<>(user.getUsername(), "新建商家", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商家
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改商家")
    @WebRequestLog
    public Result<String> update(@RequestBody ShopUpdateParam param, HttpServletRequest request) {
        //校验商家名称是否存在
        CerePlatformShop shop = cerePlatformShopService.checkShopIdByName(param.getShopName(), param.getShopId());
        if (shop != null) {
            return new Result<>(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop = cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(), param.getShopId());
        if (platformShop != null) {
            return new Result<>(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        try {
            param = param.decrypt();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.update(param, user);
        return new Result<>(user.getUsername(), "修改商家", GsonUtil.objectToGson(param));
    }

    /**
     * 启停用
     */
    @PostMapping(value = "start")
    @NoRepeatSubmit
    @Operation(summary = "启停用")
    @WebRequestLog
    public Result<String> start(@RequestBody ShopStartParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.startOrStop(param, user);
        return new Result<>(user.getUsername(), "启停用", GsonUtil.objectToGson(param));
    }

    /**
     * 商家编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "商家编辑查询")
    public Result<ShopGetAll> getById(@RequestBody ShopGetByIdParam param) throws Exception {
        ShopGetAll shop = cerePlatformShopService.getById(param.getShopId());
        return new Result<>(shop);
    }

    /**
     * 商家管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "商家管理查询")
    public Result<Page<ShopGetAll>> getAll(@RequestBody ShopGetAllParam param) {
        Page<ShopGetAll> page = cerePlatformShopService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 注销商家数据
     */
    @PostMapping(value = "cleanShop")
    @Operation(summary = "注销商家数据")
    public Result<Boolean> cleanShop(@RequestBody ShopGetByIdParam param) {
        Boolean result = cerePlatformShopService.cleanShop(param.getShopId());
        return new Result<>(result);
    }

    /**
     * 新建商家
     */
    @PostMapping(value = "cloneShop")
    @NoRepeatSubmit
    @Operation(summary = "新建商家")
    @WebRequestLog
    public Result<String> cloneShop(@RequestBody @Validated ShopCloneParam param, HttpServletRequest request) {
        if (param.getShopId() == null || CollectionUtils.isEmpty(param.getCloneList())) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        List<String> shopNameList = param.getCloneList().stream().map(ShopUpdateParam::getShopName).distinct().toList();
        if (shopNameList.size() < param.getCloneList().size()) {
            return new Result<>(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        for (String shopName : shopNameList) {
            //校验商家名称是否存在
            CerePlatformShop shop = cerePlatformShopService.findByShopName(shopName);
            if (shop != null) {
                return new Result<>(CoReturnFormat.SHOP_NAME_ALREADY);
            }
        }

        List<String> shopPhoneList = param.getCloneList().stream().map(ShopUpdateParam::getShopPhone).distinct().toList();
        if (shopPhoneList.size() < param.getCloneList().size()) {
            return new Result<>(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        for (String shopPhone : shopPhoneList) {
            //校验商家名称是否存在
            CerePlatformShop shop = cerePlatformShopService.findByPhone(shopPhone);
            if (shop != null) {
                return new Result<>(CoReturnFormat.SHOP_PHONE_ALREADY);
            }
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.cloneShop(param, user);
        return new Result<>(user.getUsername(), "克隆商家", GsonUtil.objectToGson(param));
    }
}
