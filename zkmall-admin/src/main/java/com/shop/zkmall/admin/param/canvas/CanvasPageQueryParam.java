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
@ApiModel(value = "CanvasPageQueryParam", description = "新画布列表对象")
public class CanvasPageQueryParam extends PageParam {

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 画布类型
     */
    @Schema(description = "画布类型")
    private Integer type;

    @Schema(description = "终端类型 1-移动端 2-PC")
    private Integer terminal;

    /**
     * 画布类型
     */
    @Schema(description = "新画布的id，查询画布详情时传该参数")
    private Long canvasId;

}
