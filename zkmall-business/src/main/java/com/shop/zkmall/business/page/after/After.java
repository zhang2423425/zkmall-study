/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.after;

import com.shop.zkmall.business.page.order.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 售后单返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "After", description = "售后单返回数据实体类")
public class After {

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
     * 售后单号
     */
    @Schema(description = "售后单号")
    private String afterFormid;

    /**
     * 图片凭证地址
     */
    private String image;

    /**
     * 图片凭证地址数组
     */
    @Schema(description = "图片凭证地址数组")
    private List<String> images;

    /**
     * 协商历史
     */
    @Schema(description = "协商历史")
    private List<AfterHistory> afterHistory;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private Integer state;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型  1-仅退款  2-退货退款")
    private Integer afterType;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal price;

    /**
     * 物理费用
     */
    @Schema(description = "物理费用")
    private BigDecimal logisticsPrice;

    /**
     * 买家账户
     */
    @Schema(description = "买家账户")
    private String customerName;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private String createTime;

    /**
     * 最近处理时间
     */
    @Schema(description = "最近处理时间")
    private String updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 买家说明
     */
    @Schema(description = "买家说明")
    private String explain;

    /**
     * 卖家留言
     */
    @Schema(description = "卖家留言")
    private String reason;

    /**
     * 快递公司
     */
    @Schema(description = "快递公司")
    private String express;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String deliverFormid;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private List<Product> products;

    /**
     * 售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     */
    @Schema(description = "售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过")
    private Integer afterState;

    /**
     * 售后状态名称
     */
    @Schema(description = "售后状态名称")
    private String afterStateName;

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
     * 申请平台介入时间
     */
    @Schema(description = "最近处理时间")
    private String lastAfterTime;


}
