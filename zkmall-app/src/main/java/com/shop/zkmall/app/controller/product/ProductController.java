/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.product;

import com.alibaba.fastjson.JSON;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.product.ProductDetail;
import com.shop.zkmall.app.page.product.ProductSkus;
import com.shop.zkmall.app.page.product.ShareProduct;
import com.shop.zkmall.app.param.product.*;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.product.CereSkuMemberRealInfoService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.HttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 商品模块
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

    private final CereBuyerUserService cereBuyerUserService;

    private final CereSkuMemberRealInfoService cereSkuMemberRealInfoService;

    private final UserRedisService userRedisService;

    @Value("${verifyCode}")
    private String verifyCode;

    /**
     * 商品详情查询
     */
    @GetMapping("getById")
    @Operation(summary = "商品详情查询")
    public Result<ProductDetail> getById(ProductParam param, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        ProductDetail detail = cereShopProductService.getById(param, user);
        return new Result<>(detail, CoReturnFormat.SUCCESS);
    }

    /**
     * 删除搜索记录
     */
    @RequestMapping(value = "deleteAllSearch", method = {RequestMethod.DELETE, RequestMethod.POST})
    @Operation(summary = "删除搜索记录")
    @WebRequestLog
    public Result<String> deleteAllSearch(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        String name = "";
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
            name = user.getWechatName();
        }
        cereShopProductService.deleteAllSearch(user);

        return new Result<>(CoReturnFormat.SUCCESS, name, "删除搜索记录", "");
    }

    /**
     * 选择商品查询
     */
    @GetMapping("getProducts")
    @Operation(summary = "选择商品查询")
    public Result<ProductSkus> getProducts(SkuParam param) {
        ProductSkus products = cereShopProductService.getProducts(param);
        return new Result<>(products, CoReturnFormat.SUCCESS);
    }

    /**
     * 选择指定sku数据
     */
    @GetMapping("getSpecificSku")
    @Operation(summary = "选择指定sku数据")
    public Result<ProductSkus> getSpecificSku(SkuParam param) {
        ProductSkus products = cereShopProductService.getSpecificSku(param);
        return new Result<>(products, CoReturnFormat.SUCCESS);
    }

    /**
     * 获取分享图片
     */
    @GetMapping("getSharePic")
    @Operation(summary = "获取分享图片")
    public void getSharePic(ProductParam param, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        try {
            if (!EmptyUtils.isEmpty(token)) {
                //根据token查询用户信息
                user = cereBuyerUserService.findByToken(token);
            }
            HttpUtils.write(cereShopProductService.getSharePic(param, user), response);
        } catch (Exception exception) {
            log.error("生成商品分享图错误:", exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取分享商品所需的参数
     */
    @Deprecated
    @GetMapping("getShareProductParam")
    @Operation(summary = "获取分享商品所需的参数")
    public Result<ShareProduct> getShareProductParam(ProductParam param, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        ShareProduct shareProduct = cereShopProductService.getShareProductParam(param, user);
        Result<ShareProduct> result = new Result<>();
        result.setCode(CoReturnFormat.SUCCESS);
        result.setData(shareProduct);
        return result;
    }

    /**
     * 查询随机排序的商品
     */
    @GetMapping("getRandomSortProduct")
    @Operation(summary = "查询随机排序的商品")
    public Result<Page<Product>> getRandomSortProduct(PageProductParam param, HttpServletRequest request) {
        //获取当前登录账户
        Long buyerUserId = null;
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            buyerUserId = userRedisService.getBuyerUserIdByToken(token);
        }
        Page<Product> page = cereShopProductService.getRandomSortProduct(param, buyerUserId);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    @GetMapping("refreshAllProductRealInfo")
    @Operation(summary = "刷新所有商品的实时活动信息")
    public Result<Boolean> refreshAllProductRealInfo(VerifyCodeParam param) {
        if (StringUtils.isBlank(verifyCode) || param.getVerifyCode() == null || !Objects.equals(param.getVerifyCode(), verifyCode)) {
            return new Result<>(false);
        }
        boolean result = cereSkuMemberRealInfoService.refreshAllProductRealInfo();
        return new Result<>(result, CoReturnFormat.SUCCESS);
    }

    @PostMapping("refreshSkuRealInfo")
    @Operation(summary = "刷新sku的实时信息")
    public Result<Boolean> refreshSkuRealInfo(@RequestBody RefreshRealInfoDTO refreshRealInfoDTO) {
        log.info("product refreshSkuRealInfo {}", JSON.toJSONString(refreshRealInfoDTO));
        if (StringUtils.isBlank(verifyCode) || refreshRealInfoDTO.getVerifyCode() == null || !Objects.equals(refreshRealInfoDTO.getVerifyCode(), verifyCode)) {
            return new Result<>(false);
        }
        List<RefreshSkuRealInfoSourceEnum> sceneEnumList = Arrays.asList(
                RefreshSkuRealInfoSourceEnum.SECKILL_START, RefreshSkuRealInfoSourceEnum.SECKILL_END,
                RefreshSkuRealInfoSourceEnum.DISCOUNT_START, RefreshSkuRealInfoSourceEnum.DISCOUNT_END,
                RefreshSkuRealInfoSourceEnum.GROUP_PRE_HOT, RefreshSkuRealInfoSourceEnum.GROUP_START,
                RefreshSkuRealInfoSourceEnum.GROUP_END,
                RefreshSkuRealInfoSourceEnum.SHOP_SECKILL_PRE_HOT, RefreshSkuRealInfoSourceEnum.SHOP_SECKILL_START,
                RefreshSkuRealInfoSourceEnum.SHOP_SECKILL_END, RefreshSkuRealInfoSourceEnum.SHOP_DISCOUNT_PRE_HOT,
                RefreshSkuRealInfoSourceEnum.SHOP_DISCOUNT_START, RefreshSkuRealInfoSourceEnum.SHOP_DISCOUNT_END,
                RefreshSkuRealInfoSourceEnum.SCENE_START, RefreshSkuRealInfoSourceEnum.SCENE_END);
        if (sceneEnumList.contains(refreshRealInfoDTO.getSourceEnum())) {
            cereSkuMemberRealInfoService.refreshSkuRealInfoForActivity(refreshRealInfoDTO);
        } else {
            cereSkuMemberRealInfoService.refreshSkuRealInfo(refreshRealInfoDTO);
        }
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

    @GetMapping("getBroadCastList")
    @Operation(summary = "查询循环播报列表")
    public Result<List<BroadCastDTO>> getBroadCastList(BroadCastParam param, HttpServletRequest request) {
        //获取当前登录账户
        Long buyerUserId = null;
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            buyerUserId = userRedisService.getBuyerUserIdByToken(token);
        }
        //return new Result<>(Collections.emptyList(), CoReturnFormat.SUCCESS);
        return new Result<>(cereShopProductService.getBroadCastList(param.getProductId(), param.getShopGroupWorkId(), buyerUserId), CoReturnFormat.SUCCESS);
    }

}
