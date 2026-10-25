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
 * 画布商品付款人数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CanvasProductUsers", description = "画布商品付款人数")
public class CanvasProductUsers {

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 付款人数
     */
    @Schema(description = "付款人数")
    private Integer users;


}
