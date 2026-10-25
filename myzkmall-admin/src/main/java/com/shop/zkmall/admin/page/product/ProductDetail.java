/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import com.shop.zkmall.commons.domain.image.Image;
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
@ApiModel(value = "ProductDetail", description = "商品返回数据实体类")
public class ProductDetail {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 关联店铺名称
     */
    @Schema(description = "关联店铺名称")
    private String shopName;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 卖点简介
     */
    @Schema(description = "卖点简介")
    private String productBrief;

    /**
     * 关联商品分组id
     */
    @Schema(description = "关联商品分组id")
    private Long shopGroupId;

    /**
     * 关联分类id
     */
    @Schema(description = "关联分类id")
    private Long classifyId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String classifyName;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String shopGroupName;

    /**
     * 关联供应商id
     */
    @Schema(description = "关联供应商id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String supplierName;

    /**
     * 是否需要物流 1-是 0-否
     */
    @Schema(description = "是否需要物流 1-是 0-否")
    private Integer ifLogistics;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @Schema(description = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 是否允许超卖 1-是 0-否
     */
    @Schema(description = "是否允许超卖 1-是 0-否")
    private Integer ifOversold;

    /**
     * 是否支持花呗分期 1-是 0-否
     */
    @Schema(description = "是否支持花呗分期 1-是 0-否")
    private Integer ifHuabei;

    /**
     * 商品描述（富文本）
     */
    @Schema(description = "商品描述（富文本）")
    private String productText;

    /**
     * 商品图片数组
     */
    @Schema(description = "商品图片数组")
    private List<Image> images;

    /**
     * 规格数据
     */
    @Schema(description = "规格数据")
    private List<Sku> skus;

    /**
     * 商品图片第一张
     */
    @Schema(description = "商品图片第一张")
    private String productImage;

    /**
     * 规格图片第一张
     */
    @Schema(description = "规格图片第一张")
    private String skuImage;

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
     * 销量
     */
    @Schema(description = "销量")
    private Integer volume;

    /**
     * 规格名和值数据
     */
    @Schema(description = "规格名和值数据")
    private List<SkuNameParam> names;
}
