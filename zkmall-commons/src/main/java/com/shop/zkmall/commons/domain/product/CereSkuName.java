/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_sku 商品规格属性信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereSkuName", description = "商品规格属性信息实体类")
public class CereSkuName implements Serializable {

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 是否需要配图 1-是 0-否
     */
    @Schema(description = "是否需要配图 1-是 0-否")
    private Integer need;

    /**
     * 规格名
     */
    @Schema(description = "规格名")
    private String skuName;

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private String skuValue;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String image;

    /**
     * 规格名级别
     */
    @Schema(description = "规格名级别")
    private String nameCode;

    /**
     * 规格值级别
     */
    @Schema(description = "规格值级别")
    private String valueCode;

}
