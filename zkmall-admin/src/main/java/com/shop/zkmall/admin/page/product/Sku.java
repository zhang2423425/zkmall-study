/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.product;

import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.CereSkuName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Sku 商品规格返回实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "Sku", description = "商品规格返回实体类")
public class Sku extends CereProductSku {

    /**
     * 规格名和值数组
     */
    @Schema(description = "规格名数组")
    private List<SkuNameValueParam> skuAttrCodeDTOList;

    /**
     * 规格属性信息
     */
    private List<CereSkuName> cereSkuNames;
}
