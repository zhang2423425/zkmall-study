/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import com.shop.zkmall.commons.domain.image.Image;
import com.shop.zkmall.commons.domain.product.CereProductParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新商品请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductUpdateParam", description = "更新商品请求")
public class ProductUpdateParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
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
     * 规格名和值数据
     */
    @Schema(description = "规格名和值数据")
    private List<SkuNameParam> names;

    /**
     * 规格数据
     */
    @Schema(description = "规格数据")
    private List<SkuParam> skus;

    /**
     * 删除规格数据
     */
    @Schema(description = "删除规格数据")
    private List<DeleteSkuParam> deletes;

    @Schema(description = "是否支持积分抵扣")
    private Integer ifCredit;

    @Schema(description = "单笔订单最多抵扣多少积分")
    private Integer creditLimit;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "商品参数列表")
    private List<CereProductParameter> parameterList;
}
