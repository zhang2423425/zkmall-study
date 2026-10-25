/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.label;

import com.shop.zkmall.commons.domain.label.CereLabelSource;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 店铺标签数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopLabel", description = "店铺标签数据")
public class ShopLabel {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long labelId;

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;

    /**
     * 素材数据
     */
    @Schema(description = "素材数据")
    private List<CereLabelSource> sources;
}
