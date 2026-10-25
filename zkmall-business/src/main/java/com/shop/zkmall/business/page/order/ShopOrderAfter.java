/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopOrderAfter", description = "售后返回数据实体类")
public class ShopOrderAfter {


    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 售后ID
     */
    @Schema(description = "售后ID")
    private Long afterId;
    /**
     * 售后状态
     */
    @Schema(description = "售后状态")
    private Integer afterState;
}
