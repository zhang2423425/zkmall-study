/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.group;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 获取分组商品列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "GroupProductParam", description = "获取分组商品列表请求")
public class GroupProductParam extends PageParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 库存数量最小值
     */
    @Schema(description = "库存数量最小值")
    private Integer minStock;

    /**
     * 库存数量最大值
     */
    @Schema(description = "库存数量最大值")
    private Integer maxStock;

    /**
     * 价格最小值
     */
    @Schema(description = "价格最小值")
    private BigDecimal minPrice;

    /**
     * 价格最大值
     */
    @Schema(description = "价格最大值")
    private BigDecimal maxPrice;
}
