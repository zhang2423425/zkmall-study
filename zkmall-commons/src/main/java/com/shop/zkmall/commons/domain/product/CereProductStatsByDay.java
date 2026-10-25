/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;


import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_stats_by_day 商品日度统计表
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereProductStatsByDay", description = "商品日度统计表")
public class CereProductStatsByDay implements Serializable {

    @Schema(description = "日期")
    private String createDate;

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "加购量")
    private Integer addCartCount;

    @Schema(description = "访问量")
    private Integer visitCount;

    @Schema(description = "销量")
    private Integer salesVolume;

}
