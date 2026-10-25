/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_withdrawal 商家提现申请实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopWithdrawal", description = "商家提现申请实体类")
public class CereShopWithdrawal implements Serializable {

    /**
     * 提现申请id
     */
    @Schema(description = "提现申请id")
    @TableId(type = IdType.AUTO)
    private Long withdrawalId;

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @Schema(description = "店铺编码")
    private String shopCode;

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    private String bankCard;

    /**
     * 收款人姓名
     */
    @Schema(description = "收款人姓名")
    private String collectionName;

    /**
     * 提现金额
     */
    @Schema(description = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 处理状态  0-待处理 1-已处理
     */
    @Schema(description = "处理状态  0-待处理 1-已处理")
    private Integer state;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private String applyTime;

    /**
     * 处理时间
     */
    @Schema(description = "处理时间")
    private String handleTime;

}
