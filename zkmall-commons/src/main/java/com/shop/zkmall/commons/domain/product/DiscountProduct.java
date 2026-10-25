/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 折扣相关的商品信息
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class DiscountProduct {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "活动描述 例如满1000减100 5.8折优惠")
    private String discountText;

    @Schema(description = "商品售价")
    private BigDecimal price;

}
