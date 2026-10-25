/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.buyer.BuyerWithdrawal;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.zkmall.admin.service.buyer.CereBuyerWithdrawalService;
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
 * 用户提现
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("buyer_withdrawal")
@RequiredArgsConstructor
@Tag(name = "用户提现", description = "用户提现")
public class BuyerWithdrawalController {

    private final CereBuyerWithdrawalService cereBuyerWithdrawalService;

    /**
     * 用户提现申请管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "用户提现申请管理查询")
    public Result<Page<BuyerWithdrawal>> getAll(@RequestBody BuyerWithdrawalGetAllParam param) {
        Page<BuyerWithdrawal> page = cereBuyerWithdrawalService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 查看
     */
    @PostMapping(value = "getById")
    @Operation(summary = "查看")
    public Result<BuyerWithdrawal> getById(@RequestBody BuyerWithdrawalGetByIdParam param) {
        BuyerWithdrawal withdrawal = cereBuyerWithdrawalService.getById(param.getWithdrawalId());
        return new Result<>(withdrawal);
    }

    /**
     * 提现处理
     */
    @PostMapping(value = "handle")
    @Operation(summary = "提现处理")
    @WebRequestLog
    public Result<String> handle(@RequestBody BuyerWithdrawalGetByIdParam param,
                                 HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerWithdrawalService.handle(param, user);
        return new Result<>(user.getUsername(), "提现处理", GsonUtil.objectToGson(param));
    }
}
