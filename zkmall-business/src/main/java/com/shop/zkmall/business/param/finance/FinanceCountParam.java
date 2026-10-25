/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取财务统计数据请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "FinanceCountParam", description = "获取财务统计数据请求")
public class FinanceCountParam {

    /**
     * 店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 时间条件 1-日汇总 2-月汇总
     */
    @Schema(description = "时间条件 1-日汇总 2-月汇总")
    private Integer condition;

    /**
     * 时间
     */
    @Schema(description = "时间")
    private String time;
}
