/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.member;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品会员价规格数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductMember", description = "商品会员价规格数据")
public class ProductMember {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private String value;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 优惠方式   1-折扣 2-指定价格
     */
    @Schema(description = "优惠方式   1-折扣 2-指定价格")
    private Integer mode;

    /**
     * 会员价格数据明细
     */
    @Schema(description = "会员价格数据明细")
    private List<MemberPrice> memberPrices;
}
