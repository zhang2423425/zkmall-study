/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.shop.Shop;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.business.param.shop.ShopUpdateLogoParam;
import com.shop.zkmall.business.service.shop.*;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.*;
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
 * 店铺
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
@Tag(name = "店铺模块", description = "店铺模块")
public class ShopController {

    private final CerePlatformShopService cerePlatformShopService;

    private final CereShopReturnService cereShopReturnService;

    private final CereShopPersonalService cereShopPersonalService;

    private final CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    private final CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    private final CereShopEnterpriseService cereShopEnterpriseService;

    /**
     * 店铺信息查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "店铺信息查询")
    public Result<Shop> getById(@RequestBody ShopParam cerePlatformShop) {
        cerePlatformShop.setShopId(ContextUtil.getShopId());
        Shop shop = cerePlatformShopService.getById(cerePlatformShop.getShopId());
        return new Result<>(shop);
    }

    /**
     * 修改店铺信息
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改店铺信息")
    @WebRequestLog
    public Result<String> updateLogo(@RequestBody ShopUpdateLogoParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cerePlatformShopService.update(param, user);
        return new Result<>(user.getUsername(), "修改店铺信息", GsonUtil.objectToGson(param));
    }

    /**
     * 修改退货地址信息
     */
    @PostMapping(value = "updateReturn")
    @NoRepeatSubmit
    @Operation(summary = "修改退货地址信息")
    @WebRequestLog
    public Result<String> updateReturn(@RequestBody CereShopReturn cereShopReturn, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopReturn.setShopId(ContextUtil.getShopId());
        cereShopReturnService.updateReturn(cereShopReturn, user);
        return new Result<>(user.getUsername(), "修改退货地址信息", GsonUtil.objectToGson(cereShopReturn));
    }

    /**
     * 店铺认证（个人）
     */
    @PostMapping(value = "personal")
    @NoRepeatSubmit
    @Operation(summary = "店铺认证（个人）")
    @WebRequestLog
    public Result<String> personal(@RequestBody CereShopPersonal personal, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        personal.setShopId(ContextUtil.getShopId());
        cereShopPersonalService.personal(personal, user);
        return new Result<>(user.getUsername(), "店铺认证（个人）", GsonUtil.objectToGson(personal));
    }

    /**
     * 店铺认证（个体工商户）
     */
    @PostMapping(value = "individual")
    @NoRepeatSubmit
    @Operation(summary = "店铺认证（个体工商户）")
    @WebRequestLog
    public Result<String> individual(@RequestBody CereShopIndividualBusinesses individualBusinesses, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        individualBusinesses.setShopId(ContextUtil.getShopId());
        cereShopIndividualBusinessesService.individual(individualBusinesses, user);
        return new Result<>(user.getUsername(), "店铺认证（个体工商户）", GsonUtil.objectToGson(individualBusinesses));
    }

    /**
     * 店铺认证（企业）
     */
    @PostMapping(value = "enterprise")
    @NoRepeatSubmit
    @Operation(summary = "店铺认证（企业）")
    @WebRequestLog
    public Result<String> enterprise(@RequestBody CereShopEnterprise enterprise, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        enterprise.setShopId(ContextUtil.getShopId());
        cereShopEnterpriseService.enterprise(enterprise, user);
        return new Result<>(user.getUsername(), "店铺认证（企业）", GsonUtil.objectToGson(enterprise));
    }

    /**
     * 店铺认证（其他组织）
     */
    @PostMapping(value = "organizations")
    @NoRepeatSubmit
    @Operation(summary = "店铺认证（其他组织）")
    @WebRequestLog
    public Result<String> organizations(@RequestBody CereShopOtherOrganizations otherOrganizations, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        otherOrganizations.setShopId(ContextUtil.getShopId());
        cereShopOtherOrganizationsService.organizations(otherOrganizations, user);
        return new Result<>(user.getUsername(), "店铺认证（其他组织）", GsonUtil.objectToGson(otherOrganizations));
    }

    /**
     * 修改店铺认证（个人）
     */
    @PostMapping(value = "updatePersonal")
    @NoRepeatSubmit
    @Operation(summary = "修改店铺认证（个人）")
    @WebRequestLog
    public Result<String> updatePersonal(@RequestBody CereShopPersonal personal, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        personal.setShopId(ContextUtil.getShopId());
        cereShopPersonalService.updatePersonal(personal, user);
        return new Result<>(user.getUsername(), "修改店铺认证（个人）", GsonUtil.objectToGson(personal));
    }

    /**
     * 修改店铺认证（个体工商户）
     */
    @PostMapping(value = "updateIndividual")
    @NoRepeatSubmit
    @Operation(summary = "修改店铺认证（个体工商户）")
    @WebRequestLog
    public Result<String> updateIndividual(@RequestBody CereShopIndividualBusinesses individualBusinesses, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        individualBusinesses.setShopId(ContextUtil.getShopId());
        cereShopIndividualBusinessesService.updateIndividual(individualBusinesses, user);
        return new Result<>(user.getUsername(), "修改店铺认证（个体工商户）", GsonUtil.objectToGson(individualBusinesses));
    }

    /**
     * 修改店铺认证（企业）
     */
    @PostMapping(value = "updateEnterprise")
    @NoRepeatSubmit
    @Operation(summary = "修改店铺认证（企业）")
    @WebRequestLog
    public Result<String> updateEnterprise(@RequestBody CereShopEnterprise enterprise, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        enterprise.setShopId(ContextUtil.getShopId());
        cereShopEnterpriseService.updateEnterprise(enterprise, user);
        return new Result<>(user.getUsername(), "修改店铺认证（企业）", GsonUtil.objectToGson(enterprise));
    }

    /**
     * 修改店铺认证（其他组织）
     */
    @PostMapping(value = "updateOrganizations")
    @NoRepeatSubmit
    @Operation(summary = "修改店铺认证（其他组织）")
    @WebRequestLog
    public Result<String> updateOrganizations(@RequestBody CereShopOtherOrganizations otherOrganizations, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        otherOrganizations.setShopId(ContextUtil.getShopId());
        cereShopOtherOrganizationsService.updateOrganizations(otherOrganizations, user);
        return new Result<>(user.getUsername(), "修改店铺认证（其他组织）", GsonUtil.objectToGson(otherOrganizations));
    }
}
