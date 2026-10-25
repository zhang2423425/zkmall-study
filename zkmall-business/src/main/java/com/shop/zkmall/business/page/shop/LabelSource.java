/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 素材返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelSource", description = "素材返回数据实体类")
public class LabelSource {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long labelId;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 链接
     */
    @Schema(description = "链接")
    private String link;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;
}
