/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 添加标签请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelSaveParam", description = "添加标签请求")
public class LabelSaveParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @NotBlank(message = "标签名称不能为空")
    private String labelName;


    /**
     * 素材类型 1-图片 2-视频
     */
    @Schema(description = "素材类型 1-图片 2-视频")
    private Integer labelType;
}
