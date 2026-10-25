/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.order;

import com.shop.zkmall.app.page.settlement.Distribution;
import com.shop.zkmall.app.param.settlement.ProductSku;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 结算查询商品参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopProductParam", description = "结算查询商品参数")
public class OrderProductParam implements Serializable {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 客户领取店铺优惠券主键id
     */
    @Schema(description = "客户领取店铺优惠券主键id")
    private Long id;

    /**
     * 商品明细
     */
    @Schema(description = "商品明细")
    private List<ProductSku> skus;

    /**
     * 配送方式信息
     */
    @Schema(description = "配送方式信息")
    private Distribution distribution;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String remark;
}
