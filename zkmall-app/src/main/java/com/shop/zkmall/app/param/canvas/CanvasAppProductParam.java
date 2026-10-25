/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.canvas;

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
@ApiModel(value = "CanvasAppProductParam", description = "画布商品数据请求")
public class CanvasAppProductParam extends PageParam {

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
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 活动类型 1-拼团活动 2-秒杀活动 3-限时折扣活动
     */
    @Schema(description = "活动类型 1-拼团活动 2-秒杀活动 3-限时折扣活动")
    private Integer type;

    /**
     * 商品id数组
     */
    @Schema(description = "商品id数组")
    private List<Long> ids;

    /**
     * 会员等级id
     */
    @Schema(description = "会员等级id")
    private Long memberLevelId;

    /**
     * 推荐类型
     */
    @Schema(description = "推荐类型 1-热门商品 2-好物严选 3-特卖榜单")
    private Integer recommendType;

    @Schema(description = "用户id")
    private Long buyerUserId;
}
