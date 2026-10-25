/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 规格属性名数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SkuName", description = "规格属性名数据")
public class SkuName implements Serializable {

    @Serial
    private static final long serialVersionUID = 7834859279609115324L;
    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 规格名
     */
    @Schema(description = "规格名")
    private String skuName;

    /**
     * 规格名级别
     */
    @Schema(description = "规格名级别")
    private String nameCode;

    /**
     * 规格值属性数组
     */
    @Schema(description = "规格值属性数组")
    private List<SkuValue> values;
}
