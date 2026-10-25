/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.admin.service.mark.MarkDataService;
import com.shop.zkmall.commons.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 造数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("mark")
@RequiredArgsConstructor
public class MakingDataController {

    private final MarkDataService markDataService;

    /**
     * 创建订单
     */
    @PostMapping(value = "createOrder")
    @NoRepeatSubmit
    public Result<String> createOrder() {
        markDataService.createOrder();
        return new Result<>();
    }

    /**
     * 创建售后单
     */
    @PostMapping(value = "createAfter")
    @NoRepeatSubmit
    public Result<String> createAfter() {
        markDataService.createAfter();
        return new Result<>();
    }
}
