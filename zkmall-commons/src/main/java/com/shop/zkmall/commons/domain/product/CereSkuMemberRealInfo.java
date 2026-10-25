/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * 实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
//@NoArgsConstructor
//@ToString(callSuper = true)
@ApiModel(value = "CereSkuMemberRealInfo")
//@AllArgsConstructor
public class CereSkuMemberRealInfo {

    /**
     * skuId
     */
    @Schema(description = "skuId")
    private Long skuId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 会员等级id
     */
    @Schema(description = "会员等级id")
    private Long memberLevelId;

    /**
     * 参考IntegerEnum.ACTIVITY_TYPE_XXX
     */
    @Schema(description = "参考IntegerEnum.ACTIVITY_TYPE_XXX")
    @NotNull(message = "参考IntegerEnum.ACTIVITY_TYPE_XXX不能为空")
    @TableField("activity_type")
    private Integer activityType;


    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @NotNull(message = "活动id不能为空")
    @TableField("activity_id")
    private Long activityId;


    /**
     * 当前价格
     */
    @Schema(description = "当前价格")
    @NotNull(message = "当前价格不能为空")
    @TableField("cur_price")
    private BigDecimal curPrice;

    /**
     * 当前原价
     */
    @Schema(description = "当前原价")
    @NotNull(message = "当前原价不能为空")
    @TableField("cur_original_price")
    private BigDecimal curOriginalPrice;


    /**
     * 最小价格
     */
    @Schema(description = "最小价格")
    @NotNull(message = "最小价格不能为空")
    @TableField("min_price")
    private BigDecimal minPrice;


    /**
     * 最大价格
     */
    @Schema(description = "最大价格")
    @NotNull(message = "最大价格不能为空")
    @TableField("max_price")
    private BigDecimal maxPrice;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    @NotNull(message = "限购数量不能为空")
    @TableField("limit_number")
    private Integer limitNumber;

    /**
     * 活动预热   1-停用  2-启用
     */
    @Schema(description = "活动预热   1-停用  2-启用")
    @NotNull(message = "活动预热不能为空")
    @TableField("if_enable")
    private Integer ifEnable;

    /**
     * 预热几小时前
     */
    @Schema(description = "预热几小时前")
    @NotNull(message = "预热几小时前不能为空")
    @TableField("enable_time")
    private Integer enableTime;

    /**
     * 是否可以叠加优惠券 1-是 0-否
     */
    @Schema(description = "是否可以叠加优惠券 1-是 0-否")
    @NotNull(message = "是否可以叠加优惠券不能为空")
    @TableField("if_add")
    private Integer ifAdd;

    /**
     * 成团人数
     */
    @Schema(description = "成团人数")
    @NotNull(message = "成团人数")
    @TableField("person")
    private Integer person;

    /**
     * 商品维度的下单人数 只要下过单的都算
     */
    @Schema(description = "下单人数")
    @NotNull(message = "下单人数")
    @TableField("sales_user_count")
    private Integer salesUserCount;

    /**
     * 当前销量,根据activityType不同而有变化
     */
    @Schema(description = "当前销量,根据activityType不同而有变化")
    @NotNull(message = "当前销量,根据activityType不同而有变化不能为空")
    @TableField("sales_volume")
    private Integer salesVolume;


    /**
     * 该sku总销量
     */
    @Schema(description = "该sku总销量")
    @NotNull(message = "该sku总销量不能为空")
    @TableField("sku_sales_volume")
    private Integer skuSalesVolume;


    /**
     * 商品总销量
     */
    @Schema(description = "商品总销量")
    @NotNull(message = "商品总销量不能为空")
    @TableField("product_sales_volume")
    private Integer productSalesVolume;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    @Length(max = 20, message = "活动开始时间长度不能超过20")
    @TableField(value = "start_time", condition = LIKE)
    private String startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    @Length(max = 20, message = "活动结束时间长度不能超过20")
    @TableField(value = "end_time", condition = LIKE)
    private String endTime;

    /**
     * 活动状态 对于平台端 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束; 对于商家端  0-未开始 1-进行中 2-已结束
     */
    @Schema(description = "活动状态")
    @NotNull(message = "活动状态不能为空")
    @TableField("state")
    private Integer state;

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
