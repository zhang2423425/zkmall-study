/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.collect;

import com.shop.zkmall.commons.domain.product.DiscountProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 收藏商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CollectProduct", description = "收藏商品数据")
public class CollectProduct extends DiscountProduct {

    /**
     * 收藏id
     */
    @Schema(description = "收藏id")
    private Long collectId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

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
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品原价
     */
    @Schema(description = "商品售价")
    private BigDecimal originalPrice;

    /**
     * 商品售价
     */
    @Schema(description = "商品售价")
    private BigDecimal price;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @Schema(description = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 是否选中 1-是 0-否
     */
    @Schema(description = "是否选中 1-是 0-否")
    private Integer selected;

    /**
     * 活动类型
     */
    @Schema(description = "活动类型")
    private Integer activityType;

    @Schema(description = "已售件数")
    private Integer number;

    @Schema(description = "付款人数")
    private Integer users;

    @Schema(description = "活动描述 例如满1000减100 5.8折优惠")
    private String discountText;
}
