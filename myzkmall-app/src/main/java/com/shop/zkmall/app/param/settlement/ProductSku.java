/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.settlement;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结算查询商品规格参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductSku", description = "结算查询商品规格参数")
public class ProductSku implements Serializable {

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer number;

    /**
     * 是否需要物流 1-是 0-否
     */
    @Schema(description = "是否需要物流 1-是 0-否")
    private Integer ifLogistics;

    /**
     * 是否购物车选中 1-是 0-否
     */
    @Schema(description = "是否购物车选中 1-是 0-否")
    private Integer selected;

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
     * 平台秒杀活动id
     */
    @Schema(description = "平台秒杀活动id")
    private Long platformSeckillId;

    /**
     * 平台限时折扣活动id
     */
    @Schema(description = "平台限时折扣活动id")
    private Long platformDiscountId;

    /**
     * 是否使用会员价
     */
    @Schema(description = "是否使用会员价")
    private boolean useMember;

    /**
     * 定价捆绑id
     */
    @Schema(description = "定价捆绑id")
    private Long priceId;

    /**
     * 组合捆绑id
     */
    @Schema(description = "组合捆绑id")
    private Long composeId;

    /**
     * 场景营销id
     */
    @Schema(description = "场景营销id")
    private Long sceneId;

    /**
     * 当前sku抵扣积分
     */
    @Schema(description = "当前sku抵扣积分")
    private Integer useCredit;

    /**
     * 当前sku积分抵扣的金额
     */
    @Schema(description = "当前sku积分抵扣的金额")
    private BigDecimal useCreditAmount;

    /**
     * 平台优惠券id 有值则代表该sku享受了平台优惠券
     */
    @Schema(description = "平台优惠券id")
    private Long buyerCouponId;

    /**
     * 商家优惠券id 有值就代表该sku享受了商家优惠券
     */
    @Schema(description = "商家优惠券id")
    private Long buyerShopCouponId;
}
