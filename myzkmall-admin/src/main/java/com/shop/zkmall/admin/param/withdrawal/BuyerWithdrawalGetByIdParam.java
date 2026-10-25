/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.withdrawal;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取用户提现申请详情
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerWithdrawalGetByIdParam", description = "获取用户提现申请详情")
public class BuyerWithdrawalGetByIdParam {

    /**
     * 提现id
     */
    @Schema(description = "提现id")
    private Long withdrawalId;

    /**
     * 处理状态 1-同意 2-拒绝
     */
    @Schema(description = "处理状态 1-同意 2-拒绝")
    private Integer state;
}
