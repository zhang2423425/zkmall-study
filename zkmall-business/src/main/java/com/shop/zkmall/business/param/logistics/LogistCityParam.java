/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取城市列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LogistCityParam", description = "获取城市列表请求")
public class LogistCityParam {

    /**
     * 父节点id  查省份传0
     */
    @Schema(description = "父节点id  查省份传0")
    private Long cityPid;
}
