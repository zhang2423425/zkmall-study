/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.buyer;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取客户列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "BuyerGetAllParam", description = "获取客户列表请求")
public class BuyerGetAllParam extends PageParam {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long labelId;

    /**
     * 最近消费时间日期数组
     */
    @Schema(description = "最近消费时间日期数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if (!EmptyUtils.isEmpty(dates) && dates.size() > 1) {
            this.startTime = dates.get(0) + " 00:00:00";
            this.endTime = dates.get(1) + " 23:59:59";
        }
    }

    /**
     * 累计消费金额最小值
     */
    @Schema(description = "累计消费金额最小值")
    private BigDecimal minMoney;

    /**
     * 累计消费金额最大值
     */
    @Schema(description = "累计消费金额最大值")
    private BigDecimal maxMoney;

    /**
     * 购买次数最小值
     */
    @Schema(description = "购买次数最小值")
    private Integer minBuyers;

    /**
     * 购买次数最大值
     */
    @Schema(description = "购买次数最大值")
    private Integer maxBuyers;

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
