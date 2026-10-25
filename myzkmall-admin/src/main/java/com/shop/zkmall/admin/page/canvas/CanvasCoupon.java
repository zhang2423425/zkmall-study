/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 画布平台活动数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CanvasCoupon", description = "画布平台活动数据")
public class CanvasCoupon {

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    private Long activityId;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    private Long couponId;

    /**
     * 优惠券类型
     */
    @Schema(description = "优惠券类型 1-满减 2-折扣")
    private Integer couponType;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String activityName;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    private String activityStartTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    private String activityEndTime;

    /**
     * 满多少元
     */
    @Schema(description = "满多少元")
    private BigDecimal fullMoney;

    /**
     * 减多少元
     */
    @Schema(description = "减多少元")
    private BigDecimal reduceMoney;
}
