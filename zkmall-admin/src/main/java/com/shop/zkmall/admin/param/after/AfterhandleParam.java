/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 售后处理请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterhandleParam", description = "售后处理请求")
public class AfterhandleParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 售后单id
     */
    @Schema(description = "售后单id")
    private Long afterId;

    /**
     * 处理状态 1-同意 2-拒绝
     */
    @Schema(description = "处理状态 1-同意 2-拒绝")
    private Integer state;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
