/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 导出标签请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelExcelParam", description = "导出标签请求")
public class LabelExcelParam {

    /**
     * 标签id数组
     */
    @Schema(description = "标签id数组")
    private List<Long> ids;
}
