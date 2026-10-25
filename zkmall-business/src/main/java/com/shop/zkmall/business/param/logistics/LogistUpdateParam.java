/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新物流方案请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LogistUpdateParam", description = "更新物流方案请求")
public class LogistUpdateParam {

    /**
     * 物流方案id
     */
    @Schema(description = "物流方案id")
    private Long logisticsId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 方案名称
     */
    @Schema(description = "方案名称")
    private String logisticsName;

    /**
     * 计费方式 1-按件数 2-按重量 3-全国包邮
     */
    @Schema(description = "计费方式 1-按件数 2-按重量 3-全国包邮")
    private Integer chargeType;

    /**
     * 计费明旭
     */
    @Schema(description = "计费明旭")
    private List<ChargeParam> charges;
}
