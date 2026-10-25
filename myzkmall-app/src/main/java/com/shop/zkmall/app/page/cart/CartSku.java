/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.cart;

import com.shop.zkmall.commons.domain.product.DiscountProduct;
import com.shop.zkmall.commons.domain.product.Sku;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 购物车商品明细数据
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CartSku", description = "购物车商品明细数据")
public class CartSku extends DiscountProduct {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 是否追评 1-是 0-否
     */
    @Schema(description = "是否追评 1-是 0-否")
    private Integer ifAdd;

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
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal originalPrice;

    /**
     * 售价
     */
    @Schema(description = "售价")
    private BigDecimal price;

    /**
     * 实际付款总价
     */
    @Schema(description = "实际付款总价")
    private BigDecimal actualPrice;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal weight;

    @Schema(description = "是否允许积分兑换")
    private Integer ifCredit;

    @Schema(description = "一笔订单中，该商品最多抵扣多少金额")
    private Integer creditLimit;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer number;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stockNumber;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Integer limitNumber;

    /**
     * 商品小计
     */
    @Schema(description = "商品小计")
    private BigDecimal total;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String image;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    private String SKU;

    /**
     * 规格值拼接字符串
     */
    private String value;

    /**
     * 规格值数据
     */
    @Schema(description = "规格值数据")
    private List<String> values;

    /**
     * 是否需要物流
     */
    @Schema(description = "是否需要物流")
    private Integer ifLogistics;

    /**
     * 是否允许超卖 1-是 0-否
     */
    @Schema(description = "是否允许超卖 1-是 0-否")
    private Integer ifOversold;

    /**
     * 选中状态 1-选中 0-未选中
     */
    @Schema(description = "选中状态 1-选中 0-未选中")
    private Integer selected;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @Schema(description = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;

    /**
     * 是否允许申请售后 1-允许 0-不允许
     */
    private Integer ifAfter;

    /**
     * 售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     */
    @Schema(description = "售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过")
    private Integer afterState;

    /**
     * 是否上架 1-是 0-否
     */
    @Schema(description = "是否上架 1-是 0-否")
    private Integer shelveState;

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
     * 是否使用会员价
     */
    @Schema(description = "是否使用会员价")
    private boolean useMember;

    /**
     * 定价捆绑id
     */
    @Schema(description = "定价捆绑id")
    private Long priceId;

    /**
     * 组合捆绑id
     */
    @Schema(description = "组合捆绑id")
    private Long composeId;

    /**
     * 组合捆绑id列表
     */
    private List<Long> composeIdList;

    /**
     * 场景营销id
     */
    @Schema(description = "场景营销id")
    private Long sceneId;

    /**
     * 商品所有组合规格数据
     */
    @Schema(description = "商品所有组合规格数据")
    private Map<String, Sku> map;

    /**
     * 是否允许叠加优惠券
     */
    @Schema(description = "是否允许叠加优惠券")
    private Integer ifCouponAdd;

    /**
     * 当activityType有值时，相应的活动id可以设置到这里，方便取值
     */
    private Long activityId;

    /**
     * 当前sku抵扣积分
     */
    @Schema(description = "当前sku抵扣积分")
    private Integer useCredit;

    /**
     * 当前sku积分抵扣的金额
     */
    @Schema(description = "当前sku积分抵扣的金额")
    private BigDecimal useCreditAmount;

    /**
     * 运费价格
     */
    @Schema(description = "运费价格")
    private BigDecimal logisticsPrice;

    /**
     * 定价捆绑的优惠金额
     */
    @Schema(description = "定价捆绑的优惠金额")
    private BigDecimal pricingPrice;

    /**
     * 平台优惠券优惠的金额
     */
    @Schema(description = "平台优惠券优惠的金额")
    private BigDecimal discountPrice;

    /**
     * 商家优惠券优惠的金额
     */
    @Schema(description = "商家优惠券优惠的金额")
    private BigDecimal shopDiscountPrice;

    /**
     * 平台优惠券id 有值则代表该sku享受了平台优惠券
     */
    @Schema(description = "平台优惠券id")
    private Long buyerCouponId;

    /**
     * 商家优惠券id 有值就代表该sku享受了商家优惠券
     */
    @Schema(description = "商家优惠券id")
    private Long buyerShopCouponId;

    @Schema(description = "购物车id")
    private Long cartId;

    @Schema(description = "是否收藏 1-是 0-否")
    private Integer ifCollect = 0;

    /**
     * 由于购物车可能要切换规格，所以购物车中的商品id，对应的其他sku也要查询出来
     */
    @Schema(description = "购物车中选中的sku的id")
    private Integer cartSkuId;
}

