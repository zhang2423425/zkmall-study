/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.member.ProductMember;
import com.shop.zkmall.business.page.product.ProductExportDTO;
import com.shop.zkmall.business.page.product.ShopProduct;
import com.shop.zkmall.business.page.product.SyncShopProduct;
import com.shop.zkmall.business.page.tool.ToolProduct;
import com.shop.zkmall.business.param.business.BusinessSyncToScrmParam;
import com.shop.zkmall.business.param.product.*;
import com.shop.zkmall.business.param.tool.ToolSkuParam;
import com.shop.zkmall.business.service.brand.BrandService;
import com.shop.zkmall.business.service.product.CereProductClassifyService;
import com.shop.zkmall.business.service.product.CereProductSkuService;
import com.shop.zkmall.business.service.product.CereShopProductService;
import com.shop.zkmall.business.service.shop.CereShopGroupService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.business.utils.ScrmSyncVerifyUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import com.shop.zkmall.commons.domain.brand.Brand;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import com.shop.zkmall.commons.domain.product.Classify;
import com.shop.zkmall.commons.domain.shop.CereShopGroup;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.ProjectInvokeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * 商品
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Tag(name = "商品模块", description = "商品模块")
public class ProductController {

    private final CereShopProductService cereShopProductService;

    private final CereProductClassifyService cereProductClassifyService;

    private final CereShopGroupService cereShopGroupService;

    private final CereProductSkuService cereProductSkuService;

    private final ScrmSyncVerifyUtil scrmSyncVerifyUtil;

    private final BrandService brandService;

    private final ProjectInvokeUtil projectInvokeUtil;

    /**
     * 查询分类层级
     */
    @PostMapping(value = "getClassify")
    @Operation(summary = "查询分类层级")
    public Result<List<Classify>> getClassify() {
        List<Classify> list = cereProductClassifyService.getClassify();
        return new Result<>(list);
    }

    /**
     * 添加商品
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加商品")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated ProductSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopProductService.save(param, user);
        return new Result<>(user.getUsername(), "添加商品", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商品
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改商品")
    @WebRequestLog
    public Result<String> update(@RequestBody ProductUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopProductService.update(param, user);
        return new Result<>(user.getUsername(), "修改商品", GsonUtil.objectToGson(param));
    }

    /**
     * 删除商品
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除商品")
    @WebRequestLog
    public Result<String> delete(@RequestBody ProductDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopProductService.delete(param, user);
        return new Result<>(user.getUsername(), "删除商品", GsonUtil.objectToGson(param));
    }

    /**
     * 商品上下架
     */
    @PostMapping(value = "start")
    @NoRepeatSubmit
    @Operation(summary = "商品上下架")
    @WebRequestLog
    public Result<String> start(@RequestBody ProductUpDownParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopProductService.start(param, user);
        return new Result<>(user.getUsername(), "商品上下架", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商品查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "修改商品查询")
    public Result<ShopProduct> getById(@RequestBody ProductGetByIdParam param) {
        ShopProduct shopProduct = cereShopProductService.getById(param.getProductId());
        return new Result<>(shopProduct);
    }

    /**
     * 商品管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "商品管理查询")
    public Result<Page<ShopProduct>> getAll(@RequestBody ProductGetAllParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<ShopProduct> page = cereShopProductService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 商品会员价格数据查询
     */
    @PostMapping(value = "getProductMembers")
    @Operation(summary = "商品会员价格数据查询")
    public Result<List<ProductMember>> getProductMembers(@RequestBody ProductGetByIdParam param) {
        List<ProductMember> list = cereShopProductService.getProductMembers(param.getProductId());
        return new Result<>(list);
    }

    /**
     * 商品设置会员价
     */
    @PostMapping(value = "setProductMember")
    @Operation(summary = "商品设置会员价")
    public Result<String> setProductMember(@RequestBody ProductMemberParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopProductService.setProductMember(param);
        //刷新sku实时信息
        projectInvokeUtil.postRefreshSkuRealInfo(param.getProductId(), null, RefreshSkuRealInfoSourceEnum.EDIT_MEMBER_PRODUCT, null);
        return new Result<>(user.getUsername(), "设置会员价格", GsonUtil.objectToGson(param));
    }

    /**
     * 商品清除会员价
     */
    @PostMapping(value = "clearProductMember")
    @Operation(summary = "商品清除会员价")
    public Result<String> clearProductMember(@RequestBody ProductGetByIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopProductService.clearProductMember(param.getProductId());
        return new Result<>(user.getUsername(), "清除会员价格", GsonUtil.objectToGson(param));
    }

    /**
     * 选择类别查询(根据上级节点查询下级)
     */
    @PostMapping(value = "getClassifySelect")
    @Operation(summary = "选择类别查询(根据上级节点查询下级)")
    public Result<List<CereProductClassify>> getClassifySelect(@RequestBody ProductGetClassifyParam param) {
        List<CereProductClassify> list = cereProductClassifyService.getClassifySelect(param);
        return new Result<>(list);
    }

    /**
     * 选择商品分组查询
     */
    @PostMapping(value = "getGroupSelect")
    @Operation(summary = "选择商品分组查询")
    public Result<List<CereShopGroup>> getGroupSelect(@RequestBody ProductGetGroupParam param) {
        param.setShopId(ContextUtil.getShopId());
        List<CereShopGroup> list = cereShopGroupService.getGroupSelect(param);
        return new Result<>(list);
    }

    /**
     * 导入商品模板表下载
     */
    @PostMapping(value = "downloadTemplate")
    @Operation(summary = "导入商品模板表下载")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            ClassPathResource resource = new ClassPathResource("static/导入商品模板表.xlsx");
            InputStream in = resource.getInputStream();
            XSSFWorkbook excel = new XSSFWorkbook(in);
            String str = "商品导入模板表";
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + str + ".xlsx");
            response.flushBuffer();
            excel.write(response.getOutputStream());
            excel.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 导入商品数据
     */
    @PostMapping(value = "importProduct")
    @NoRepeatSubmit
    @Operation(summary = "导入商品数据")
    public Result<String> importProduct(MultipartFile file) throws Exception {
        if (EmptyUtils.isEmpty(file)) {
            return new Result<>(CoReturnFormat.NOT_FILE);
        }
        Workbook wb = WorkbookFactory.create(file.getInputStream());
        cereShopProductService.importProduct(wb);
        return new Result<>();
    }

    /**
     * 商品规格查询
     */
    @PostMapping(value = "getSkus")
    @Operation(summary = "商品规格查询")
    public Result<List<ToolProduct>> getToolSkus(@RequestBody ToolSkuParam param) {
        List<ToolProduct> list = cereProductSkuService.getToolSkus(param.getProductId());
        return new Result<>(list);
    }

    /**
     * 同步商品列表到scrm
     */
    @PostMapping(value = "syncProductList")
    @Operation(summary = "同步商品列表到scrm")
    public Result<List<SyncShopProduct>> syncProductList(@RequestBody BusinessSyncToScrmParam param) {
        if (param == null || param.getSecret() == null || param.getPageSize() == null) {
            return new Result<>(Collections.emptyList());
        }
        scrmSyncVerifyUtil.verify(param.getShopId(), param.getSecret());
        List<SyncShopProduct> list = cereShopProductService.syncProductList(param.getShopId(), param.getLastId(), param.getPageSize());
        return new Result<>(list);
    }

    /**
     * 导出商品数据
     */
    @PostMapping(value = "exportProduct")
    @NoRepeatSubmit
    @Operation(summary = "导出商品数据")
    public void exportProduct(@RequestBody ProductGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        List<ProductExportDTO> productExportList = cereShopProductService.getExportList(param);
        //定义导出的excel名字
        String excelName = "商品列表";
        ExcelExportUtils.exportExcel(request, response, excelName, productExportList, ProductExportDTO.class);
    }

    /**
     * 查询品牌列表
     */
    @PostMapping(value = "getBrandList")
    @Operation(summary = "查询品牌列表")
    public Result<List<Brand>> getBrandList() {
        List<Brand> list = brandService.getBrandList();
        return new Result<>(list);
    }

    /**
     * 克隆商品
     */
    @PostMapping(value = "cloneProduct")
    @NoRepeatSubmit
    @Operation(summary = "克隆商品")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated ProductUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopProductService.cloneProduct(param, user);
        return new Result<>(user.getUsername(), "克隆商品", GsonUtil.objectToGson(param));
    }

    /**
     * 导出商品数据
     */
    @PostMapping(value = "recommendProduct")
    @NoRepeatSubmit
    @Operation(summary = "推荐商品")
    public Result<String> recommendProduct(@RequestBody RecommendProductParam param,
                                           HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopProductService.recommendProduct(param, user);
        return new Result<>(user.getUsername(), "推荐商品", GsonUtil.objectToGson(param));
    }
}
