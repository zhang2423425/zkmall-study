/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取分类下拉请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductGetClassifyParam", description = "获取分类下拉请求")
public class ProductGetClassifyParam {

    /**
     * 上级分类id,查一级类目传0
     */
    @Schema(description = "上级分类id,查一级类目传0")
    private Long classifyPid;
}
