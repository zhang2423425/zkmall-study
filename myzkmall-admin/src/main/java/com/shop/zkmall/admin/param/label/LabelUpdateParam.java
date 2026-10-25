/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.label;

import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 更新标签请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LabelUpdateParam", description = "更新标签请求")
public class LabelUpdateParam {

    /**
     * 客户标签id
     */
    @Schema(description = "客户标签id")
    private Long buyerLabelId;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;

    /**
     * 标签类型 1-手动标签 2-自动标签
     */
    @Schema(description = "标签类型 1-手动标签 2-自动标签")
    private Integer labelType;

    /**
     * 满足条件 1-满足任意一个被选中条件即可  2-必须满足所有被选中条件
     */
    @Schema(description = "满足条件 1-满足任意一个被选中条件即可  2-必须满足所有被选中条件")
    private Integer meetConditions;

    /**
     * 选中条件数组
     */
    @Schema(description = "选中条件数组")
    private List<Integer> conditions;

    public void setConditions(List<Integer> conditions) {
        if (!EmptyUtils.isEmpty(conditions)) {
            if (conditions.contains(1)) {
                this.lastConsumptionTime = 1;
            } else {
                this.lastConsumptionTime = 0;
            }
            if (conditions.contains(2)) {
                this.consumptionFrequency = 1;
            } else {
                this.consumptionFrequency = 0;
            }
            if (conditions.contains(3)) {
                this.consumptionMoney = 1;
            } else {
                this.consumptionMoney = 0;
            }
        }
    }

    /**
     * 是否选中最后消费时间 1-是 0-否
     */
    @Schema(description = "是否选中最后消费时间 1-是 0-否")
    private Integer lastConsumptionTime;

    /**
     * 是否选中累计消费次数  1-是 0-否
     */
    @Schema(description = "是否选中累计消费次数  1-是 0-否")
    private Integer consumptionFrequency;

    /**
     * 是否选中累计交易金额 1-是 0-否
     */
    @Schema(description = "是否选中累计交易金额 1-是 0-否")
    private Integer consumptionMoney;

    /**
     * 最近几天（天）
     */
    @Schema(description = "最近几天（天）")
    private Integer consumptionDay;

    /**
     * 最后消费开始时间
     */
    @Schema(description = "最后消费开始时间")
    private String consumptionStartTime;

    /**
     * 最后消费结束时间
     */
    @Schema(description = "最后消费结束时间")
    private String consumptionEndTime;

    /**
     * 起始次数
     */
    @Schema(description = "起始次数")
    private Integer frequencyStart;

    /**
     * 截止次数
     */
    @Schema(description = "截止次数")
    private Integer frequencyEnd;

    /**
     * 起始金额
     */
    @Schema(description = "起始金额")
    private BigDecimal moneyStart;

    /**
     * 截止金额
     */
    @Schema(description = "截止金额")
    private BigDecimal moneyEnd;

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
