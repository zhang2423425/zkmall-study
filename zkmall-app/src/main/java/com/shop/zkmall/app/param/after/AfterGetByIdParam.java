/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 售后详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterGetByIdParam", description = "售后详情请求")
public class AfterGetByIdParam {

    /**
     * 售后单id
     */
    @Schema(description = "售后单id")
    private Long afterId;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

}
