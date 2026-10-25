/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.withdrawal;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取用户提现申请列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "BuyerWithdrawalGetAllParam", description = "获取用户提现申请列表请求")
public class BuyerWithdrawalGetAllParam extends PageParam {

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String name;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 提现状态 空-全部 0-待审核 1-通过 2-拒绝
     */
    @Schema(description = "提现状态 空-全部 0-待审核 1-通过 2-拒绝")
    private Integer state;
}
