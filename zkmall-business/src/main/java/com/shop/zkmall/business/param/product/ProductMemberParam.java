/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import com.shop.zkmall.business.page.member.ProductMember;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 设置商品会员价请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ProductMemberParam", description = "设置商品会员价请求")
public class ProductMemberParam {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 商品会员数据明细
     */
    @Schema(description = "商品id")
    private List<ProductMember> members;
}
