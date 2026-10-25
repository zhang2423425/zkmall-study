/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.brand.BrandGetAllParam;
import com.shop.zkmall.admin.param.brand.BrandParam;
import com.shop.zkmall.admin.service.brand.BrandService;
import com.shop.zkmall.commons.domain.brand.Brand;
import com.shop.zkmall.commons.domain.common.Page;
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
 * 品牌管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("brand")
@RequiredArgsConstructor
@Tag(name = "品牌管理模块", description = "品牌管理模块")
public class BrandController {

    private final BrandService brandService;

    /**
     * 品牌管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "品牌管理查询")
    public Result<Page<Brand>> getAll(@RequestBody BrandGetAllParam param) {
        Page<Brand> page = brandService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 品牌详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "品牌详情查询")
    public Result<Brand> getById(@RequestBody BrandParam param) {
        Brand brand = brandService.getById(param.getId());
        return new Result<>(brand);
    }

    /**
     * 新增品牌
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "新增品牌")
    @WebRequestLog
    public Result<Integer> save(@RequestBody BrandParam param) {
        return new Result<>(brandService.save(param));
    }

    /**
     * 更新品牌
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "更新品牌")
    @WebRequestLog
    public Result<Integer> update(@RequestBody BrandParam param) {
        return new Result<>(brandService.update(param));
    }

    /**
     * 删除品牌
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除品牌")
    @WebRequestLog
    public Result<Integer> delete(@RequestBody BrandParam param) {
        return new Result<>(brandService.delete(param.getId()));
    }

}
