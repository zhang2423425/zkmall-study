/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取商品列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ProductGetAllParam", description = "获取商品列表请求")
public class ProductGetAllParam extends PageParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 搜做字段
     */
    @Schema(description = "搜做字段")
    private String search;

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @Schema(description = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 库存状态 1-有库存 0-无库存
     */
    @Schema(description = "库存状态 1-有库存 0-无库存")
    private Integer stock;

    /**
     * 关联分类id
     */
    @Schema(description = "关联分类id")
    private Long classifyId;
}
