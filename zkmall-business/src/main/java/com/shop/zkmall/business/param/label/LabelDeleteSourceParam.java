/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 删除素材图片请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelDeleteSourceParam", description = "删除素材图片请求")
public class LabelDeleteSourceParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long labelId;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String image;

    /**
     * 链接
     */
    @Schema(description = "链接")
    private String link;
}
