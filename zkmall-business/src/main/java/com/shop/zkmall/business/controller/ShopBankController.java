/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.shop.ShopBankDetail;
import com.shop.zkmall.business.param.bank.BankDeleteParam;
import com.shop.zkmall.business.param.bank.BankSaveParam;
import com.shop.zkmall.business.param.bank.BankUpdateParam;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.business.service.shop.CereShopBankService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.ValidationUtil;
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
 * 店铺收款账户
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
@Tag(name = "店铺收款账户", description = "店铺收款账户")
public class ShopBankController {

    private final CereShopBankService cereShopBankService;

    /**
     * 绑定账户
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "绑定账户")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated BankSaveParam param, HttpServletRequest request) {
        //校验验证码
        ValidationUtil.checkValidateCode(param.getPhone(), param.getCode());
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBankService.save(param, user);
        return new Result<>(user.getUsername(), "绑定账户", GsonUtil.objectToGson(param));
    }

    /**
     * 更换账户
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "更换账户")
    @WebRequestLog
    public Result<String> update(@RequestBody BankUpdateParam param, HttpServletRequest request) {
        //校验验证码
        ValidationUtil.checkValidateCode(param.getPhone(), param.getCode());
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBankService.update(param, user);
        return new Result<>(user.getUsername(), "更换账户", GsonUtil.objectToGson(param));
    }

    /**
     * 解绑账户
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "解绑账户")
    @WebRequestLog
    public Result<String> delete(@RequestBody BankDeleteParam param, HttpServletRequest request) {
        //校验验证码
        ValidationUtil.checkValidateCode(param.getPhone(), param.getCode());
        //校验手机号是否为店铺账号
        param.setShopId(ContextUtil.getShopId());
        CerePlatformShop shop = cereShopBankService.findByPhone(param.getShopId(), param.getPhone());
        if (shop == null) {
            return new Result<>(CoReturnFormat.PHONE_NOT_BANK_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopBankService.delete(param, user);
        return new Result<>(user.getUsername(), "解绑账户", GsonUtil.objectToGson(param));
    }

    /**
     * 收款账户信息查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "收款账户信息查询")
    public Result<ShopBankDetail> getById(@RequestBody ShopParam param) {
        param.setShopId(ContextUtil.getShopId());
        ShopBankDetail bank = cereShopBankService.getById(param.getShopId());
        return new Result<>(bank);
    }

}
