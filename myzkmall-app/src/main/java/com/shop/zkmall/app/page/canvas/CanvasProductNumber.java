/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 画布商品购买数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CanvasProductNumber", description = "画布商品购买数据")
public class CanvasProductNumber {

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 销量
     */
    @Schema(description = "销量")
    private Integer number;

}
