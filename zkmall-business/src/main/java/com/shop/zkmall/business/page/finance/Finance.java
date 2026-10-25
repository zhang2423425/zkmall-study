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
 * 财务返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Finance", description = "财务返回数据实体类")
public class Finance {

    /**
     * 日期
     */
    @Schema(description = "日期")
    private String time;

    /**
     * 收入
     */
    @Schema(description = "收入")
    private BigDecimal income;

    /**
     * 支出
     */
    @Schema(description = "支出")
    private BigDecimal expenditure;
}
