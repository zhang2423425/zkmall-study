/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分享商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShareProduct", description = "分享商品数据")
public class ShareProduct {

    /**
     * 客户姓名
     */
    @Schema(description = "客户姓名")
    private String name;

    /**
     * 客户头像
     */
    @Schema(description = "客户头像")
    private String headImage;

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
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal weight;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String SKU;

    /**
     * 规格值拼接字符串
     */
    private String value;

    /**
     * 规格值数据
     */
    @Schema(description = "规格值数据")
    private List<String> values;

    /**
     * 小程序码
     */
    @Schema(description = "小程序码")
    private String shareImage;

}
