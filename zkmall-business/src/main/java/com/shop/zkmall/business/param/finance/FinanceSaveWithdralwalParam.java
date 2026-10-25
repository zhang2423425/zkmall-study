/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 添加提现申请请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "FinanceSaveWithdralwalParam", description = "添加提现申请请求")
public class FinanceSaveWithdralwalParam {

    /**
     * 店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @Schema(description = "店铺编码")
    private String shopCode;

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
     * 收款人姓名
     */
    @Schema(description = "收款人姓名")
    private String collectionName;

    /**
     * 提现金额
     */
    @Schema(description = "提现金额")
    private BigDecimal withdrawalMoney;
}
