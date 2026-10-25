/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.classify;

import com.shop.zkmall.app.page.classify.Classify;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.param.classify.ClassifyParam;
import com.shop.zkmall.app.param.classify.ClassifyProductParam;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.product.CereProductClassifyService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("classify")
@RequiredArgsConstructor
@Tag(name = "分类模块", description = "分类模块")
public class ClassifyController {

    private final CereProductClassifyService cereProductClassifyService;

    private final CereBuyerUserService cereBuyerUserService;

    /**
     * 分类商品查询
     */
    @GetMapping("getClassifyProducts")
    @Operation(summary = "商品搜索查询")
    public Result<Page<Product>> getClassifyProducts(ClassifyProductParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Page<Product> page = cereProductClassifyService.getClassifyProducts(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 分类商品查询新接口，如果调用有问题可以切换回getProducts接口
     */
    @GetMapping("getClassifyProducts2")
    @Operation(summary = "商品搜索查询新接口")
    public Result<Page<Product>> getClassifyProducts2(ClassifyProductParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Page<Product> page = cereProductClassifyService.getClassifyProducts2(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询所有一级类目
     */
    @GetMapping("getFirstClassify")
    @Operation(summary = "查询店铺分类")
    public Result<List<Classify>> getFirstClassify(ClassifyParam param) {
        List<Classify> list = cereProductClassifyService.getFirstClassify(param);
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }
}
