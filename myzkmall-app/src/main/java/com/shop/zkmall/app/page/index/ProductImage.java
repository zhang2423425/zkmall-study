/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 产品图片
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductImage", description = "产品图片")
public class ProductImage {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

}
