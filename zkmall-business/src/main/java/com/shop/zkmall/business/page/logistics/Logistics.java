/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 物流方案返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Logistics", description = "物流方案返回数据实体类")
public class Logistics {

    /**
     * 物流方案id
     */
    @Schema(description = "物流方案id")
    private Long logisticsId;

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
     * 配送范围
     */
    @Schema(description = "配送范围")
    private String regions;

    /**
     * 计费明细
     */
    @Schema(description = "计费明细")
    private List<Charge> charges;

    /**
     * 计费城市数组
     */
    private List<String> cities;
}
