/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.settlement;

import com.shop.zkmall.app.page.cart.CartSku;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 结算返回店铺数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SettlementShop", description = "结算返回店铺数据")
public class SettlementShop {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String shopLogo;

    /**
     * 商品数据
     */
    @Schema(description = "商品数据")
    private List<CartSku> skus;

    /**
     * 配送方式信息
     */
    @Schema(description = "配送方式信息")
    private Distribution distribution;

    /**
     * 商品总件数
     */
    @Schema(description = "商品总件数")
    private Integer number;

    /**
     * 店铺总计
     */
    @Schema(description = "店铺总计")
    private BigDecimal total;

    /**
     * sku的优惠信息
     */
    @Schema(description = "sku的优惠信息")
    private Map<Long, List<String>> skuDiscountInfoMap;

    /**
     * 定价捆绑sku优惠后的金额
     */
    @Schema(description = "定价捆绑sku优惠后的金额")
    private BigDecimal priceAfterDiscount;

    /**
     * 收货地址与商家配送区域不匹配
     */
    @Schema(description = "收货地址与商家配送区域不匹配")
    private boolean receiveNotMatch;

    @Schema(description = "商品种类")
    private Integer productClassifyCount;

    @Schema(description = "店铺简介")
    private String shopBrief;

    @Schema(description = "店铺评价分数")
    private BigDecimal shopStar;
}
