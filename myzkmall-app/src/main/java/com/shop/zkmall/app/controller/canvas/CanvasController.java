/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.canvas;

import com.shop.zkmall.app.page.canvas.CanvasProduct;
import com.shop.zkmall.app.page.notice.BuyerNotice;
import com.shop.zkmall.app.page.shop.ShopIndex;
import com.shop.zkmall.app.param.canvas.CanvasAppProductParam;
import com.shop.zkmall.app.param.canvas.IdsParam;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.index.IndexService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.Classify;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 画布选择商品查询
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("canvas")
@RequiredArgsConstructor
@Tag(name = "画布选择商品查询", description = "画布选择商品查询")
public class CanvasController {

    private final CereShopProductService cereShopProductService;

    private final IndexService indexService;

    private final CereNoticeService cereNoticeService;

    private final CerePlatformShopService cerePlatformShopService;

    private final UserRedisService userRedisService;

    /**
     * 画布选择商品查询
     */
    @GetMapping("getProducts")
    @Operation(summary = "商家编辑查询")
    public Result<Page<CanvasProduct>> getProducts(CanvasAppProductParam param, HttpServletRequest request) {
        Long buyerUserId = null;
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            buyerUserId = userRedisService.getBuyerUserIdByToken(token);
        }
        param.setBuyerUserId(buyerUserId);
        Page<CanvasProduct> page = cereShopProductService.getCanvasProducts(param);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 画布选择商品查询新接口,如果调用有问题可以切换回getProducts接口
     */
    @GetMapping("getProducts2")
    @Operation(summary = "商家编辑查询新接口")
    public Result<Page<CanvasProduct>> getProducts2(CanvasAppProductParam param) {
        Page<CanvasProduct> page = cereShopProductService.getProducts2(param);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询分类层级
     */
    @GetMapping("getClassify")
    @Operation(summary = "查询分类层级")
    public Result<List<Classify>> getClassify(IdsParam param) {
        List<Classify> list;
        if (param != null && CollectionUtils.isNotEmpty(param.getIds())) {
            list = cereShopProductService.getClassifyByIds(param.getIds());
        } else {
            list = cereShopProductService.getClassify();
        }
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 读取画布数据
     */
    @GetMapping("getCanvas")
    @Operation(summary = "读取画布数据")
    public Result<CerePlatformCanvas> getCanvas(CerePlatformCanvas canvas) {
        canvas = indexService.getCanvas(canvas);
        return new Result<>(canvas, CoReturnFormat.SUCCESS);
    }

    /**
     * 获取公告数据
     */
    @GetMapping("getNotices")
    @Operation(summary = "获取公告数据")
    public Result<List<BuyerNotice>> getNotices() {
        return new Result<>(cereNoticeService.getNotices(), CoReturnFormat.SUCCESS);
    }

    /**
     * 查询商品推荐
     */
    @GetMapping("selectRecommendProductList")
    @Operation(summary = "查询商品推荐")
    public Result<Page<CanvasProduct>> selectRecommendProductList(CanvasAppProductParam param, HttpServletRequest request) {
        Long buyerUserId = null;
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        if (user != null) {
            buyerUserId = user.getBuyerUserId();
        }
        Page<CanvasProduct> list = cereShopProductService.selectRecommendProductList(buyerUserId, param);
        return new Result<>(list);
    }

    /**
     * 查询人气商家top5
     */
    @GetMapping("selectHotShopList")
    @Operation(summary = "查询人气商家top5")
    public Result<List<ShopIndex>> selectHotShopList() {
        List<ShopIndex> indexList = cerePlatformShopService.selectHotShopList();
        return new Result<>(indexList, CoReturnFormat.SUCCESS);
    }
}
