/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.after;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class AfterRefund {

    /**
     * 订单支付流水id
     */
    private Long id;

    /**
     * 退款金额
     */
    private BigDecimal price;

    /**
     * 订单总金额
     */
    private BigDecimal orderPrice;

    /**
     * 支付单号
     */
    private String transactionId;

    /**
     * 退款单号
     */
    private String outRefundNo;

    /**
     * 售后单id字符串
     */
    private String after;

    /**
     * 订单编号
     */
    private String orderFormid;

    /**
     * 订单支付编号
     */
    private String outTradeNo;
}
