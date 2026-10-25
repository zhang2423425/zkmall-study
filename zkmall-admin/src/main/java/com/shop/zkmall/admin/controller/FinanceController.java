/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.admin.page.finance.BondCount;
import com.shop.zkmall.admin.page.finance.FinanceCount;
import com.shop.zkmall.admin.param.finance.BondParam;
import com.shop.zkmall.admin.param.finance.FinanceParam;
import com.shop.zkmall.admin.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 财务管理
 *
 * @author zkmall zkmallvip@163.com
 */
@RestController
@RequestMapping("finance")
@RequiredArgsConstructor
@Tag(name = "财务管理模块", description = "财务管理模块")
public class FinanceController {

    private final CerePlatformShopService cerePlatformShopService;

    /**
     * 财务管理查询
     */
    @PostMapping(value = "getAllFinance")
    @Operation(summary = "财务管理查询")
    public Result<FinanceCount> getAllFinance(@RequestBody FinanceParam param) {
        FinanceCount count = cerePlatformShopService.getAllFinance(param);
        return new Result<>(count);
    }

    /**
     * 保证金管理查询
     */
    @PostMapping(value = "getAllBond")
    @Operation(summary = "保证金管理查询")
    public Result<BondCount> getAllBond(@RequestBody BondParam param) {
        return new Result<>();
    }
}
