/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("订单数量")
public class OrderCountDTO {

    /**
     * 待付款订单数
     */
    @Schema(description = "待付款订单数")
    private Integer waitPayOrderCount;

    /**
     * 待发货订单数
     */
    @Schema(description = "待发货订单数")
    private Integer waitSendOrderCount;

    /**
     * 待收货订单数
     */
    @Schema(description = "待收货订单数")
    private Integer waitReceiveOrderCount;

    /**
     * 总订单数
     */
    @Schema(description = "总订单数")
    private Integer totalOrderCount;

}
