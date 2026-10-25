/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.finance.*;
import com.shop.zkmall.business.param.finance.*;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.business.service.activity.CereActivitySignService;
import com.shop.zkmall.business.service.order.CereShopOrderService;
import com.shop.zkmall.business.service.shop.CereShopWithdrawalService;
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
 * 财务
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("finance")
@RequiredArgsConstructor
@Tag(name = "财务模块", description = "财务模块")
public class FinanceController {

    private final CereShopOrderService cereShopOrderService;

    private final CereShopWithdrawalService cereShopWithdrawalService;

    private final CereActivitySignService cereActivitySignService;

    /**
     * 财务统计数据查询
     */
    @PostMapping(value = "getFinanceCount")
    @Operation(summary = "财务统计数据查询")
    public Result<FinanceCount> getFinanceCount(@RequestBody FinanceCountParam param) {
        param.setShopId(ContextUtil.getShopId());
        FinanceCount count = cereShopOrderService.getFinanceCount(param);
        return new Result<>(count);
    }

    /**
     * 查询绑定银行卡
     */
    @PostMapping(value = "getBank")
    @Operation(summary = "查询绑定银行卡")
    public Result<Bank> getBank(@RequestBody ShopParam param) {
        param.setShopId(ContextUtil.getShopId());
        Bank bank = cereShopOrderService.getBank(param.getShopId());
        return new Result<>(bank);
    }

    /**
     * 提现明细查询
     */
    @PostMapping(value = "getWithdrawalDetails")
    @Operation(summary = "提现明细查询")
    public Result<List<WithdrawalDetail>> getWithdrawalDetails(@RequestBody FinanceWithdrawalParam param) {
        param.setShopId(ContextUtil.getShopId());
        List<WithdrawalDetail> list = cereShopOrderService.getWithdrawalDetails(param);
        return new Result<>(list);
    }

    /**
     * 流水明细
     */
    @PostMapping(value = "getDetails")
    @Operation(summary = "流水明细")
    public Result<Page<FlowingWater>> getDetails(@RequestBody FinanceDetailParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<FlowingWater> page = cereShopOrderService.getDetails(param);
        return new Result<>(page);
    }

    /**
     * 提现申请
     */
    @PostMapping(value = "withdrawal")
    @NoRepeatSubmit
    @Operation(summary = "提现申请")
    @WebRequestLog
    public Result<String> withdrawal(@RequestBody FinanceSaveWithdralwalParam withdrawal, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        withdrawal.setShopId(ContextUtil.getShopId());
        cereShopWithdrawalService.save(withdrawal, user);
        return new Result<>(user.getUsername(), "提现申请", GsonUtil.objectToGson(withdrawal));
    }

    /**
     * 保证金管理查询
     */
    @PostMapping(value = "getAllBond")
    @Operation(summary = "保证金管理查询")
    public Result<BondCount> getAllBond(@RequestBody BondParam param) {
        param.setShopId(ContextUtil.getShopId());
        BondCount count = cereActivitySignService.getAllBond(param);
        return new Result<>(count);
    }
}
