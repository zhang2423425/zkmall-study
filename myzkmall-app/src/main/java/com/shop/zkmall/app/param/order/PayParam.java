/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PayParam", description = "支付请求")
public class PayParam {

    /**
     * 拼单id
     */
    @Schema(description = "拼单id")
    private Long collageId;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 订单类型 1-父订单 2-子订单
     */
    @Schema(description = "订单类型 1-父订单 2-子订单")
    private Integer type;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal money;

    /**
     * 订单支付方式 1-微信 2-支付宝
     */
    @Schema(description = "订单支付方式 1-微信 2-支付宝")
    private Integer paymentMode;

    /**
     * 花呗分期数
     */
    @Schema(description = "花呗分期数")
    private Integer huabeiPeriod;
}
