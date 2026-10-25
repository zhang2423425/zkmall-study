/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.business.param.dict.DictGetSelectParam;
import com.shop.zkmall.business.service.dict.CerePlatformDictService;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
@Tag(name = "数据字典模块", description = "数据字典模块")
public class DictController {

    private final CerePlatformDictService cerePlatformDictService;

    /**
     * 字典下拉数据查询
     */
    @PostMapping(value = "getSelect")
    @Operation(summary = "字典下拉数据查询")
    public Result<List<CerePlatformDict>> getSelect(@RequestBody DictGetSelectParam param) {
        List<CerePlatformDict> list = cerePlatformDictService.getSelect(param.getDictName());
        return new Result<>(list);
    }
}
