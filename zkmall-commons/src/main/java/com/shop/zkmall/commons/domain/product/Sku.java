/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 规格数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Sku", description = "规格数据")
public class Sku implements Serializable {

    @Serial
    private static final long serialVersionUID = -6415941333616523286L;
    /**
     * 规格值code拼接字符串
     */
    @Schema(description = "规格值code拼接字符串")
    private String valueCodes;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 当商品参加拼团活动时 price变成 拼团价 salePrice放原来的售价
     */
    @Schema(description = "单独购买价")
    private BigDecimal salePrice;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Integer stockNumber;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @Schema(description = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    private Long activityId;

    /**
     * 店铺拼团活动id
     */
    @Schema(description = "店铺拼团活动id")
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

    /**
     * 场景营销id
     */
    @Schema(description = "场景营销id")
    private Long sceneId;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    private String endTime;

    /**
     * 活动结束倒计时
     */
    @Schema(description = "活动结束倒计时")
    private long time = 10000;

    /**
     * 是否正在进行活动预热 1-是 0-否
     */
    @Schema(description = "是否正在进行活动预热 1-是 0-否 ")
    private Integer ifEnable;

    /**
     * 成团人数
     */
    @Schema(description = "成团人数")
    private Integer person;

    /**
     * 活动限制数量总数
     */
    @Schema(description = "活动限制数量总数")
    private Integer total;
}
