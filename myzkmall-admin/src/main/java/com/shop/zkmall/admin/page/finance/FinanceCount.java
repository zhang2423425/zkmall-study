/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.finance;

import com.shop.zkmall.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 财务统计数据返回实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "FinanceCount", description = "财务统计数据返回实体类")
public class FinanceCount {

    /**
     * 营收总额
     */
    @Schema(description = "营收总额")
    private BigDecimal revenue;

    /**
     * 冻结中金额
     */
    @Schema(description = "冻结中金额")
    private BigDecimal frozen;

    /**
     * 可提现金额
     */
    @Schema(description = "可提现金额")
    private BigDecimal withdrawable;

    /**
     * 已提现金额
     */
    @Schema(description = "已提现金额")
    private BigDecimal haveWithdrawable;

    /**
     * 提现中的金额
     */
    @Schema(description = "提现中的金额")
    private BigDecimal withdrawableStayMoney;

    /**
     * 已退款金额
     */
    @Schema(description = "已退款金额")
    private BigDecimal refund;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private Page<Finance> page;
}
