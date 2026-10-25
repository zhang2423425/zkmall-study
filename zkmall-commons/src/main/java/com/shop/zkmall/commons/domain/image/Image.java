/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.image;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 图片数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ImageParam", description = "图片数据")
public class Image {

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    @NotBlank(message = "至少上传一张图片")
    private String imgPath;
}
