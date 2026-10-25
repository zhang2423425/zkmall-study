/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.index;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
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
public class SearchParam extends PageParam {

    /**
     * 搜索内容
     */
    @Schema(description = "搜索内容")
    private String search;

    public void setSearch(String search) {
        if (!EmptyUtils.isEmpty(search) && search.length() > 40) {
            this.search = search.substring(0, 40);
        } else {
            this.search = search;
        }
    }

    /**
     * 搜索id
     */
    @Schema(description = "搜索id")
    private Long searchId;

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
     * 最小价格
     */
    @Schema(description = "最小价格")
    private BigDecimal minMoney;

    /**
     * 最大价格
     */
    @Schema(description = "最大价格")
    private BigDecimal maxMoney;

    @Schema(description = "会员等级id")
    private Long memberLevelId;

    @Schema(description = "分类id")
    private Long classifyId;

    @Schema(description = "分类层级")
    private Integer classifyLevel;
}
