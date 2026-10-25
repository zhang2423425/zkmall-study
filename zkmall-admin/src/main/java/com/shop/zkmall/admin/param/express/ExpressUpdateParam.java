/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.express;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 切换物流策略请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ExpressParam", description = "切换物流策略请求")
public class ExpressUpdateParam {

    /**
     * 对接第三方快递类型 1-快递100 2-快递鸟
     */
    @Schema(description = "对接第三方快递类型 1-快递100 2-快递鸟")
    private Integer expressType;
}
