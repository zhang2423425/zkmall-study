/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.buyer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_buyer_withdrawal 客户提现信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereBuyerWithdrawal", description = "客户提现信息实体")
public class CereBuyerWithdrawal implements Serializable {
    /**
     * 提现申请id
     */
    @Schema(description = "提现申请id")
    @TableId(type = IdType.AUTO)
    private Long withdrawalId;

    /**
     * 客户id
     */
    @Schema(description = "分销员id")
    private Long buyerUserId;

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
     * 提现金额
     */
    @Schema(description = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 处理状态  0-审核中 1-通过 2-拒绝
     */
    @Schema(description = "处理状态  0-审核中 1-通过 2-拒绝")
    private Integer state;

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
