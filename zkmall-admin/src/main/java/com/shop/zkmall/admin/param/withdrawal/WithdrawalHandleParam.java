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
 * 处理提现申请请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "WithdrawalHandleParam", description = "处理提现申请请求")
public class WithdrawalHandleParam {

    /**
     * 提现申请id
     */
    @Schema(description = "提现申请id")
    private Long withdrawalId;
}
