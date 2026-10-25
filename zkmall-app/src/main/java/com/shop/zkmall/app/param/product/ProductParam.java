/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品请求参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductParam", description = "商品请求参数")
public class ProductParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 访问终端 1-APP 2-微信小程序 3-H5 4-支付宝小程序
     */
    @Schema(description = "访问终端 1-APP 2-微信小程序 3-H5 4-支付宝小程序")
    private Integer terminal;

    /**
     * 操作系统 1-Android 2-IOS
     */
    @Schema(description = "操作系统 1-Android 2-IOS")
    private Integer system;

    /**
     * 所在地区
     */
    @Schema(description = "所在地区")
    private String city;

    /**
     * 拼团活动id
     */
    @Schema(description = "拼团活动id")
    private Long shopGroupWorkId;

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
     * 秒杀活动id
     */
    @Schema(description = "秒杀活动id")
    private Long platformSeckillId;

    /**
     * 平台限时折扣活动id
     */
    @Schema(description = "平台限时折扣活动id")
    private Long platformDiscountId;
}
