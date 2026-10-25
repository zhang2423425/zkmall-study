/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalGetByIdParam;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.zkmall.admin.service.shop.CereShopWithdrawalService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopWithdrawal;
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
 * 提现申请
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("withdrawal")
@RequiredArgsConstructor
@Tag(name = "提现申请模块", description = "提现申请模块")
public class WithdrawalController {

    private final CereShopWithdrawalService cereShopWithdrawalService;

    /**
     * 提现申请查看
     */
    @PostMapping(value = "getById")
    @Operation(summary = "提现申请查看")
    public Result<CereShopWithdrawal> getById(@RequestBody WithdrawalGetByIdParam param) {
        CereShopWithdrawal withdrawal = cereShopWithdrawalService.getById(param.getWithdrawalId());
        return new Result<>(withdrawal);
    }

    /**
     * 提现申请处理
     */
    @PostMapping(value = "handle")
    @NoRepeatSubmit
    @Operation(summary = "提现申请处理")
    @WebRequestLog
    public Result<String> handle(@RequestBody WithdrawalHandleParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopWithdrawalService.handle(param, user);
        return new Result<>(user.getUsername(), "提现申请处理", GsonUtil.objectToGson(param));
    }

    /**
     * 提现申请管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "提现申请管理查询")
    public Result<Page<CereShopWithdrawal>> getAll(@RequestBody WithdrawalGetAllParam param) {
        Page<CereShopWithdrawal> page = cereShopWithdrawalService.getAll(param);
        return new Result<>(page);
    }
}
