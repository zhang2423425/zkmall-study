/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_label_source  店铺素材信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereLabelSource", description = "店铺素材信息实体类")
public class CereLabelSource implements Serializable {
    /**
     * 店铺标签id
     */
    @Schema(description = "店铺标签id")
    private Long labelId;

    /**
     * 素材类型 1-图片 2-视频
     */
    @Schema(description = "素材类型 1-图片 2-视频")
    private Integer labelType;

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
