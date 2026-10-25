/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.business.service.init.InitPermissionService;
import com.shop.zkmall.commons.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化版本迭代新增菜单
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class InitPermissionController {

    private final InitPermissionService initPermissionService;

    /**
     * 初始化1.4版本新增商家端菜单
     *
     */
    @PostMapping(value = "initPermission")
    public Result<?> getAll() {
        initPermissionService.initPermission();
        return new Result<>();
    }

}
