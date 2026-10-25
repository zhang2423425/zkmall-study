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
 * 获取物流方案详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LogistGetByIdParam", description = "获取物流方案详情请求")
public class LogistGetByIdParam {

    /**
     * 物流方案id
     */
    @Schema(description = "物流方案id")
    private Long logisticsId;
}
