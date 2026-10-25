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
import java.util.List;

/**
 * 财务统计返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "FinanceCount", description = "财务统计返回数据实体类")
public class FinanceCount {

    /**
     * 累计营业额
     */
    @Schema(description = "累计营业额")
    private BigDecimal turnover;

    /**
     * 冻结金额
     */
    @Schema(description = "冻结金额")
    private BigDecimal frozenMoney;

    /**
     * 可提现金额
     */
    @Schema(description = "可提现金额")
    private BigDecimal withdrawableMoney;

    /**
     * 提现中金额
     */
    @Schema(description = "提现中金额")
    private BigDecimal withdrawableStayMoney;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private List<Finance> finances;
}
