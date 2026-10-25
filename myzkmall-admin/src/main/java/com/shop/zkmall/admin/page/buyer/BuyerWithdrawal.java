/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户提现数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUserDetail", description = "用户提现数据")
public class BuyerWithdrawal {

    /**
     * 提现id
     */
    @Schema(description = "提现id")
    private Long withdrawalId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 提现金额
     */
    @Schema(description = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 状态 0-审核中 1-通过 2-拒绝
     */
    @Schema(description = "状态 0-审核中 1-通过 2-拒绝")
    private Integer state;

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    private String bankCard;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private String applyTime;

    /**
     * 处理时间
     */
    @Schema(description = "处理时间")
    private String handleTime;
}
