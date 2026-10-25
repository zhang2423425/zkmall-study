/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 上传素材请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelSaveSourceParam", description = "上传素材请求")
public class LabelSaveSourceParam {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long labelId;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private List<String> images;

    /**
     * 素材地址
     */
    @Schema(description = "素材地址")
    private String image;
}
