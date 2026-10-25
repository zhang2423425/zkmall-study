/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.canvas;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 画布列表
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CanvasPageParam", description = "画布列表对象")
public class CanvasPageParam extends PageParam {

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 画布id
     */
    @Schema(description = "画布id")
    private Long canvasId;

}
