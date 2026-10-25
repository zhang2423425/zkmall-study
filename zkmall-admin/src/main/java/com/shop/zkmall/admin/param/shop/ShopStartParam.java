/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 启停用商家请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopStartParam", description = "启停用商家请求")
public class ShopStartParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;
}
