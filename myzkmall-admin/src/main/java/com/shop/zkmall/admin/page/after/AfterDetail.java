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
import java.util.List;

/**
 * 售后详情返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterDetail", description = "售后详情返回数据实体类")
public class AfterDetail {

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
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 售后单号
     */
    @Schema(description = "售后单号")
    private String afterFormid;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private String paymentTime;

    /**
     * 支付方式 1-微信支付
     */
    @Schema(description = "支付方式 1-微信支付")
    private String paymentMode;

    /**
     * 订单总金额
     */
    @Schema(description = "订单总金额")
    private BigDecimal orderPrice;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 物流单号
     */
    @Schema(description = "物流单号")
    private String deliverFormid;

    /**
     * 物流公司
     */
    @Schema(description = "物流公司")
    private String express;

    /**
     * 售后类型 1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型 1-仅退款  2-退货退款")
    private String afterType;

    /**
     * 买家账户id
     */
    @Schema(description = "买家账户id")
    private Long buyerUserId;

    /**
     * 生效日期
     */
    @Schema(description = "生效日期")
    private String effectiveDate;

    /**
     * 生效年限
     */
    @Schema(description = "生效年限")
    private Integer effectiveYear;

    /**
     * 买家账户
     */
    @Schema(description = "买家账户")
    private String customerName;

    /**
     * 商品明细
     */
    @Schema(description = "商品明细")
    private List<AfterProduct> products;

    /**
     * 协商历史
     */
    @Schema(description = "协商历史")
    private List<AfterHistory> afterHistory;

    /**
     * 支付单号
     */
    @Schema(description = "支付单号")
    private String transactionId;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭
     */
    @Schema(description = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭")
    private Integer state;

    /**
     * 售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭
     */
    @Schema(description = "售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭")
    private Integer afterState;

    /**
     * 物流费用
     */
    @Schema(description = "物流费用")
    private BigDecimal logisticsPrice;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountPrice;

    /**
     * 实际支付金额
     */
    @Schema(description = "实际支付金额")
    private BigDecimal price;

    /**
     * 合同有效期
     */
    @Schema(description = "合同有效期")
    private String effectiveTime;

    /**
     * 负责人姓名
     */
    @Schema(description = "负责人姓名")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @Schema(description = "负责人电话")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    /**
     * 买家留言
     */
    @Schema(description = "买家留言")
    private String explain;

    /**
     * 卖家留言
     */
    @Schema(description = "卖家留言")
    private String reason;
}
