/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.collect;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 收藏商品、店铺请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CollectParam", description = "收藏商品、店铺请求")
public class CollectParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;
}
