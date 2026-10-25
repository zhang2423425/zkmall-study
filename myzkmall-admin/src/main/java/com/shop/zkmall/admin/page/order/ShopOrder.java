/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.order;

import com.shop.zkmall.admin.page.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopOrder", description = "订单返回数据实体类")
public class ShopOrder {
    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    private String shopName;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 支付单号
     */
    @Schema(description = "支付单号")
    private String transactionId;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private Integer state;

    /**
     * 售后状态
     */
    @Schema(description = "售后状态")
    private Integer afterState;

    /**
     * 是否支付 1-是 0-否
     */
    @Schema(description = "是否支付 1-是 0-否")
    private Integer paymentState;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private Integer paymentMode;

    /**
     * 物流方案名称
     */
    @Schema(description = "物流方案名称")
    private String logisticsName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private String paymentTime;

    /**
     * 下单账户
     */
    @Schema(description = "下单账户")
    private String customerName;

    /**
     * 下单总数
     */
    @Schema(description = "下单总数")
    private Integer total;

    /**
     * 下单备注
     */
    @Schema(description = "下单备注")
    private String remark;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String receiveName;

    /**
     * 收货人手机号
     */
    @Schema(description = "收货人手机号")
    private String receivePhone;

    /**
     * 收货人地址
     */
    @Schema(description = "收货人地址")
    private String receiveAdress;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;

    /**
     * 邮编
     */
    @Schema(description = "邮编")
    private String postalCode;

    /**
     * 商品总价
     */
    @Schema(description = "商品总价")
    private BigDecimal orderPrice;

    /**
     * 物流费用
     */
    @Schema(description = "物流费用")
    private BigDecimal logisticsPrice;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal price;

    /**
     * 商品信息数据
     */
    @Schema(description = "商品信息数据")
    private List<Product> products;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer number;

    /**
     * 售后单号
     */
    @Schema(description = "售后单号")
    private String afterFormIds;
}
