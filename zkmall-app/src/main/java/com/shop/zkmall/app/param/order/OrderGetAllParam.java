/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.order;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取订单列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OrderGetAllParam", description = "获取订单列表请求")
public class OrderGetAllParam extends PageParam {

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 状态 null-全部 1-代付款 2-待发货 3-待收货 4-待评价 6-待成团
     */
    @Schema(description = "状态 null-全部 1-待付款 2-待发货 3-待收货 4-待评价 6-待成团")
    private String state;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 页大小
     */
    private Integer page;
}
