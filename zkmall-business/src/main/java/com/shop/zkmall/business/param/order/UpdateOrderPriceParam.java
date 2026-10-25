/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单改价请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UpdateOrderPriceParam", description = "订单改价请求")
public class UpdateOrderPriceParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 最新支付金额
     */
    @Schema(description = "最新支付金额")
    private BigDecimal price;

}
