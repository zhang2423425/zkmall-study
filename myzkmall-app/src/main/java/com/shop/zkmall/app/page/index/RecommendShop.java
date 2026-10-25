/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 首页推荐店铺数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RecommendShop", description = "首页推荐店铺数据")
public class RecommendShop {

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
     * 销量最高商品id
     */
    @Schema(description = "销量最高商品id")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 销量最高商品名称
     */
    @Schema(description = "销量最高商品名称")
    private String productName;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 付款人数
     */
    @Schema(description = "付款人数")
    private Integer users;

    /**
     * 活动限制总数
     */
    @Schema(description = "活动限制总数")
    private Integer total;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @Schema(description = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;
}
