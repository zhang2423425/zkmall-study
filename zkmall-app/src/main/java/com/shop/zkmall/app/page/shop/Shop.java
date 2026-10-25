/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.shop;

import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 店铺商品数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Shop", description = "店铺商品数据")
public class Shop {

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
     * 店铺log
     */
    @Schema(description = "店铺log")
    private String shopLogo;

    /**
     * 已售件数
     */
    @Schema(description = "已售件数")
    private Integer number;

    /**
     * 商品列表数据
     */
    @Schema(description = "商品列表数据")
    private Page<Product> page;
}
