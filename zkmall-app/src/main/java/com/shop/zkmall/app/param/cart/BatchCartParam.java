/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 批量加入购物车
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BatchCartParam", description = "批量加入购物车")
public class BatchCartParam {

    /**
     * 商品数据
     */
    @Schema(description = "商品数据")
    private List<AddCartParam> carts;
}
