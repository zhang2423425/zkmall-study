/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.canvas;

import com.shop.zkmall.commons.domain.product.DiscountProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 画布商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CanvasProduct", description = "画布商品数据")
public class CanvasProduct extends DiscountProduct {

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
     * 店铺图片
     */
    @Schema(description = "店铺图片")
    private String shopLogo;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    private String productName;

    /**
     * 产品主图
     */
    @Schema(description = "产品主图")
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
     * 库存
     */
    @Schema(description = "库存")
    private Integer stockNumber;

    /**
     * 销量
     */
    @Schema(description = "销量")
    private Integer number;

    /**
     * 活动类型
     */
    @Schema(description = "活动类型")
    private Integer activityType;

    /**
     * 付款人数
     */
    @Schema(description = "付款人数")
    private Integer users;

    /**
     * 店铺拼团活动id
     */
    @Schema(description = "店铺拼团活动id")
    private Long shopGroupWorkId;

    /**
     * 秒杀活动id
     */
    @Schema(description = "秒杀活动id")
    private Long shopSeckillId;

    /**
     * 限时折扣活动id
     */
    @Schema(description = "限时折扣活动id")
    private Long shopDiscountId;

    /**
     * 是否开启预热状态 1-是 0-否
     */
    @Schema(description = "是否开启预热状态 1-是 0-否")
    private Integer ifEnable;

    /**
     * 预热时间
     */
    @Schema(description = "预热时间")
    private Integer enableTime;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    private String endTime;

    /**
     * 活动状态 0-未开始 1-进行中 2-已结束
     */
    @Schema(description = "活动状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 活动倒计时
     */
    @Schema(description = "活动倒计时")
    private long time;

    @Schema(description = "活动描述 例如满1000减100 5.8折优惠")
    private String discountText;
}
