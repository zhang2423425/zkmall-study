/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 运营计划优惠券明细
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "OperateCoupon", description = "运营计划优惠券明细")
public class OperateCoupon {

    /**
     * 店铺优惠券id
     */
    @Schema(description = "店铺优惠券id")
    private Long shopCouponId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    @Schema(description = "优惠券类型 1-满减券 2-折扣券")
    private Integer couponType;

    /**
     * 优惠内容
     */
    @Schema(description = "优惠内容")
    private String content;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Integer stockNumber;

    /**
     * 用券时间 1-固定时间 2-领券当日起几天内可用
     */
    @Schema(description = "用券时间 1-固定时间 2-领券当日起几天内可用")
    private Integer timeType;

    /**
     * 赠券数量
     */
    @Schema(description = "赠券数量")
    private Integer number;

    /**
     * 使用门槛满多少元，无门槛为0
     */
    @Schema(description = "使用门槛满多少元，无门槛为0")
    private BigDecimal threshold;

    /**
     * 优惠内容减多少元
     */
    @Schema(description = "优惠内容减多少元")
    private BigDecimal couponContent;

    /**
     * 用券开始时间
     */
    @Schema(description = "用券开始时间")
    private String effectiveStart;

    /**
     * 用券结束时间
     */
    @Schema(description = "用券结束时间")
    private String effectiveEnd;

}
