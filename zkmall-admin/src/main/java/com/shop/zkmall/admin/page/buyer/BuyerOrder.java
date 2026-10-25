/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户订单返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerOrder", description = "客户订单返回数据实体类")
public class BuyerOrder {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer products;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal price;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭
     */
    @Schema(description = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭")
    private Integer state;
}
