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
@ApiModel(value = "ShopDataDetail", description = "商家数据明细")
public class ShopDataDetail {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @Schema(description = "店铺编码")
    private String shopCode;

    /**
     * 参与商品数
     */
    @Schema(description = "参与商品数")
    private Integer products;

    /**
     * 访客数
     */
    @Schema(description = "访客数")
    private Integer visit;

    /**
     * 提交订单笔数
     */
    @Schema(description = "提交订单笔数")
    private Integer orders;

    /**
     * 成交笔数
     */
    @Schema(description = "成交笔数")
    private Integer finish;

    /**
     * 成交总金额
     */
    @Schema(description = "成交总金额")
    private BigDecimal total;
}
