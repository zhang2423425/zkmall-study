/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 首页类目数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductClassify", description = "首页类目数据")
public class ProductClassify {

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
