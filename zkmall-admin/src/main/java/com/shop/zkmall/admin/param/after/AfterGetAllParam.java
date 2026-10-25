/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.after;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 获取售后列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "AfterGetAllParam", description = "获取售后列表请求")
public class AfterGetAllParam extends PageParam {

    /**
     * 处理状态 0-待处理 1-已处理
     */
    @Schema(description = "处理状态 0-待处理 1-已处理")
    private Integer state;

    /**
     * 店铺名称或编号
     */
    @Schema(description = "店铺名称或编号")
    private String shopName;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 申请时间数组
     */
    @Schema(description = "申请时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if (!EmptyUtils.isEmpty(dates) && dates.size() > 1) {
            this.startTime = dates.get(0) + " 00:00:00";
            this.endTime = dates.get(1) + " 23:59:59";
        }
    }

    /**
     * 申请开始时间
     */
    @Schema(description = "申请开始时间")
    private String startTime;

    /**
     * 申请结束时间
     */
    @Schema(description = "申请结束时间")
    private String endTime;
}
