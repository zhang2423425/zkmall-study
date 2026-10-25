/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物流明细返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ChargeParam", description = "物流明细请求参数")
public class Charge {

    /**
     * 配送区域数组  省-市-区
     */
    @Schema(description = "配送区域  省-市-区")
    private List<String> regions;

    /**
     * 配送区域
     */
    private String region;

    /**
     * 重量 多少KG/件
     */
    @Schema(description = "重量 多少KG/件")
    private BigDecimal weight;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 续重 多少KG/件
     */
    @Schema(description = "续重 多少KG/件")
    private BigDecimal secondWeight;

    /**
     * 续重价格
     */
    @Schema(description = "续重价格")
    private BigDecimal secondPrice;
}
