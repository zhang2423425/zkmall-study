/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("首页统计图表")
public class IndexCharts {

    @Schema(description = "今日订单金额")
    private BigDecimal todayOrderAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    ;

    @Schema(description = "过去一个月订单金额列表")
    private List<StatsAmountByDay> orderAmountList;

    @Schema(description = "日订单数")
    private Integer todayOrderCount = 0;

    @Schema(description = "订单日同比")
    private BigDecimal dayToDayOrderCountRelativeRate = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Schema(description = "过去一个月订单量")
    private List<StatsByDay> orderCountList;

    @Schema(description = "月订单数")
    private Integer curMonthOrderCount = 0;

    @Schema(description = "订单月同比")
    private BigDecimal monthToMonthOrderCountRelativeRate = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);


    @Schema(description = "当日支付人数")
    private Integer todayPayUserCount = 0;

    @Schema(description = "支付人数同比")
    private BigDecimal dayToDayPayUserCountRelativeRate = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Schema(description = "过去一个月支付人数")
    private List<StatsByDay> payUserCountList;

    @Schema(description = "月支付人数")
    private Integer curMonthPayUserCount = 0;

    @Schema(description = "支付人数月同比")
    private BigDecimal monthToMonthPayUserCountRelativeRate = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
}
