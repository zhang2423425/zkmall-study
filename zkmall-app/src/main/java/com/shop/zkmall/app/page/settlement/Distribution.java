/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.settlement;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结算查询返回配送数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Distribution", description = "结算查询返回配送数据")
public class Distribution implements Serializable {

    /**
     * 物流方案id
     */
    @Schema(description = "物流方案id")
    private Long logisticsId;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String distributionName;

    /**
     * 运费
     */
    @Schema(description = "运费")
    private BigDecimal distributionPrice;
}
