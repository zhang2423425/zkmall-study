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

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("首页统计数据")
public class IndexStats {

    @Schema(description = "今日新增用户数")
    private int todayNewUser = 0;
    @Schema(description = "昨日新增用户数")
    private int yesterdayNewUser = 0;
    @Schema(description = "本周和上周新增用户数环比")
    private BigDecimal weekRelativeRatioNewUser = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Schema(description = "今日访问量")
    private int todayVisitCount = 0;
    @Schema(description = "昨日访问量")
    private int yesterdayVisitCount = 0;
    @Schema(description = "本周和上周访问量环比")
    private BigDecimal weekRelativeRatioVisitCount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Schema(description = "今日访客")
    private int todayVisitUser = 0;
    @Schema(description = "昨日访客")
    private int yesterdayVisitUser = 0;
    @Schema(description = "本周和上周访客数环比")
    private BigDecimal weekRelativeRatioVisitUser = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Schema(description = "今日新增店铺")
    private int todayShopCount = 0;
    @Schema(description = "昨日新增店铺")
    private int yesterdayShopCount = 0;
    @Schema(description = "本周和上周新增店铺环比")
    private BigDecimal weekRelativeRatioShopCount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

}
