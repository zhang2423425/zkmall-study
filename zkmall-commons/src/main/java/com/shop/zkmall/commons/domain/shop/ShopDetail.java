/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家数据明细
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopDetail", description = "商家数据明细")
public class ShopDetail {

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long shopId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String shopCode;

    /**
     * 参与商品数量
     */
    @Schema(description = "参与商品数量")
    private Integer products;

    /**
     * 访客数
     */
    @Schema(description = "访客数")
    private Integer persons;

    /**
     * 订单数
     */
    @Schema(description = "订单数")
    private Integer orders;

    /**
     * 成交客户数
     */
    @Schema(description = "成交客户数")
    private Integer customers;

    /**
     * 客单价
     */
    @Schema(description = "客单价")
    private BigDecimal price;

    /**
     * 成交总额
     */
    @Schema(description = "成交总额")
    private BigDecimal total;

//    public ShopDetail(String 胜多负少, String sj1338, int i, int i1, int i2, int i3, BigDecimal decimal, BigDecimal decimal1) {
//
//    }
}
