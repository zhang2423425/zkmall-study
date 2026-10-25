/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 画布店铺数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CanvasShop", description = "画布店铺数据")
public class CanvasShop {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
}
