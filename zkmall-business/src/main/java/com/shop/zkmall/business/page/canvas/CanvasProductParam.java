/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.canvas;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 画布商品数据请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CanvasProductParam", description = "画布商品数据请求")
public class CanvasProductParam extends PageParam {

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @Schema(description = "是否上架 1-上架 0-不上架")
    private String shelveState;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long classifyId;

    @Schema(description = "分类层级")
    private Integer classifyLevel;

    /**
     * 商品id数组
     */
    @Schema(description = "商品id数组")
    private List<Long> ids;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 商家优惠券id
     */
    @Schema(description = "商家优惠券id")
    private Long shopCouponId;
}
