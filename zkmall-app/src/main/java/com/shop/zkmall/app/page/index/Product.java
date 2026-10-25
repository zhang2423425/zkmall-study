/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.index;

import com.shop.zkmall.commons.domain.product.DiscountProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 搜索商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "Product", description = "搜索商品数据")
public class Product extends DiscountProduct {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺图标
     */
    @Schema(description = "店铺图标")
    private String shopLogo;

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
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 商品售价
     */
    @Schema(description = "商品售价")
    private BigDecimal price;

    /**
     * 付款人数
     */
    @Schema(description = "付款人数")
    private Integer users;

    /**
     * 商品简介
     */
    @Schema(description = "商品简介")
    private String productBrief;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @Schema(description = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;

    /**
     * 已售件数
     */
    @Schema(description = "已售件数")
    private Integer number;

    /**
     * 活动限制总数
     */
    @Schema(description = "活动限制总数")
    private Integer total;

    @Schema(description = "活动描述 例如满1000减100 5.8折优惠")
    private String discountText;

    // 详情地址
    @Schema(description = "详情地址")
    private String detailUrl;
}
