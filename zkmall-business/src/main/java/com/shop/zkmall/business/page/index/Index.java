/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.index;

import com.shop.zkmall.business.page.shop.ShopConversion;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 首页概况返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Index", description = "首页概况返回数据实体类")
public class Index {

    /**
     * 店铺访问人数
     */
    @Schema(description = "店铺访问人数")
    private Integer total;

    /**
     * 待处理订单数
     */
    @Schema(description = "待处理订单数")
    private Integer stayOrders;

    /**
     * 待处理售后订单数量
     */
    @Schema(description = "待处理售后订单数量")
    private Integer stayAfters;

    /**
     * 成交金额
     */
    @Schema(description = "成交金额")
    private BigDecimal money;

    /**
     * 当月每周访问用户数据
     */
    @Schema(description = "当月每周访问用户数据")
    private VisitWeek visitWeek;

    /**
     * 订单转化漏斗数据
     */
    @Schema(description = "订单转化漏斗数据")
    private ShopConversion conversion;

    /**
     * 热卖商品数据
     */
    @Schema(description = "热卖商品数据")
    private List<HotSellProduct> hotSellProducts;

    /**
     * 总访问人数
     */
    @Schema(description = "总访问人数")
    private Integer count;

    /**
     * 总转化率
     */
    @Schema(description = "总转化率")
    private BigDecimal rate;
}
