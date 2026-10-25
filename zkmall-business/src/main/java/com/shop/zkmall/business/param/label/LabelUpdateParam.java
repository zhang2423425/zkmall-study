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
 * 更新标签请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelUpdateParam", description = "更新标签请求")
public class LabelUpdateParam {

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
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;


    /**
     * 素材类型 1-图片 2-视频
     */
    @Schema(description = "素材类型 1-图片 2-视频")
    private Integer labelType;
}
