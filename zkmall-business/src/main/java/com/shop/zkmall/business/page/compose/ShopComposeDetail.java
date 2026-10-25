/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.compose;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组合捆绑详情
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopComposeDetail", description = "组合捆绑详情")
public class ShopComposeDetail {

    /**
     * 组合id
     */
    @Schema(description = "组合id")
    private Long composeId;

    /**
     * 组合名称
     */
    @Schema(description = "组合名称")
    private String composeName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;

    /**
     * 定价类型
     */
    @Schema(description = "定价类型")
    private Integer composeType;

    /**
     * 定价内容
     */
    @Schema(description = "定价内容")
    private BigDecimal promote;

    /**
     * 组合商品数据
     */
    @Schema(description = "组合商品数据")
    private List<ComposeProduct> composeProducts;
}
