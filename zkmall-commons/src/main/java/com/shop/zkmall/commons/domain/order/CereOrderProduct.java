/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_order_product   订单商品信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereOrderProduct implements Serializable {

    /**
     * 订单商品明细id
     */
    @TableId(type = IdType.AUTO)
    private Long orderProductId;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 关联规格id
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 商品售价
     */
    private BigDecimal productPrice;

    /**
     * 该sku实际总支付加个(不包含运费和积分)
     */
    private BigDecimal actualPrice;

    /**
     * 图片地址
     */
    private String image;

    /**
     * SKU
     */
    @TableField("SKU")
    private String SKU;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 积分抵扣金额
     */
    private BigDecimal useCreditAmount;

    /**
     * 抵扣的积分数
     */
    private Integer useCredit;

    /**
     * 运费价格
     */
    private BigDecimal logisticsPrice;

}
