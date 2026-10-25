/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.settlement;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 结算查询商品参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopProductParam", description = "结算查询商品参数")
public class ShopProductParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商品明细
     */
    @Schema(description = "商品明细")
    private List<ProductSku> skus;

    /**
     * 秒杀活动id
     */
    @Schema(description = "秒杀活动id")
    private Long shopSeckillId;

    /**
     * 限时折扣活动id
     */
    @Schema(description = "限时折扣活动id")
    private Long shopDiscountId;

    /**
     * 平台秒杀活动id
     */
    @Schema(description = "平台秒杀活动id")
    private Long platformSeckillId;

    /**
     * 平台限时折扣活动id
     */
    @Schema(description = "平台限时折扣活动id")
    private Long platformDiscountId;

    /**
     * 组合捆绑id
     */
    @Schema(description = "组合捆绑id")
    private Long composeId;

}
