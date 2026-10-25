/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.order;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取订单列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OrderGetAllParam", description = "获取订单列表请求")
public class OrderGetByIdParam extends PageParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 消息id
     */
    @Schema(description = "消息id")
    private Long noticeId;

    /**
     * 订单类型 1-父订单 2-子订单
     */
    @Schema(description = "订单类型 1-父订单 2-子订单")
    private Integer type = 2;

    /**
     * 订单支付方式 1-微信 2-支付宝
     */
    @Schema(description = "订单支付方式")
    private Integer paymentMode;

    /**
     * 支付子类型 微信支付，还对应小程序支付 app支付 H5支付等
     * 1-小程序支付 2-app支付 3-H5支付 4-pc二维码
     */
    private Integer subPaymentMode;
}
