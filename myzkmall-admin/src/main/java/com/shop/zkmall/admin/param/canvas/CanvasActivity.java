/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.canvas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询最新活动返回的活动信息
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CanvasActivity implements Serializable {

    @Schema(description = "活动类型")
    private Integer activityType;

    @Schema(description = "活动id")
    private Long activityId;

    @Schema(description = "活动名称")
    private String activityName;

    @Schema(description = "开始时间")
    private String startTime;
}
