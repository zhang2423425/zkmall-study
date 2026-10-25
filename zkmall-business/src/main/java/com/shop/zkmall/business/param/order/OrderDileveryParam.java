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
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "OrderDileveryParam", description = "发货请求")
public class OrderDileveryParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 快递公司（取数据字典）
     */
    @Schema(description = "快递公司（取数据字典）")
    private Long express;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String deliverFormid;
}
