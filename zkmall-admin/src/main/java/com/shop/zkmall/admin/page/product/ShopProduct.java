/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopProduct", description = "商品返回数据实体类")
public class ShopProduct {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 售价区间
     */
    @Schema(description = "售价区间")
    private String sectionPrice;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Integer stockNumber;

    /**
     * 实际销量
     */
    @Schema(description = "实际销量")
    private Integer volume;

    /**
     * 虚拟销量
     */
    @Schema(description = "虚拟销量")
    private Integer fictitiousNumber;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    @Schema(description = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
    private Integer shelveState;

    /**
     * 品牌id
     */
    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "商品推荐类型列表")
    private List<Integer> recommendTypeList;

}
