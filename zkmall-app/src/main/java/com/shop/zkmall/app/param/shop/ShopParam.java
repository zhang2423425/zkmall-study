/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.shop;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品搜索请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "SearchParam", description = "商品搜索请求")
public class ShopParam extends PageParam {

    /**
     * 搜索字段
     */
    @Schema(description = "搜索字段")
    private String search;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 是否新品 1-是 0-否
     */
    private Integer ifNew;

    /**
     * 价格排序条件 1-升序 2-降序
     */
    @Schema(description = "价格排序条件 1-升序 2-降序")
    private Integer type;

    /**
     * 销量排序条件 1-升序 2-降序
     */
    @Schema(description = "销量排序条件 1-升序 2-降序")
    private Integer volume;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long classifyId;

    @Schema(description = "分类层级")
    private Integer classifyLevel;

    /**
     * 分组id
     */
    @Schema(description = "分组id")
    private Long groupId;

    /**
     * 最小价格
     */
    @Schema(description = "最小价格")
    private BigDecimal minMoney;

    /**
     * 最大价格
     */
    @Schema(description = "最大价格")
    private BigDecimal maxMoney;

    /**
     * 商品状态
     */
    @Schema(description = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
    private Integer shelveState;

    @Schema(description = "会员等级id")
    private Long memberLevelId;
}
