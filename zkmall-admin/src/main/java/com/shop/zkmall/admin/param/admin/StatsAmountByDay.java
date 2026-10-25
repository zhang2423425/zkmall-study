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

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("金额统计")
public class StatsAmountByDay {

    @Schema(description = "统计日期")
    private String statsDate;

    @Schema(description = "金额")
    private BigDecimal amount;

}
