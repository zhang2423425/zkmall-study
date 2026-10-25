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
 * 申请平台介入
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PlatformParam", description = "申请平台介入")
public class PlatformParam {

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
     * 问题描述
     */
    @Schema(description = "问题描述")
    private String reason;

    /**
     * 图片地址（多个以逗号隔开）
     */
    @Schema(description = "图片地址（多个以逗号隔开）")
    private String image;
}
