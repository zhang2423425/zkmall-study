/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.shop;

import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商家用户返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "PlatformBusiness", description = "商家用户返回数据实体类")
public class PlatformBusiness extends CerePlatformBusiness {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺入住处理状态 0-未处理 1-通过 2-拒绝
     */
    @Schema(description = "店铺入住处理状态 0-未处理 1-通过 2-拒绝")
    private Integer checkState;
}
