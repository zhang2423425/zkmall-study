/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.after;

import com.shop.zkmall.commons.poi.export.ExcelResources;
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
public class AfterExportDTO {

    /**
     * 售后id
     */
    @Schema(description = "售后id")
    @ExcelResources(order = 0, title = "售后id", cloumn = 20)
    private Long afterId;

    /**
     * 售后单号
     */
    @Schema(description = "售后单号")
    @ExcelResources(order = 1, title = "售后单号", cloumn = 20)
    private String afterFormid;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型  1-仅退款  2-退货退款")
    @ExcelResources(order = 2, title = "售后类型", cloumn = 20)
    private String afterType;

    /**
     * 售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     */
    @Schema(description = "售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过")
    @ExcelResources(order = 3, title = "售后状态", cloumn = 20)
    private String afterState;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @ExcelResources(order = 4, title = "订单id", cloumn = 20)
    private Long orderId;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    @ExcelResources(order = 5, title = "订单状态", cloumn = 20)
    private String orderState;

    /**
     * 售后商品数量
     */
    @Schema(description = "售后商品数量")
    @ExcelResources(order = 6, title = "售后商品数量", cloumn = 20)
    private Integer number;

    /**
     * 售后商品名称
     */
    @Schema(description = "售后商品名称")
    @ExcelResources(order = 7, title = "售后商品名称", cloumn = 20)
    private String afterProductNames;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    @ExcelResources(order = 8, title = "退款金额", cloumn = 20)
    private BigDecimal refundMoney;

    /**
     * 下单账户
     */
    @Schema(description = "买家账户")
    @ExcelResources(order = 9, title = "买家账户", cloumn = 10)
    private String customerName;

    /**
     * 支付时间
     */
    @Schema(description = "申请时间")
    @ExcelResources(order = 10, title = "申请时间", cloumn = 20)
    private String applyTime;

    /**
     * 最近处理时间
     */
    @Schema(description = "最近处理时间")
    @ExcelResources(order = 11, title = "最近处理时间", cloumn = 20)
    private String lastAfterTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelResources(order = 12, title = "备注", cloumn = 20)
    private String remark;
}
