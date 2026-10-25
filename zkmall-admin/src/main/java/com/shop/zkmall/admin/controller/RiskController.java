/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.admin.page.risk.CereRiskUserBlack;
import com.shop.zkmall.admin.param.risk.CereRiskBlackPageParam;
import com.shop.zkmall.admin.service.risk.CereRiskBlackService;
import com.shop.zkmall.admin.service.risk.CereRiskRuleService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风控模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("risk")
@RequiredArgsConstructor
@Tag(name = "风控模块", description = "风控模块")
public class RiskController {

    private final CereRiskBlackService cereRiskBlackService;

    private final CereRiskRuleService cereRiskRuleService;

    /**
     * 查询ip黑名单列表
     */
    @PostMapping(value = "getAllIpBlackList")
    @Operation(summary = "查询ip黑名单列表")
    public Result<Page<CereRiskBlack>> getAllIpBlackList(@RequestBody CereRiskBlackPageParam param) {
        Page<CereRiskBlack> page = cereRiskBlackService.getAllIpBlackList(param);
        return new Result<>(page);
    }

    /**
     * 查询用户黑名单列表
     */
    @PostMapping(value = "getAllUserBlackList")
    @Operation(summary = "查询用户黑名单列表")
    public Result<Page<CereRiskUserBlack>> getAllUserBlackList(@RequestBody CereRiskBlackPageParam param) {
        Page<CereRiskUserBlack> page = cereRiskBlackService.getUserBlackList(param);
        return new Result<>(page);
    }

    /**
     * 新增ip或用户黑名单
     */
    @PostMapping(value = "saveRiskBlack")
    @Operation(summary = "新增ip或用户黑名单")
    public Result<Long> saveRiskBlack(@RequestBody CereRiskBlack param) {
        long result = cereRiskBlackService.save(param);
        return new Result<>(result);
    }

    /**
     * 更新ip或用户黑名单
     */
    @PostMapping(value = "updateRiskBlack")
    @Operation(summary = "更新ip或用户黑名单")
    public Result<Integer> updateRiskBlack(@RequestBody CereRiskBlack param) {
        int result = cereRiskBlackService.update(param);
        return new Result<>(result);
    }

    /**
     * 删除ip或用户黑名单
     */
    @PostMapping(value = "deleteRiskBlack")
    @Operation(summary = "删除ip或用户黑名单")
    public Result<Integer> deleteRiskBlack(@RequestBody CereRiskBlack param) {
        int result = cereRiskBlackService.delete(param);
        return new Result<>(result);
    }

    /**
     * 查询规则列表
     */
    @PostMapping(value = "getAllRiskRule")
    @Operation(summary = "查询规则列表")
    public Result<Page<CereRiskRule>> getAllRiskRule(@RequestBody PageParam param) {
        Page<CereRiskRule> page = cereRiskRuleService.getAllRiskRule(param);
        return new Result<>(page);
    }

    /**
     * 新增规则配置
     */
    @PostMapping(value = "saveRiskRule")
    @Operation(summary = "新增规则配置")
    public Result<Integer> saveRiskRule(@RequestBody CereRiskRule param) {
        int result = cereRiskRuleService.save(param);
        return new Result<>(result);
    }

    /**
     * 更新规则配置
     */
    @PostMapping(value = "updateRiskRule")
    @Operation(summary = "更新规则配置")
    public Result<Integer> updateRiskRule(@RequestBody CereRiskRule param) {
        int result = cereRiskRuleService.update(param);
        return new Result<>(result);
    }

    /**
     * 删除规则配置
     */
    @PostMapping(value = "deleteRiskRule")
    @Operation(summary = "删除规则配置")
    public Result<Integer> deleteRiskRule(@RequestBody CereRiskRule param) {
        int result = cereRiskRuleService.delete(param);
        return new Result<>(result);
    }
}
