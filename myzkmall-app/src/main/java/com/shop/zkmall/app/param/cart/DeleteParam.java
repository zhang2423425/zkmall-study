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
 * 删除购物车商品请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DeleteParam", description = "删除购物车商品请求")
public class DeleteParam {

    /**
     * 商品规格id数组
     */
    @Schema(description = "商品规格id数组")
    private List<Long> ids;
}
