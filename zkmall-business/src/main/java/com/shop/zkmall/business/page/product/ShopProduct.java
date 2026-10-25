/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.product;

import com.shop.zkmall.business.param.product.SkuNameParam;
import com.shop.zkmall.commons.domain.image.Image;
import com.shop.zkmall.commons.domain.product.CereProductParameter;
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

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

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
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    @Schema(description = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
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
     * 售价区间
     */
    @Schema(description = "售价区间")
    private String section;

    /**
     * 会员价区间
     */
    @Schema(description = "会员价区间")
    private String memberSection;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 规格名和值数据
     */
    @Schema(description = "规格名和值数据")
    private List<SkuNameParam> names;

    @Schema(description = "是否支持积分抵扣")
    private Integer ifCredit;

    @Schema(description = "单笔订单最多抵扣多少积分")
    private Integer creditLimit;

    @Schema(description = "sku最高价格")
    private BigDecimal maxPrice;

    @Schema(description = "驳回原因")
    private String reject;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "一级分类id")
    private Long classifyId1;

    @Schema(description = "二级分类id")
    private Long classifyId2;

    @Schema(description = "三级分类id")
    private Long classifyId3;

    @Schema(description = "商品推荐类型列表")
    private List<Integer> recommendTypeList;

    @Schema(description = "商品参数列表")
    private List<CereProductParameter> parameterList;
}
