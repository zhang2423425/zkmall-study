/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.member;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品规格会员价数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "MemberPrice", description = "商品规格会员价数据")
public class MemberPrice {

    /**
     * 会员等级id
     */
    @Schema(description = "会员等级id")
    private Long memberLevelId;

    /**
     * 会员等级名称
     */
    @Schema(description = "会员等级名称")
    private String memberLevelName;

    /**
     * 优惠方式   1-折扣 2-指定价格
     */
    @Schema(description = "优惠方式   1-折扣 2-指定价格")
    private Integer mode;

    /**
     * 多少元/几折
     */
    @Schema(description = "多少元/几折")
    private BigDecimal price;
}
