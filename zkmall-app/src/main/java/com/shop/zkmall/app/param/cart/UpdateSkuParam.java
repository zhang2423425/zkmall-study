/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新商品规格请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UpdateSkuParam", description = "更新商品规格请求")
public class UpdateSkuParam {

    /**
     * 原来的规格id
     */
    @Schema(description = "原来的规格id")
    private Long skuId;

    /**
     * 修改后的规格id
     */
    @Schema(description = "修改后的规格id")
    private Long newSkuId;

    /**
     * 修改后的购买数量
     */
    @Schema(description = "修改后的购买数量")
    private Integer number;

}
