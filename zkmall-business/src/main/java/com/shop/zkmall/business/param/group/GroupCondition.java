/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 智能添加条件
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GroupCondition", description = "智能添加条件")
public class GroupCondition {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 类型 1-库存 2-价格 3-重量 4-销量
     */
    @Schema(description = "类型 1-库存 2-价格 3-重量 4-销量")
    private Integer type;

    /**
     * 运算符 1-大于 2-等于 3-小于
     */
    @Schema(description = "运算符 1-大于 2-等于 3-小于")
    private Integer calculation;

    /**
     * 数值
     */
    @Schema(description = "数值")
    private BigDecimal number;

    /**
     * 已满足部分条件的商品id数组
     */
    @Schema(description = "已满足部分条件的商品id数组")
    private List<Long> ids;
}
