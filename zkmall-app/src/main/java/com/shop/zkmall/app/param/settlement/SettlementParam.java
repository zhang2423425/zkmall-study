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
 * 结算查询请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SettlementParam", description = "结算查询请求")
public class SettlementParam {

    /**
     * 请求类型 1-立即购买（秒杀活动、限时折扣活动） 2-购物车结算
     */
    @Schema(description = "请求类型 1-立即购买 2-购物车结算")
    private Integer type;

    /**
     * 收货地址id
     */
    @Schema(description = "收货地址id")
    private Long receiveId;

    /**
     * 购物车商品数据
     */
    @Schema(description = "购物车商品数据")
    private List<ShopProductParam> shops;

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
     * 是否拼团商品单独购买 1-是 0-否
     */
    @Schema(description = "是否拼团商品单独购买 1-是 0-否")
    private Integer ifWork;

}
