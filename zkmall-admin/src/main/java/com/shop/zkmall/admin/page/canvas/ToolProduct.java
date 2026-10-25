/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 营销活动商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ToolProduct", description = "营销活动商品数据实体类")
public class ToolProduct {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 活动价格
     */
    @Schema(description = "活动价格")
    private BigDecimal price;

    /**
     * 限时折扣/直降多少元
     */
    @Schema(description = "限时折扣")
    private BigDecimal discount;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stockNumber;

    /**
     * 库存总数
     */
    @Schema(description = "库存总数")
    private Integer total;

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private String value;

    /**
     * 已拼人数
     */
    @Schema(description = "已拼人数")
    private Integer workUsers;
}
