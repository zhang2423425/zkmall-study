/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.after;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 获取售后单列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "AfterGetAllParam", description = "获取售后单列表请求")
public class AfterGetAllParam extends PageParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 搜索类型  1-订单ID 2-买家账户 3-收件人姓名 4-收件人手机号 5-商品ID
     */
    @Schema(description = "搜索类型  1-订单ID 2-买家账户 3-收件人姓名 4-收件人手机号 5-商品ID")
    private Integer searchType;

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 订单状态 1-待付款 2-待发货 3-已发货 4-已完成 5-已关闭
     */
    @Schema(description = "订单状态 1-待付款 2-待发货 3-已发货 4-已完成 5-已关闭")
    private Integer state;

    /**
     * 售后状态 1-售后中 2-售后成功 3-售后关闭
     */
    @Schema(description = "售后状态 1-售后中 2-售后成功 3-售后关闭")
    private Integer afterState;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    @Schema(description = "售后类型  1-仅退款  2-退货退款")
    private Integer afterType;
    /**
     * 售后状态集合  1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     * 1、2、3、5、6、7、8、10 售后中
     * 4 售后成功
     * 9 售后关闭
     */
    @Schema(description = "售后状态")
    private List<Integer> afterStateList;
    /**
     * 列表累心 1-待商家处理 2-待商家收货
     */
    @Schema(description = "列表累心 1-待商家处理 2-待商家收货")
    private Integer type;

    /**
     * 查询是否需要关联cere_after_product表
     */
    public boolean getNeedJoinAfterProduct() {
        return Objects.equals(5, searchType) && StringUtils.isNotBlank(search);
    }

    /**
     * 查询是否需要关联cere_shop_order
     */
    public boolean getNeedJoinShopOrder() {
        return state != null || (StringUtils.isNotBlank(search) && Arrays.asList(2, 3, 4).contains(searchType));
    }
}
