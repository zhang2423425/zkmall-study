/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.risk;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 风控规则表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("cere_risk_rule")
@ApiModel(value = "CereRiskRule", description = "风控规则表")
@AllArgsConstructor
public class CereRiskRule {

    @Schema(description = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 规则名称
     */
    @Schema(description = "规则名称")
    @NotEmpty(message = "规则名称不能为空")
    @Length(max = 100, message = "规则名称长度不能超过100")
    @TableField(value = "rule_name", condition = LIKE)
    private String ruleName;

    /**
     * 状态 0未发布 1已发布
     */
    @Schema(description = "状态 0未发布 1已发布")
    @NotNull(message = "状态 0未发布 1已发布不能为空")
    @TableField("status")
    private Integer status;

    /**
     * 触发条件 1：满足任一一项  2：满足所有
     */
    @Schema(description = "触发条件 1：满足任一一项  2：满足所有")
    @NotNull(message = "触发条件 1：满足任一一项  2：满足所有不能为空")
    @TableField("rule_type")
    private Integer ruleType;

    /**
     * 创建订单数限制规则，json保存参数
     */
    @Schema(description = "创建订单数限制规则，json保存参数")
    @Length(max = 500, message = "创建订单数限制规则，json保存参数长度不能超过500")
    @TableField(value = "rule_place_order_limit", condition = LIKE)
    private String rulePlaceOrderLimit;

    /**
     * 待付款订单数限制规则，json保存参数
     */
    @Schema(description = "待付款订单数限制规则，json保存参数")
    @Length(max = 500, message = "待付款订单数限制规则，json保存参数长度不能超过500")
    @TableField(value = "rule_wait_pay_limit", condition = LIKE)
    private String ruleWaitPayLimit;

    /**
     * 单sku商品采购数限制规则，json保存参数
     */
    @Schema(description = "单sku商品采购数限制规则，json保存参数")
    @Length(max = 500, message = "单sku商品采购数限制规则，json保存参数长度不能超过500")
    @TableField(value = "rule_sku_limit", condition = LIKE)
    private String ruleSkuLimit;

    /**
     * 单sku商品采购数限制规则，json保存参数
     */
    @Schema(description = "单sku商品采购数限制规则，json保存参数")
    @Length(max = 500, message = "单sku商品采购数限制规则，json保存参数长度不能超过500")
    @TableField(value = "rule_post_sale_limit", condition = LIKE)
    private String rulePostSaleLimit;

    /**
     * 售后规则开关  0关闭 1开启
     */
    @Schema(description = "售后规则开关  0关闭 1开启")
    @NotNull(message = "售后规则开关  0关闭 1开启不能为空")
    @TableField("rule_switch_post_sale")
    private Integer ruleSwitchPostSale;

    /**
     * 单sku商品规则开关 0关闭 1开启
     */
    @Schema(description = "单sku商品规则开关 0关闭 1开启")
    @NotNull(message = "单sku商品规则开关 0关闭 1开启不能为空")
    @TableField("rule_switch_sku")
    private Integer ruleSwitchSku;

    /**
     * 下单规则开关 0关闭 1开启
     */
    @Schema(description = "下单规则开关 0关闭 1开启")
    @NotNull(message = "下单规则开关 0关闭 1开启不能为空")
    @TableField("rule_switch_place_order")
    private Integer ruleSwitchPlaceOrder;

    /**
     * 待付款规则开关 0关闭 1开启
     */
    @Schema(description = "待付款规则开关 0关闭 1开启")
    @NotNull(message = "待付款规则开关 0关闭 1开启不能为空")
    @TableField("rule_switch_wait_pay")
    private Integer ruleSwitchWaitPay;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private String updateTime;

}
