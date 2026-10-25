/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.express;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 快递100物流轨迹实体封装
 *
 * @author zkmall zkmallvip@163.com
 */
@ApiModel("快递轨迹对象")
@Data
public class ShippingDTO {

    @Schema(description = "快递公司编码")
    private String shipperCode;

    @Schema(description = "快递单号")
    private String logisticCode;

    @Schema(description = "快递轨迹列表")
    private List<ShippingTrace> traces;

}

