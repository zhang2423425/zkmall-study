/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.canvas.CanvasCoupon;
import com.shop.zkmall.admin.page.canvas.CanvasPlatformDiscount;
import com.shop.zkmall.admin.page.canvas.CanvasPlatformSeckill;
import com.shop.zkmall.admin.page.canvas.ToolProduct;
import com.shop.zkmall.admin.page.product.*;
import com.shop.zkmall.admin.page.shop.Shop;
import com.shop.zkmall.admin.param.canvas.CanvasActivity;
import com.shop.zkmall.admin.param.canvas.CanvasPageParam;
import com.shop.zkmall.admin.param.product.CanvasAdminProductParam;
import com.shop.zkmall.admin.param.product.IdsParam;
import com.shop.zkmall.admin.param.product.ProductGetAllParam;
import com.shop.zkmall.admin.param.product.ProductGetByIdParam;
import com.shop.zkmall.admin.service.canvas.CerePlatformCanvasCustomService;
import com.shop.zkmall.admin.service.canvas.CerePlatformCanvasService;
import com.shop.zkmall.admin.service.notice.CereNoticeService;
import com.shop.zkmall.admin.service.product.CereProductClassifyService;
import com.shop.zkmall.admin.service.product.CereProductMemberService;
import com.shop.zkmall.admin.service.product.CereShopProductService;
import com.shop.zkmall.admin.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasCustom;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.product.Classify;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.ProjectInvokeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 画布模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("canvas")
@RequiredArgsConstructor
@Tag(name = "画布模块", description = "画布模块")
public class CanvasController {

    private final CereShopProductService cereShopProductService;

    private final CereProductClassifyService cereProductClassifyService;

    private final CerePlatformCanvasService cerePlatformCanvasService;

    private final CerePlatformCanvasCustomService cerePlatformCanvasCustomService;

    private final CereProductMemberService cereProductMemberService;

    private final CereNoticeService cereNoticeService;

    private final ProjectInvokeUtil projectInvokeUtil;

    private final CerePlatformShopService cerePlatformShopService;

    /**
     * 画布选择商品查询
     */
    @GetMapping("getProducts")
    @Operation(summary = "画布选择商品查询")
    public Result<Page<CanvasProduct>> getProducts(CanvasAdminProductParam param) throws Exception {
        Page<CanvasProduct> page = cereShopProductService.getProducts(param);
        return new Result<>(page);
    }


    /**
     * 查询会员商品数据
     */
    @GetMapping("getMemberProducts")
    @Operation(summary = "查询会员商品数据")
    public Result<Page<MemberProduct>> getMemberProducts(CanvasAdminProductParam param) {
        Page<MemberProduct> page = cereProductMemberService.getMemberProducts(param);
        return new Result<>(page);
    }

    /**
     * 获取公告数据
     */
    @GetMapping("getNotices")
    @Operation(summary = "获取公告数据")
    public Result<List<CereNotice>> getNotices() {
        List<CereNotice> list = cereNoticeService.getNotices();
        return new Result<>(list);
    }

    /**
     * 选择平台端限时折扣活动查询
     */
    @GetMapping("getMinDiscount")
    @Operation(summary = "选择平台端限时折扣活动查询")
    public Result<List<CanvasPlatformDiscount>> getMinDiscount() {
        return new Result<>(Collections.emptyList());
    }

    /**
     * 选择平台端秒杀活动查询
     */
    @GetMapping("getPlatformSeckills")
    @Operation(summary = "选择平台端秒杀活动查询")
    public Result<List<CanvasPlatformSeckill>> getPlatformSeckills() {
        return new Result<>(Collections.emptyList());
    }

    /**
     * 选择拼团活动查询
     */
    @GetMapping("getGroupWorks")
    @Operation(summary = "选择拼团活动查询")
    public Result<List<ToolProduct>> getGroupWorks() {
        return new Result<>(Collections.emptyList());
    }

    /**
     * 画布选择店铺查询
     */
    @GetMapping("getShops")
    @Operation(summary = "画布选择店铺查询")
    public Result<Page<CanvasShop>> getShops(CanvasAdminProductParam param) {
        Page<CanvasShop> page = cereShopProductService.getShops(param);
        return new Result<>(page);
    }

    /**
     * 查询分类层级
     */
    @GetMapping("getClassify")
    @Operation(summary = "查询分类层级")
    public Result<List<Classify>> getClassify(IdsParam param) {
        List<Classify> list;
        if (param != null && CollectionUtils.isNotEmpty(param.getIds())) {
            list = cereProductClassifyService.getClassifyByIds(param.getIds());
        } else {
            list = cereProductClassifyService.getClassify();
        }
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询所有平台优惠券
     */
    @GetMapping("getCoupons")
    @Operation(summary = "查询分类层级")
    public Result<Page<CanvasCoupon>> getCoupons() {
        return new Result<>(new Page<>(Collections.emptyList(), 0));
    }

    /**
     * 保存画布
     */
    @PostMapping("saveCanvas")
    @Operation(summary = "保存画布")
    @WebRequestLog
    public Result<String> saveCanvas(@RequestBody CerePlatformCanvas canvas, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformCanvasService.saveCanvas(canvas, user);
        return new Result<>(user.getUsername(), "保存画布", GsonUtil.objectToGson(canvas));
    }

    /**
     * 读取画布数据
     */
    @GetMapping("getCanvas")
    @Operation(summary = "读取画布数据")
    public Result<CerePlatformCanvas> getCanvas(CerePlatformCanvas canvas) {
        canvas = cerePlatformCanvasService.getCanvas(canvas);
        return new Result<>(canvas);
    }

    /**
     * 删除画布
     */
    @PostMapping("delCanvas")
    @Operation(summary = "删除画布")
    @WebRequestLog
    public Result<String> delCanvas(@RequestBody CerePlatformCanvas canvas, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformCanvasService.delCanvas(canvas, user);
        return new Result<>(user.getUsername(), "删除画布", GsonUtil.objectToGson(canvas));
    }

    /**
     * 查询画布列表
     */
    @PostMapping("selectCanvasList")
    @Operation(summary = "查询画布列表")
    public Result<Page<CerePlatformCanvas>> selectCanvasList(@RequestBody CanvasPageParam param) {
        Page<CerePlatformCanvas> page = cerePlatformCanvasService.selectCanvasList(param);
        return new Result<>(page);
    }

    /**
     * 保存自定义页面
     */
    @PostMapping("saveCanvasCustom")
    @Operation(summary = "保存自定义页面")
    @WebRequestLog
    public Result<String> saveCanvasCustom(@RequestBody CerePlatformCanvasCustom canvasCustom, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformCanvasCustomService.saveCanvasCustom(canvasCustom, user);
        return new Result<>(user.getUsername(), "保存自定义页面", GsonUtil.objectToGson(canvasCustom));
    }

    /**
     * 删除自定义页面
     */
    @PostMapping("delCanvasCustom")
    @Operation(summary = "删除自定义页面")
    @WebRequestLog
    public Result<String> delCanvasCustom(@RequestBody CerePlatformCanvasCustom canvasCustom, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformCanvasCustomService.delCanvasCustom(canvasCustom, user);
        return new Result<>(user.getUsername(), "删除自定义页面", GsonUtil.objectToGson(canvasCustom));
    }

    /**
     * 查询自定义页面列表
     */
    @PostMapping("selectCanvasCustomList")
    @Operation(summary = "查询自定义页面列表")
    public Result<Page<CerePlatformCanvasCustom>> selectCanvasCustomList(@RequestBody CanvasPageParam param) {
        Page<CerePlatformCanvasCustom> page = cerePlatformCanvasCustomService.selectCanvasCustomList(param);
        return new Result<>(page);
    }

    /**
     * 商品管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "商品管理查询")
    public Result<Page<ShopProduct>> getAll(@RequestBody ProductGetAllParam param) {
        Page<ShopProduct> page = cereShopProductService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 商品管理导出
     */
    @PostMapping(value = "exportAll")
    @NoRepeatSubmit
    @Operation(summary = "商品管理导出")
    public void exportAll(@RequestBody ProductGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        List<ProductExportDTO> productExportList = cereShopProductService.getExportList(param);
        //定义导出的excel名字
        String excelName = "商品列表";
        ExcelExportUtils.exportExcel(request, response, excelName, productExportList, ProductExportDTO.class);
    }

    /**
     * 商品详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "商品详情查询")
    public Result<ProductDetail> getById(@RequestBody ProductGetByIdParam param) {
        ProductDetail detail = cereShopProductService.getById(param.getProductId());
        return new Result<>(detail);
    }

    /**
     * 商品审核
     */
    @PostMapping(value = "examine")
    @NoRepeatSubmit
    @Operation(summary = "商品审核")
    @WebRequestLog
    public Result<String> examine(@RequestBody ProductExamineParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        boolean ret = cereShopProductService.examine(param, user);
        if (ret) {
            //刷新实时信息
            projectInvokeUtil.postRefreshSkuRealInfo(param.getProductId(), RefreshSkuRealInfoSourceEnum.PUT_ON_SHELVE);
        }
        return new Result<>(user.getUsername(), "商品审核", GsonUtil.objectToGson(param));
    }

    /**
     * 设置虚拟销量
     */
    @PostMapping(value = "setFictitious")
    @NoRepeatSubmit
    @Operation(summary = "设置虚拟销量")
    @WebRequestLog
    public Result<String> setFictitious(@RequestBody ProductUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopProductService.setFictitious(param, user);
        return new Result<>(user.getUsername(), "设置虚拟销量", GsonUtil.objectToGson(param));
    }

    /**
     * 强制下架
     */
    @PostMapping(value = "forced")
    @NoRepeatSubmit
    @Operation(summary = "强制下架")
    @WebRequestLog
    public Result<String> forced(@RequestBody ProductUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopProductService.forced(param, user);
        return new Result<>(user.getUsername(), "强制下架", GsonUtil.objectToGson(param));
    }

    @GetMapping("selectLiveList")
    @Operation(summary = "查询直播列表")
    public Result<?> selectLiveList() {
        return new Result<>(Collections.emptyList());
    }

    /**
     * 查询商品推荐
     */
    @GetMapping("selectRecommendProductList")
    @Operation(summary = "查询商品推荐")
    public Result<Page<CanvasProduct>> selectRecommendProductList(CanvasAdminProductParam param) {
        Page<CanvasProduct> list = cereShopProductService.selectRecommendProductList(param);
        return new Result<>(list);
    }

    /**
     * 查询人气商家top5
     */
    @GetMapping("selectHotShopList")
    @Operation(summary = "查询人气商家top5")
    public Result<List<Shop>> selectHotShopList() {
        List<Shop> indexList = cerePlatformShopService.selectHotShopList();
        return new Result<>(indexList, CoReturnFormat.SUCCESS);
    }

    /**
     * 查询最新活动
     */
    @GetMapping("selectMostNewActivity")
    @Operation(summary = "查询最新活动top5")
    public Result<List<CanvasActivity>> selectMostNewActivity() {
        List<CanvasActivity> list = cereShopProductService.selectMostNewActivity();
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

}
