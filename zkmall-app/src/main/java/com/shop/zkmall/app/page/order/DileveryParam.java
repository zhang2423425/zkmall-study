/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取物流信息请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DileveryParam", description = "获取物流信息请求")
public class DileveryParam {

    /**
     * 物流公司
     */
    @Schema(description = "物流公司")
    private String express;

    /**
     * 物流单号
     */
    @Schema(description = "物流单号")
    private String deliverFormid;
}
