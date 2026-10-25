/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.product;

import com.shop.zkmall.admin.page.product.ProductClassify;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * 添加类别请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ClassifyParam", description = "添加类别请求")
public class ClassifyLevelParam {

    /**
     * 删除分类id数组
     */
    @Schema(description = "删除分类id数组")
    private List<Long> deleteIds;

    /**
     * 分类层级数据
     */
    @Schema(description = "分类层级数据")
    @Valid
    private List<ProductClassify> classifies;
}
