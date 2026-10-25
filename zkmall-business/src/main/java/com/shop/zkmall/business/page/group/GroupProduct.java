/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分组商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GroupProduct", description = "分组商品数据")
public class GroupProduct {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 上架状态 1-已上架 0-未上架
     */
    @Schema(description = "上架状态 1-已上架 0-未上架")
    private Integer shelveState;

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
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stockNumber;
}
