/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_product_sku 商品规格信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereProductSku", description = "商品规格信息实体类")
public class CereProductSku implements Serializable {

    /**
     * 商品规格id
     */
    @Schema(description = "商品规格id")
    @TableId(type = IdType.AUTO)
    private Long skuId;

    /**
     * 关联商品id
     */
    @Schema(description = "关联商品id")
    private Long productId;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    @TableField("SKU")
    private String SKU;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stockNumber;

    /**
     * 库存总数
     */
    @Schema(description = "库存总数")
    private Integer total;

    /**
     * 重量
     */
    @Schema(description = "重量")
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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

}
