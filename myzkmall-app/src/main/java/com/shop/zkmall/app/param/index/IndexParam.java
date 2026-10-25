/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 首页请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "IndexParam", description = "首页请求")
public class IndexParam {

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    private Long activityId;

}
