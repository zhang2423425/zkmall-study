/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.domain.product.SkuName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品所有规格属性数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductSkus", description = "商品所有规格属性数据")
public class ProductSkus {

    /**
     * 规格属性名数组
     */
    @Schema(description = "规格属性名数组")
    private List<SkuName> names;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Integer stockNumber;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal weight;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String SKU;

    /**
     * 所有规格属性组合数据
     */
    @Schema(description = "所有规格属性组合数据")
    private Map<String, Sku> map;

    /**
     * 是否需要物流
     */
    @Schema(description = "是否需要物流")
    private Integer ifLogistics;

    /**
     * 是否支持花呗分期
     */
    @Schema(description = "是否支持花呗分期 1-是 0-否")
    private Integer ifHuabei;

}
