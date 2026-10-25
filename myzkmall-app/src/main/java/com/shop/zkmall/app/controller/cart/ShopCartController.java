/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.cart;

import com.shop.zkmall.app.page.cart.ShopCart;
import com.shop.zkmall.app.param.cart.*;
import com.shop.zkmall.app.service.cart.CereShopCartService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@Tag(name = "购物车模块", description = "购物车模块")
public class ShopCartController {

    private final CereShopCartService cereShopCartService;

    /**
     * 加入购物车
     */
    @PostMapping("addCart")
    @NoRepeatSubmit
    @Operation(summary = "加入购物车")
    @WebRequestLog
    public Result<String> addCart(@RequestBody AddCartParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.addCart(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "加入购物车", GsonUtil.objectToGson(param));
    }

    /**
     * 更换规格
     */
    @RequestMapping(value = "updateSku", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "更换规格")
    @WebRequestLog
    public Result<String> updateSku(@RequestBody UpdateSkuParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.updateSku(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "更换规格", GsonUtil.objectToGson(param));
    }

    /**
     * 批量加入购物车
     */
    @PostMapping("addBatchCart")
    @NoRepeatSubmit
    @Operation(summary = "批量加入购物车")
    @WebRequestLog
    public Result<String> addBatchCart(@RequestBody BatchCartParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.addBatchCart(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "批量加入购物车", GsonUtil.objectToGson(param));
    }

    /**
     * 再次购买
     */
    @RequestMapping(value = "buyAgain", method = {RequestMethod.PUT, RequestMethod.POST})
    @Operation(summary = "再次购买")
    @WebRequestLog
    public Result<String> buyAgain(@RequestBody BuyerAgainParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.buyAgain(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "再次购买", GsonUtil.objectToGson(param));
    }

    /**
     * 修改购物车商品数量
     */
    @RequestMapping(value = "updateNumber", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "修改购物车商品数量")
    @WebRequestLog
    public Result<String> updateNumber(@RequestBody CartUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.updateNumber(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "修改购物车商品数量", GsonUtil.objectToGson(param));
    }

    /**
     * 删除购物车商品
     */
    @RequestMapping(value = "delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "删除购物车商品")
    @WebRequestLog
    public Result<String> delete(@RequestBody DeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.delete(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "删除购物车商品", GsonUtil.objectToGson(param));
    }

    /**
     * 清空购物车
     */
    @RequestMapping(value = "deleteAll", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "清空购物车")
    @WebRequestLog
    public Result<String> deleteAll(HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.deleteAll(user.getBuyerUserId());
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "清空购物车", "");
    }

    /**
     * 选中商品
     */
    @RequestMapping(value = "selected", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "选中商品")
    @WebRequestLog
    public Result<String> selected(@RequestBody SelectedParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopCartService.selected(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "选中商品", GsonUtil.objectToGson(param));
    }

    /**
     * 购物车查询
     */
    @GetMapping("getCart")
    @Operation(summary = "购物车查询")
    public Result<List<ShopCart>> getCart(HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<ShopCart> list = cereShopCartService.getCart(user);
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 清空失效购物车商品
     */
    @GetMapping("clearInvalidSku")
    @Operation(summary = "清空失效购物车商品")
    public Result<Boolean> clearInvalidSku(HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Boolean result = cereShopCartService.clearInvalidSku(user.getBuyerUserId());
        return new Result<>(result, CoReturnFormat.SUCCESS);
    }
}
