/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺首页数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopIndex", description = "店铺首页数据")
public class ShopIndex {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 收藏id
     */
    @Schema(description = "收藏id")
    private Long collectId;

    /**
     * 是否收藏 1-是 0-否
     */
    @Schema(description = "是否收藏 1-是 0-否")
    private Integer ifCollect;

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
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 商品总类数
     */
    @Schema(description = "商品总类数")
    private Integer classifyNumber;

    /**
     * 已售件数
     */
    @Schema(description = "已售件数")
    private Integer number;

    /**
     * 粉丝数
     */
    @Schema(description = "粉丝数")
    private Integer fansNumber;

    @Schema(description = "评价分数 保留到小数点后一位")
    private BigDecimal shopStar;

    @Schema(description = "店铺简介")
    private String shopBrief;
}
