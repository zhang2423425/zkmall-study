/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 买家信息返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterBuyer", description = "买家信息返回数据实体类")
public class AfterBuyer {

    /**
     * 买家账户
     */
    @Schema(description = "买家账户")
    private String name;

    /**
     * 买家注册时间
     */
    @Schema(description = "买家注册时间")
    private String registerTime;

    /**
     * 买家手机号
     */
    @Schema(description = "买家手机号")
    private String phone;

    /**
     * 买家订单总数
     */
    @Schema(description = "买家订单总数")
    private Integer orders;

    /**
     * 售后单数
     */
    @Schema(description = "售后单数")
    private Integer afters;

    /**
     * 售后成功率
     */
    @Schema(description = "售后成功率")
    private BigDecimal rate;
}
