/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.after;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_after_product 售后单商品信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereAfterProduct implements Serializable {

    /**
     * 售后单商品明细id
     */
    @TableId(type = IdType.AUTO)
    private Long afterProductId;

    /**
     * 关联售后单id
     */
    private Long afterId;

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

}
