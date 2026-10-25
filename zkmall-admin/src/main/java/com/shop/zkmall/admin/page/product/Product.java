/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Product", description = "订单商品返回数据实体类")
public class Product {

    /**
     * 售后商品明细id
     */
    @Schema(description = "售后商品明细id")
    private Long afterProductId;

    /**
     * 订单商品明细id
     */
    @Schema(description = "订单商品明细id")
    private Long orderProductId;

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
     * 规格名称
     */
    @Schema(description = "规格名称")
    private String skuName;

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private String skuValue;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String SKU;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer number;

    /**
     * 商品总价
     */
    @Schema(description = "商品总价")
    private BigDecimal total;

    /**
     * 最低价格
     */
    @Schema(description = "最低价格")
    private BigDecimal minMoney;

    /**
     * 最高价格
     */
    @Schema(description = "最高价格")
    private BigDecimal maxMoney;

    /**
     * 规格属性值
     */
    @Schema(description = "规格属性值")
    private String value;

    /**
     * 规格属性明细数据
     */
    @Schema(description = "规格属性明细数据")
    private List<SkuDetail> skuDetails;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stockNumber;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;
}
