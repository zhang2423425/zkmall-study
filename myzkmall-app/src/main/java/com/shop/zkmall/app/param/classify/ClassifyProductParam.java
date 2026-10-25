/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.classify;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询分类商品请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ClassifyProductParam", description = "查询分类商品请求")
public class ClassifyProductParam extends PageParam {

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
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 用户id
     */
    private Long buyerUserId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;
}
