/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.admin.param.product.RecommendProductParam;
import com.shop.zkmall.admin.service.product.CereShopProductService;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkmall zkmallvip@163.com
 */
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Tag(name = "商品管理模块", description = "商品管理模块")
public class ProductController {

    private final CereShopProductService cereShopProductService;

    /**
     * 推荐商品数据
     */
    @PostMapping(value = "recommendProduct")
    @NoRepeatSubmit
    @Operation(summary = "推荐商品")
    public Result<String> recommendProduct(@RequestBody RecommendProductParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopProductService.recommendProduct(param, user);
        return new Result<>(user.getUsername(), "推荐商品", GsonUtil.objectToGson(param));
    }

}
