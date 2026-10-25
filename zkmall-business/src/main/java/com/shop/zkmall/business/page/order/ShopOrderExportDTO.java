/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.order;

import com.shop.zkmall.commons.poi.export.ExcelResources;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单导出实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopOrderExportDTO", description = "订单导出实体类")
public class ShopOrderExportDTO {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @ExcelResources(order = 0, title = "订单id", cloumn = 15)
    private Long orderId;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    @ExcelResources(order = 1, title = "订单状态", cloumn = 10)
    private String state;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    @ExcelResources(order = 2, title = "支付方式", cloumn = 10)
    private String paymentMode;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    @ExcelResources(order = 3, title = "支付金额", cloumn = 10)
    private BigDecimal price;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    @ExcelResources(order = 4, title = "商品数量", cloumn = 10)
    private Integer number = 0;


    /**
     * 商品信息数据
     */
    @Schema(description = "商品信息数据")
    @ExcelResources(order = 5, title = "全部商品名称/品牌/国际条码", cloumn = 20)
    private String productNames;

    /**
     * 下单账户
     */
    @Schema(description = "下单账户")
    @ExcelResources(order = 6, title = "下单账户", cloumn = 10)
    private String customerName;

    /**
     * 历史订单总数
     */
    @Schema(description = "历史订单总数")
    @ExcelResources(order = 7, title = "历史订单总数", cloumn = 20)
    private Integer historyOrderNum;

    /**
     * 支付单号
     */
    @Schema(description = "支付单号")
    @ExcelResources(order = 8, title = "支付单号", cloumn = 20)
    private String transactionId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelResources(order = 9, title = "创建时间", cloumn = 20)
    private String createTime;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    @ExcelResources(order = 10, title = "支付时间", cloumn = 20)
    private String paymentTime;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    @ExcelResources(order = 11, title = "收货人姓名", cloumn = 15)
    private String receiveName;

    /**
     * 收货人手机号
     */
    @Schema(description = "收货人手机号")
    @ExcelResources(order = 12, title = "收货手机号", cloumn = 15)
    private String receivePhone;

    /**
     * 收货人地址
     */
    @Schema(description = "收货人地址")
    @ExcelResources(order = 13, title = "收货地址", cloumn = 20)
    private String receiveAdress;

    /**
     * 下单备注
     */
    @Schema(description = "下单备注")
    @ExcelResources(order = 14, title = "下单备注", cloumn = 20)
    private String remark;

}
