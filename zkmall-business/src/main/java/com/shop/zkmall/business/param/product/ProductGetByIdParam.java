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
 * 获取商品详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductGetByIdParam", description = "获取商品详情请求")
public class ProductGetByIdParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;
}
