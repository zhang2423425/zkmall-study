/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import com.shop.zkmall.commons.domain.image.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 商品类别返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductClassify", description = "商品类别返回数据实体类")
public class ProductClassify {

    /**
     * 一级类别id
     */
    @Schema(description = "一级类别id")
    private Long id;

    /**
     * 类别名称
     */
    @Schema(description = "类别名称")
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

    /**
     * 类别链接
     */
    @Schema(description = "类别链接")
    private Long link;

    /**
     * 类别图片地址
     */
    @Schema(description = "类别图片地址")
    private String categoryImg;

    /**
     * 类别图片地址
     */
    @Schema(description = "类别图片地址")
    private List<Image> categoryImgArray;

    /**
     * 类别级别
     */
    @Schema(description = "类别级别")
    private Integer depth;

    /**
     * 子节点数据
     */
    @Schema(description = "子节点数据")
    private List<ProductClassify> childs;
}
