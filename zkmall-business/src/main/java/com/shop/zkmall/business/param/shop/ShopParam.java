/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.shop;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺通用请求参数
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ShopIdParam", description = "店铺通用请求参数")
public class ShopParam extends PageParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 分销员搜索字段(分销员查询需要)
     */
    @Schema(description = "分销员搜索字段")
    private String search;

    /**
     * 方案名称(物流方案查询需要)
     */
    @Schema(description = "方案名称")
    private String logisticsName;

    /**
     * 推广标题(推广查询需要)
     */
    @Schema(description = "推广标题")
    private String title;
}
