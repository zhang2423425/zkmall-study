/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.finance;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取流水明细列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "FinanceDetailParam", description = "获取流水明细列表请求")
public class FinanceDetailParam extends PageParam {

    /**
     * 店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 时间
     */
    @Schema(description = "时间")
    private String time;

    /**
     * 收支类型 1-收入 2-支出
     */
    @Schema(description = "收支类型 1-收入 2-支出")
    private Integer income;

    /**
     * 流水类型 传支付、退款、提现
     */
    @Schema(description = "流水类型 传支付、退款、提现")
    private String state;
}
