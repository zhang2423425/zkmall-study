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
 * 物流明细返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Dilevery", description = "物流明细返回数据实体类")
public class Dilevery {

    /**
     * 时间
     */
    @Schema(description = "时间")
    private String time;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String reason;
}
