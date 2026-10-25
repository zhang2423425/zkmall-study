/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 删除规格数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DeleteSkuParam", description = "删除规格数据")
public class DeleteSkuParam {

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 规格名级别
     */
    @Schema(description = "规格名级别")
    private String code;

    /**
     * 规格值级别
     */
    @Schema(description = "规格值级别")
    private String valueCode;
}
