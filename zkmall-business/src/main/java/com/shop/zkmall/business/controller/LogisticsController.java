/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.city.City;
import com.shop.zkmall.business.page.logistics.Logistics;
import com.shop.zkmall.business.param.logistics.*;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.business.service.city.CereCityManageService;
import com.shop.zkmall.business.service.logistics.CereOrderLogisticsService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物流方案
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("logistics")
@RequiredArgsConstructor
@Tag(name = "物流方案模块", description = "物流方案模块")
public class LogisticsController {

    private final CereOrderLogisticsService cereOrderLogisticsService;

    private final CereCityManageService cereCityManageService;

    /**
     * 添加物流方案
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加物流方案")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated LogistSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereOrderLogisticsService.save(param, user);
        return new Result<>(user.getUsername(), "添加物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 修改物流方案
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改物流方案")
    @WebRequestLog
    public Result<String> update(@RequestBody LogistUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereOrderLogisticsService.update(param, user);
        return new Result<>(user.getUsername(), "修改物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 删除物流方案
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除物流方案")
    @WebRequestLog
    public Result<String> delete(@RequestBody LogistDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderLogisticsService.delete(param, user);
        return new Result<>(user.getUsername(), "删除物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 物流方案修改查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "物流方案修改查询")
    public Result<Logistics> getById(@RequestBody LogistGetByIdParam param) {
        Logistics logistics = cereOrderLogisticsService.getById(param.getLogisticsId());
        return new Result<>(logistics);
    }

    /**
     * 物流方案管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "物流方案管理查询")
    public Result<Page<Logistics>> getAll(@RequestBody ShopParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<Logistics> page = cereOrderLogisticsService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 选择城市查询（根据上级节点查询下级节点数据）
     */
    @PostMapping(value = "getCitySelect")
    @Operation(summary = "选择城市查询（根据上级节点查询下级节点数据）")
    public Result<List<City>> getCitySelect(@RequestBody LogistCityParam param) {
        List<City> list = cereCityManageService.getCitySelect(param);
        return new Result<>(list);
    }
}
