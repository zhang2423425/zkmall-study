/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.order;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 订单列表查询请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OrderGetAllParam", description = "订单列表查询请求")
public class OrderGetAllParam extends PageParam {

    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    private String shopName;

    /**
     * 订单ID数组
     */
    @Schema(description = "商户名称")
    private List<Long> orderIds;
    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderFormid;

    /**
     * 搜索内容 结合 searchType使用
     */
    @Schema(description = "搜索内容")
    private String search;

    /**
     * 搜索类型 1-订单id 2-买家账户 3-收件人姓名 4-收件人手机号
     */
    @Schema(description = "搜索类型 1-订单id 2-买家账户 3-收件人姓名 4-收件人手机号")
    private Integer searchType;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团
     */
    @Schema(description = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团")
    private Integer state;

    //    @Schema(description = "售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭")
//    private Integer afterState;

    /**
     * 下单时间数组
     */
    @Schema(description = "下单时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if (!EmptyUtils.isEmpty(dates) && dates.size() > 1) {
            this.startTime = dates.get(0);
            this.endTime = dates.get(1);
        }
    }

    /**
     * 发送开始时间
     */
    @Schema(description = "发送开始时间")
    private String startTime;

    /**
     * 发送结束时间
     */
    @Schema(description = "发送结束时间")
    private String endTime;
}
