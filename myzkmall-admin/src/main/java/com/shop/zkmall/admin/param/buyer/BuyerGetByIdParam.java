/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取客户详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerGetByIdParam", description = "获取客户详情请求")
public class BuyerGetByIdParam {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;
}
