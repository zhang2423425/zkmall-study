/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 流水明细返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "FlowingWater", description = "流水明细返回数据实体类")
public class FlowingWater {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 流水类型
     */
    @Schema(description = "流水类型")
    private String waterType;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 收支类型
     */
    @Schema(description = "收支类型")
    private String incomeType;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal money;

    /**
     * 余额
     */
    @Schema(description = "余额")
    private BigDecimal balance;

    /**
     * 时间
     */
    @Schema(description = "时间")
    private String time;
}
