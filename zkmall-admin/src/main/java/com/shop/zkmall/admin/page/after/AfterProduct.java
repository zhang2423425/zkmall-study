/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 售后商品明细返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterProduct", description = "售后商品明细返回数据实体类")
public class AfterProduct {

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
     * sku
     */
    @Schema(description = "sku")
    private String SKU;

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
}
