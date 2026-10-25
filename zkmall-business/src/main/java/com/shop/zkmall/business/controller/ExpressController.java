/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.business.service.dict.CerePlatformDictService;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物流
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("express")
@RequiredArgsConstructor
@Tag(name = "物流", description = "物流")
public class ExpressController {

    private final CerePlatformDictService cerePlatformDictService;

    /**
     * 选择物流公司查询
     */
    @PostMapping(value = "getExpressSelect")
    @Operation(summary = "选择物流公司查询")
    public Result<List<CerePlatformDict>> getExpressSelect() {
        List<CerePlatformDict> list = cerePlatformDictService.getExpressSelect();
        return new Result<>(list);
    }
}
