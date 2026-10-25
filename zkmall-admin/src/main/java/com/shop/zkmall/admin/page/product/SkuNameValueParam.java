/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品规格名数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SkuNameValueParam", description = "商品规格名和规格值数据")
public class SkuNameValueParam {

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
