/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 删除类别请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ClassifyDeleteParam", description = "删除类别请求")
public class ClassifyDeleteParam {

    /**
     * 一级类别id
     */
    @Schema(description = "一级类别id")
    private Long oneClassifyId;
}
