/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 首页数据返回
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Index", description = "首页数据返回")
public class Index {

    /**
     * 类目数据
     */
    @Schema(description = "类目数据")
    private List<ProductClassify> classifies;

    /**
     * 推荐店铺数据
     */
    @Schema(description = "推荐店铺数据")
    private List<RecommendShop> shops;

    /**
     * 好物推荐数据
     */
    @Schema(description = "好物推荐数据")
    private List<RecommendShop> products;
}
