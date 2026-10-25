/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取订单详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "OrderGetByIdParam", description = "获取订单详情请求")
public class OrderGetByIdParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;
}
