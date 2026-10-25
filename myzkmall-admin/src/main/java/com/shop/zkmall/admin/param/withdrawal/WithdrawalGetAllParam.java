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
 * 获取提现申请列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "WithdrawalGetAllParam", description = "获取提现申请列表请求")
public class WithdrawalGetAllParam extends PageParam {

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
     * 提现日期
     */
    @Schema(description = "提现日期")
    private String startTime;

    /**
     * 提现状态 1-已处理 0-未处理
     */
    @Schema(description = "提现状态 1-已处理 0-未处理")
    private Integer state;
}
