/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 画布平台端限时折扣数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CanvasPlatformDiscount", description = "画布平台端限时折扣数据")
public class CanvasPlatformDiscount implements Serializable {
    /**
     * 平台限时折扣活动id
     */
    @Schema(description = "平台限时折扣活动id")
    private Long discountId;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String discountName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 报名开始时间
     */
    @Schema(description = "报名开始时间")
    private String signStartTime;

    /**
     * 报名结束时间
     */
    @Schema(description = "报名结束时间")
    private String signEndTime;

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
     * 商品限购 1-不限购 2-限购
     */
    @Schema(description = "商品限购 1-不限购 2-限购")
    private Integer ifLimit;

    /**
     * 限购几件/人
     */
    @Schema(description = "限购几件/人")
    private Integer limitNumber;

    /**
     * 优惠券是否叠加 1-是 0-否
     */
    @Schema(description = "优惠券是否叠加 1-是 0-否")
    private Integer ifAdd;

    /**
     * 活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    @Schema(description = "活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束")
    private Integer state;

    /**
     * 是否需要保证金  1-是 0-否
     */
    @Schema(description = "是否需要保证金  1-是 0-否")
    private Integer ifBond;

    /**
     * 保证金金额
     */
    @Schema(description = "保证金金额")
    private BigDecimal bondMoney;

    /**
     * 折扣
     */
    @Schema(description = "折扣")
    private BigDecimal discount;

    /**
     * 报名商品明细
     */
    @Schema(description = "报名商品明细")
    private List<ToolProduct> products;

    private static final long serialVersionUID = 1L;

}
