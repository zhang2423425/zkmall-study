/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.after;

import com.shop.zkmall.app.page.cart.CartSku;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 售后详情
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterDetail", description = "售后详情")
public class AfterDetail {

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
     * 售后类型  1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型  1-仅退款  2-退货退款")
    private Integer afterType;

    /**
     * 售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     */
    @Schema(description = "售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过")
    private Integer afterState;

    /**
     * 商品明细
     */
    @Schema(description = "商品明细")
    private List<CartSku> skus;

    /**
     * （货物状态）是否收到货 1-是 0-否
     */
    @Schema(description = "（货物状态）是否收到货 1-是 0-否")
    private Integer goodsState;

    /**
     * 是否平台介入 1-是 0-否
     */
    @Schema(description = "是否平台介入 1-是 0-否")
    private Integer isPlaformState;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal price;

    /**
     * 售后单号
     */
    @Schema(description = "售后单号")
    private String afterFormid;

    /**
     * 商家处理留言
     */
    @Schema(description = "商家处理留言")
    private String reason;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    private String returnReason;

    /**
     * 自动关闭剩余时间
     */
    @Schema(description = "自动关闭剩余时间")
    private long time;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private String createTime;

    /**
     * 协商历史
     */
    @Schema(description = "协商历史")
    private List<AfterHistory> afterHistory;

    /**
     * 退货地址
     */
    @Schema(description = "退货地址")
    private String returnAdress;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String returnPerson;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String returnPhone;

    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "联系电话")
    private String shopName;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String shopPhone;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 支付渠道 1-微信 2-支付宝
     */
    @Schema(description = "支付渠道 1-微信 2-支付宝")
    private String paymentMode;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private String paymentTime;

    /**
     * 成交时间
     */
    @Schema(description = "联系电话")
    private String receiveTime;

    /**
     * 交易号
     */
    @Schema(description = "交易号")
    private String transactionId;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消
     */
    @Schema(description = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消")
    private Integer orderState;

    /**
     * 买家售后说明
     */
    @Schema(description = "买家售后说明")
    private String explain;

    /**
     * 买家售后图片
     */
    @Schema(description = "买家售后图片")
    private String image;

    /**
     * 售后图片数组
     */
    @Schema(description = "售后图片数组")
    private List<String> images;

    /**
     * 买家退货公司id
     */
    @Schema(description = "买家退货公司id")
    private Long express;

    /**
     * 买家退货物流单号
     */
    @Schema(description = "买家退货物流单号")
    private String deliverFormid;

    /**
     * 买家退货说明
     */
    @Schema(description = "买家退货说明")
    private String deliverReason;

    /**
     * 退款时间
     */
    @Schema(description = "退款时间")
    private Date refundTime;
}
