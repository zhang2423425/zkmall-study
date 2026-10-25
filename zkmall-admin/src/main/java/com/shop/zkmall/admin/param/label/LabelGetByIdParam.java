/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取标签详情
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelGetByIdParam", description = "获取标签详情")
public class LabelGetByIdParam {

    /**
     * 客户标签id
     */
    @Schema(description = "客户标签id")
    private Long buyerLabelId;
}
