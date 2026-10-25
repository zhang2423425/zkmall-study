/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家保证金数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopBond", description = "商家保证金数据")
public class ShopBond {

    /**
     * 报名id
     */
    private Long signId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 保证金类型
     */
    @Schema(description = "保证金类型")
    private String bondType = "营销活动保证金";

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String activityName;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String transactionId;

    /**
     * 保证金金额
     */
    @Schema(description = "保证金金额")
    private BigDecimal bondMoney;

    /**
     * 保证金状态 空-全部 0-待支付 1-冻结中 2-已退回
     */
    @Schema(description = "保证金状态 空-全部 0-待支付 1-冻结中 2-已退回")
    private Integer bondState;

    /**
     * 缴纳时间
     */
    private String paymentTime;

    /**
     * 退保时间
     */
    private String returnTime;

    /**
     * 缴纳/退保时间
     */
    @Schema(description = "缴纳/退保时间")
    private String time;
}
