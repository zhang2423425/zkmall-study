/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询分类请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ClassifyParam", description = "查询分类请求")
public class ClassifyParam {

    /**
     * 分类父节点id(查一级类目时不传值)
     */
    @Schema(description = "分类父节点id(查一级类目时不传值)")
    private Long classifyId;
}
