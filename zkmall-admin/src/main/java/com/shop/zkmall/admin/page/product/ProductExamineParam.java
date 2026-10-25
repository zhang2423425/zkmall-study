/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审核商品请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductUpdateParam", description = "审核商品请求")
public class ProductExamineParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品状态 1-通过 3-驳回
     */
    @Schema(description = "商品状态 1-通过 3-驳回")
    private Integer shelveState;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String reject;
}
