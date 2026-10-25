/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.cache.product;

import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.domain.product.SkuName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("商品缓存bo")
public class ProductBo implements Serializable {

    @Serial
    private static final long serialVersionUID = -2611598963152137284L;

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
     * 活动仅剩件数
     */
    @Schema(description = "活动仅剩件数")
    private Integer surplusNumber;

    /**
     * 活动限制数量总数
     */
    @Schema(description = "活动限制数量总数")
    private Integer total;

    /**
     * 秒杀活动开始时间
     */
    @Schema(description = "秒杀活动开始时间")
    private String effectiveStart;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺名称")
    private String shopLogo;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long classifyId;

    /**
     * 商品总类数
     */
    @Schema(description = "商品总类数")
    private Integer classifyNumber;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

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
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * 快递费用
     */
    @Schema(description = "快递费用")
    private BigDecimal logisticsPrice;

    /**
     * 已售件数
     */
    @Schema(description = "已售件数")
    private Integer number;

    /**
     * 商品详情描述
     */
    @Schema(description = "商品详情描述")
    private String text;

    /**
     * 商品图片数组
     */
    @Schema(description = "商品图片数组")
    private List<String> images;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String activityName;

    /**
     * 商品简介
     */
    @Schema(description = "商品简介")
    private String productBrief;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @Schema(description = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;


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
     * 规格名数组
     */
    @Schema(description = "规格名数组")
    private List<SkuName> names;

    /**
     * 商品所有组合规格数据
     */
    @Schema(description = "商品所有组合规格数据")
    private Map<String, Sku> map;

    /**
     * 是否上架 1-是 0-否
     */
    @Schema(description = "是否上架 1-是 0-否")
    private Integer shelveState;

    /**
     * 是否支持花呗分期
     */
    @Schema(description = "是否支持花呗分期 1-是 0-否")
    private Integer ifHuabei;

    /**
     * 场景营销id
     */
    @Schema(description = "场景营销id")
    private Long sceneId;

    /**
     * 场景营销包邮
     */
    @Schema(description = "场景营销包邮")
    private Integer sceneFreeShipping;

    /**
     * 场景营销折扣
     */
    @Schema(description = "场景营销折扣")
    private BigDecimal sceneDiscount;

    /**
     * 活动是否允许优惠券叠加
     */
    @Schema(description = "活动是否允许优惠券叠加")
    private Integer ifAdd;

    /**
     * 上一次更新时的时间戳
     */
    private long lastUpdateTimestamp;
}
