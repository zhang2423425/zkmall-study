/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 售后返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "After", description = "售后返回数据实体类")
public class After {

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @Schema(description = "店铺编码")
    private String shopCode;

    /**
     * 售后单号
     */
    @Schema(description = "售后单号")
    private String afterFormid;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型  1-仅退款  2-退货退款")
    private String afterType;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private Integer orderState;

    /**
     * 售后商品数量
     */
    @Schema(description = "售后商品数量")
    private Integer number;

    /**
     * 售后商品名称
     */
    @Schema(description = "售后商品名称")
    private String afterProductNames;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal refundMoney;

    /**
     * 下单账户
     */
    @Schema(description = "买家账户")
    private String customerName;

    /**
     * 物流公司
     */
    @Schema(description = "物流公司")
    private String express;

    /**
     * 物流单号
     */
    @Schema(description = "物流单号")
    private String deliverFormid;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private String paymentTime;

    /**
     * 申请平台介入时间
     */
    @Schema(description = "申请平台介入时间")
    private String platformAfterTime;

    /**
     * 售后单id
     */
    @Schema(description = "售后单id")
    private Long afterId;
    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

}
