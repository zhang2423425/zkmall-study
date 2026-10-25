/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_cart_attribute 购物车商品属性实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereCartAttribute", description = "购物车商品属性实体")
public class CereCartAttribute implements Serializable {
    /**
     * 关联购物车id
     */
    @Schema(description = "关联购物车id")
    private Long cartId;

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
