/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 加入购物车请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopCartParam", description = "加入购物车请求")
public class ShopCartParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer number;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String SKU;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal weight;
}
