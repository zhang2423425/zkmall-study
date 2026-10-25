/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.domain.product;

import com.shop.zkmall.app.domain.common.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.time.LocalDateTime;


/**
 * <p>
 * 实体类
 * 商品表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Product", description = "商品表")
@AllArgsConstructor
public class Product extends Entity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 供应商
     */
    @Schema(description = "供应商")
    @NotEmpty(message = "供应商不能为空")
    @Length(max = 255, message = "供应商长度不能超过255")
    private String supplierName;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    /**
     * 商品类目ID.必须是叶子类目ID
     */
    @Schema(description = "商品类目ID.必须是叶子类目ID")
    private Long categoryId;

    /**
     * 分组id
     */
    @Schema(description = "分组id")
    private Long groupId;

    /**
     * 物流模板id
     */
    @Schema(description = "物流模板id")
    private Long logisticsId;

    /**
     * 商品卖点
     */
    @Schema(description = "商品简称")
    @Length(max = 10, message = "商品简称长度不能超过10")
    private String shortName;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    @Length(max = 40, message = "商品名称长度不能超过40")
    private String productName;

    /**
     * 商品卖点说明文字 例如：全网最便宜
     */
    @Schema(description = "商品卖点说明文字 例如：全网最便宜")
    //@Length(max = 255, message = "商品卖点说明文字 例如：全网最便宜长度不能超过20")
    private String sellDesc;

    /**
     * 商品自编号
     */
    @Schema(description = "商品自编号")
    @Length(max = 30, message = "商品自编号长度不能超过30")
    private String productCode;

    /**
     * 重量，单位：克
     */
    @Schema(description = "重量，单位：克")
    private Long weight;

    /**
     * 采购价，单位：分
     */
    @Schema(description = "采购价，单位：分")
    private Long applyPrice;

    /**
     * 商品列表时显示的价格
     */
    @Schema(description = "商品列表时显示的价格")
    private Long price;

    /**
     * 总库存
     */
    @Schema(description = "总库存")
    @NotNull(message = "总库存不能为空")
    private Integer stock;

    /**
     * 总销量
     */
    @Schema(description = "总销量")
    private Integer sellCount;

    /**
     * 需要物流：1-需要 0-不需要
     */
    @Schema(description = "需要物流：1-需要 0-不需要")
    private Integer needLogistics;

    /**
     * 允许超卖：1-允许 0-不允许
     */
    @Schema(description = "允许超卖：1-允许 0-不允许")
    private Integer oversold;

    /**
     * 状态：1-上架 0-下架
     */
    @Schema(description = "状态：1-上架 0-下架")
    private Integer status;

    /**
     * 款式类型：1-多款式 0-单款式
     */
    @Schema(description = "款式类型：1-多款式 0-单款式")
    private Integer attrStyle;

    /**
     * 逻辑删除 1-删除 0-未删除
     */
    @Schema(description = "逻辑删除 1-删除 0-未删除")
    @NotNull(message = "逻辑删除 1-删除 0-未删除不能为空")
    private Integer isDelete;


    @Builder
    public Product(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                   String supplierName, Long storeId, Long categoryId, Long groupId, Long logisticsId, String shortName,
                   String productName, String sellDesc, String productCode, Long weight, Long applyPrice, Long price,
                   Integer stock, Integer sellCount, Integer needLogistics, Integer oversold, Integer status, Integer attrStyle, Integer isDelete) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.supplierName = supplierName;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.groupId = groupId;
        this.logisticsId = logisticsId;
        this.shortName = shortName;
        this.productName = productName;
        this.sellDesc = sellDesc;
        this.productCode = productCode;
        this.weight = weight;
        this.applyPrice = applyPrice;
        this.price = price;
        this.stock = stock;
        this.sellCount = sellCount;
        this.needLogistics = needLogistics;
        this.oversold = oversold;
        this.status = status;
        this.attrStyle = attrStyle;
        this.isDelete = isDelete;
    }

}
