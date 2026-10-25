/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 商品规格名值数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "NameValue", description = "商品规格名值数据")
public class NameValue {

    /**
     * 规格名
     */
    @NotBlank(message = "规格名不能为空")
    @Schema(description = "规格名")
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
     * 是否需要配图
     */
    private Integer need;
}
