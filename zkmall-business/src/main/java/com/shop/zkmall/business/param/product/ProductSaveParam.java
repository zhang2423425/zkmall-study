/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import com.shop.zkmall.commons.domain.image.Image;
import com.shop.zkmall.commons.domain.product.CereProductParameter;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductSaveParam", description = "添加商品请求")
public class ProductSaveParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    @NotBlank(message = "商品名称不能为空")
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
    @NotNull(message = "分类不能为空")
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
    @NotBlank(message = "没有选择是否需要物流")
    private String ifLogistics;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @Schema(description = "是否上架 1-上架 0-不上架")
    @NotBlank(message = "没有选择是否上架")
    private String shelveState;

    /**
     * 是否允许超卖 1-是 0-否
     */
    @Schema(description = "是否允许超卖 1-是 0-否")
    @NotBlank(message = "没有选择是否允许超卖")
    private String ifOversold;

    /**
     * 是否支持花呗分期 1-是 0-否
     */
    @Schema(description = "是否支持花呗分期 1-是 0-否")
    @NotBlank(message = "没有选择是否支持花呗分期")
    private String ifHuabei;

    /**
     * 商品描述（富文本）
     */
    @Schema(description = "商品描述（富文本）")
    private String productText;

    /**
     * 商品图片数组
     */
    @Schema(description = "商品图片数组")
    @Valid
    private List<Image> images;

    public void setImages(List<Image> images) {
        if (EmptyUtils.isEmpty(images)) {
            List<Image> list = new ArrayList<>();
            Image image = new Image();
            image.setImgPath("");
            list.add(image);
            this.images = list;
        } else {
            this.images = images;
        }
    }

    /**
     * 规格名和值数据
     */
    @Schema(description = "规格名和值数据")
    @Valid
    private List<SkuNameParam> names;

    /**
     * 规格数据
     */
    @Schema(description = "规格数据")
    @Valid
    private List<SkuParam> skus;

    @Schema(description = "是否支持积分抵扣")
    private Integer ifCredit;

    @Schema(description = "单笔订单最多抵扣多少积分")
    private Integer creditLimit;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "商品参数列表")
    private List<CereProductParameter> parameterList;
}
