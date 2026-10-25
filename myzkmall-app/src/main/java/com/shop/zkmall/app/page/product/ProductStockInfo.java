/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class ProductStockInfo {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 库存总数
     */
    @Schema(description = "库存总数")
    private Integer total;

    /**
     * 当前剩余库存数
     */
    @Schema(description = "当前剩余库存数")
    private Integer stockNumber;
}
