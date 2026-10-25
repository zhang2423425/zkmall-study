/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.logistics;

import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 添加物流方案请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LogistSaveParam", description = "添加物流方案请求")
public class LogistSaveParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 方案名称
     */
    @Schema(description = "方案名称")
    @NotBlank(message = "方案名称不能为空")
    private String logisticsName;

    /**
     * 计费方式 1-按件数 2-按重量 3-全国包邮
     */
    @Schema(description = "计费方式 1-按件数 2-按重量 3-全国包邮")
    private Integer chargeType;

    public void setChargeType(Integer chargeType) {
        if (EmptyUtils.isEmpty(chargeType)) {
            //默认全国包邮
            this.chargeType = 3;
        } else {
            this.chargeType = chargeType;
        }
    }

    /**
     * 计费明旭
     */
    @Schema(description = "计费明旭")
    private List<ChargeParam> charges;
}
