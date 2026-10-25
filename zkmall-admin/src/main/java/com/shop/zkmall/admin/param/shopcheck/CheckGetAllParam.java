/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.shopcheck;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 获取入驻申请记录列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CheckGetAllParam", description = "获取入驻申请记录列表请求")
public class CheckGetAllParam extends PageParam {

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String shopPhone;

    /**
     * 店铺类型 1-个人 2-个体工商户 3-企业 4-其他组织
     */
    @Schema(description = "店铺类型 1-个人 2-个体工商户 3-企业 4-其他组织")
    private Integer authenType;

    /**
     * 入驻处理状态 0-未处理 1-通过 2-拒绝
     */
    @Schema(description = "入驻处理状态 0-未处理 1-通过 2-拒绝")
    private Integer checkState;

    /**
     * 申请日期数组
     */
    @Schema(description = "申请日期数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if (!EmptyUtils.isEmpty(dates) && dates.size() > 1) {
            this.startTime = dates.get(0);
            this.endTime = dates.get(1);
        }
    }

    /**
     * 申请日期开始时间
     */
    @Schema(description = "申请日期开始时间")
    private String startTime;

    /**
     * 申请日期结束时间
     */
    @Schema(description = "申请日期结束时间")
    private String endTime;
}
