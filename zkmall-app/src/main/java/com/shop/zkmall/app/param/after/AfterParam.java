/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.after;

import com.shop.zkmall.app.param.settlement.ProductSku;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 申请售后请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterParam", description = "申请售后请求")
public class AfterParam {

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
     * 是否全选  0-否  1-全选
     */
    @Schema(description = "是否全选  0-否  1-全选")
    private Integer isAllSelect;

    /**
     * 商品明细
     */
    @Schema(description = "商品明细")
    private List<ProductSku> skus;

    /**
     * （货物状态）是否收到货 1-是 0-否
     */
    @Schema(description = "（货物状态）是否收到货 1-是 0-否")
    private Integer goodsState;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal price;

    /**
     * 说明
     */
    @Schema(description = "说明")
    private String explain;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    private String returnReason;

    /**
     * 图片地址（多个以逗号隔开）
     */
    @Schema(description = "图片地址（多个以逗号隔开）")
    private String image;
}
