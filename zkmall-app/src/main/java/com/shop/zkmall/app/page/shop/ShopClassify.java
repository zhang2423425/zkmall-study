/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 店铺所有商品所属一级类目
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopClassify", description = "店铺所有商品所属一级类目")
public class ShopClassify {

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long classifyId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String classifyName;
}
