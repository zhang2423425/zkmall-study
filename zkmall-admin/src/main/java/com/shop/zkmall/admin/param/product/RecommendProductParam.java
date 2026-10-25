/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 推荐商品的参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RecommendProductParam", description = "推荐商品的参数")
public class RecommendProductParam {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "推荐类型 1-热门商品 2-好物严选 3-特卖榜单")
    private List<Integer> recommendTypeList;

    @Schema(description = "商家id")
    private Long shopId;
}
