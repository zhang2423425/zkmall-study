/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.admin.param.admin.IndexCharts;
import com.shop.zkmall.admin.param.admin.IndexStats;
import com.shop.zkmall.admin.service.admin.AdminService;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("index")
@RequiredArgsConstructor
@Tag(name = "统计模块", description = "统计模块")
public class IndexController {

    private final AdminService adminService;

    /**
     * 查询平台统计数据
     */
    @PostMapping(value = "indexStats")
    @Operation(summary = "查询平台首页统计数据(新增用户、浏览量、访客数、店铺数)")
    public Result<IndexStats> indexStats() {
        IndexStats stats = adminService.indexStats();
        return new Result<>(stats);
    }

    /**
     * 查询平台首页统计图表
     */
    @PostMapping(value = "indexCharts")
    @NoRepeatSubmit
    @Operation(summary = "查询平台首页统计图表(订单金额、订单数)")
    public Result<IndexCharts> indexCharts() {
        IndexCharts charts = adminService.indexCharts();
        return new Result<>(charts);
    }

}
