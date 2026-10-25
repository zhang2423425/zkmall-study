/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.collect;

import com.shop.zkmall.app.page.index.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收藏店铺数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CollectShop", description = "收藏店铺数据")
public class CollectShop {

    /**
     * 收藏id
     */
    @Schema(description = "收藏id")
    private Long collectId;

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
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String shopLogo;

    /**
     * 关注人数
     */
    @Schema(description = "关注人数")
    private Integer person;

    /**
     * 是否选中 1-是 0-否
     */
    @Schema(description = "是否选中 1-是 0-否")
    private Integer selected;

    /**
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<Product> productList;

    @Schema(description = "评价分数 保留到小数点后一位")
    private BigDecimal shopStar;

    @Schema(description = "店铺简介")
    private String shopBrief;
}
