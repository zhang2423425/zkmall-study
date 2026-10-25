/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.cart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_cart  购物车实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopCart", description = "购物车实体")
public class CereShopCart implements Serializable {
    /**
     * 购物车id
     */
    @Schema(description = "购物车id")
    @TableId(type = IdType.AUTO)
    private Long cartId;

    /**
     * 关联客户id
     */
    @Schema(description = "关联客户id")
    private Long buyerUserId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 关联商品id
     */
    @Schema(description = "关联商品id")
    private Long productId;

    /**
     * 关联规格id
     */
    @Schema(description = "关联规格id")
    private Long skuId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Integer number;

    /**
     * 商品售价
     */
    @Schema(description = "商品售价")
    private BigDecimal productPrice;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String image;

    /**
     * SKU
     */
    @Schema(description = "SKU")
    @TableField("SKU")
    private String SKU;

    /**
     * 重量
     */
    @Schema(description = "重量")
    private BigDecimal weight;

    /**
     * 是否选中 1-是 0-否
     */
    @Schema(description = "是否选中 1-是 0-否")
    private Integer selected;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

}
