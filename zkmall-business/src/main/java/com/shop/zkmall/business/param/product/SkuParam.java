/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品规格明细参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SkuParam", description = "商品规格明细参数")
public class SkuParam {

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 规格名和值数组
     */
    @Schema(description = "规格名数组")
    private List<SkuNameValueParam> skuAttrCodeDTOList;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String sku;

    /**
     * 售价
     */
    @Schema(description = "售价")
    @NotNull(message = "售价不能为空")
    private BigDecimal price;

    /**
     * 原价
     */
    @Schema(description = "原价")
    @NotNull(message = "原价不能为空")
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    @NotNull(message = "库存不能为空")
    private Integer stockNumber;

    /**
     * 重量
     */
    @Schema(description = "重量")
    @NotNull(message = "重量不能为空")
    private BigDecimal weight;

    /**
     * 规格图片地址
     */
    @Schema(description = "规格图片地址")
    private String skuImage;

    /**
     * 款式  0-单款式 1-多款式
     */
    @Schema(description = "款式  0-单款式 1-多款式")
    private Integer style;

    /**
     * 导入数据
     */
    private NameValue nameValue;
}
