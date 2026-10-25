/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 售后处理请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterIdParam", description = "售后处理请求")
public class AfterIdParam {

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

    /**
     * 商家处理留言
     */
    @Schema(description = "商家处理留言")
    private String reason;

    /**
     * 快递公司
     */
    @Schema(description = "快递公司")
    private Long express;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String deliverFormid;
}
