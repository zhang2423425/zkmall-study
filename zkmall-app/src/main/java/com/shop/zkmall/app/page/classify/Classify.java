/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 店铺所有商品所属一级类目
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopClassify", description = "店铺所有商品所属一级类目")
public class Classify {

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long classifyId;

    /**
     * 分类父节点id
     */
    @Schema(description = "分类父节点id")
    private Long classifyPid;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String classifyName;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String classifyImage;

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<Classify> childs;
}
