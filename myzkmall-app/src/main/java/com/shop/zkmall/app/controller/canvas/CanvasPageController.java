/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.canvas;

import com.shop.zkmall.app.service.canvas.CerePlatformCanvasPageService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新版画布选择商品查询
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("canvasPage")
@RequiredArgsConstructor
@Tag(name = "新版画布选择商品查询", description = "新版画布选择商品查询")
public class CanvasPageController {

    private final CerePlatformCanvasPageService cerePlatformCanvasPageService;

    /**
     * 画布选择商品查询
     */
    @GetMapping("getHomePage")
    @Operation(summary = "查询首页模板")
    public Result<CerePlatformCanvasPage> getHomePage(CerePlatformCanvasPage param) {
        CerePlatformCanvasPage page = cerePlatformCanvasPageService.getHomePage(param);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }
}
