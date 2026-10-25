/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.express;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 快递100物流轨迹明细实体
 *
 * @author zkmall zkmallvip@163.com
 */
@ApiModel("快递轨迹")
@Data
public class ShippingTrace implements Serializable {


    @Schema(description = "到达站点")
    private String acceptStation;

    @Schema(description = "到达时间")
    private String acceptTime;

}
