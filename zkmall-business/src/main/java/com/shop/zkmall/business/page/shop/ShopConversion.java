/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺转化统计数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopConversion", description = "店铺转化统计数据")
public class ShopConversion {

    /**
     * 文字描述
     */
    @Schema(description = "文字描述")
    private List<String> names;

    /**
     * 转化率
     */
    @Schema(description = "转化率")
    private List<BigDecimal> rates;
}
