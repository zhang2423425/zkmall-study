/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("同步给scrm的商品对象")
@Data
public class SyncShopProduct extends ShopProduct {

    private List<Sku> skuList;

    @Schema(description = "分类名称")
    private String classifyName;

}
