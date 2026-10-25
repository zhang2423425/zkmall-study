/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.system;

import com.shop.zkmall.app.service.dict.CerePlatformDictService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
@Tag(name = "字典模块", description = "字典模块")
public class DictController {

    private final CerePlatformDictService cerePlatformDictService;

    /**
     * 数据字典编辑查询
     */
    @GetMapping(value = "getByName")
    @Operation(summary = "数据字典编辑查询")
    public Result<CerePlatformDict> getById(String name) {
        CerePlatformDict dict = cerePlatformDictService.getByName(name);
        return new Result<>(dict, CoReturnFormat.SUCCESS);
    }

}
