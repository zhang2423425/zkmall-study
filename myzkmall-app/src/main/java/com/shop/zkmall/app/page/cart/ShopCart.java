/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 购物车数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopCart", description = "购物车数据")
public class ShopCart {

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
     * 选中状态 1-选中 0-未选中
     */
    @Schema(description = "选中状态 1-选中 0-未选中")
    private Integer selected;

    /**
     * 商品数据
     */
    @Schema(description = "商品数据")
    private List<CartSku> skus;
}
