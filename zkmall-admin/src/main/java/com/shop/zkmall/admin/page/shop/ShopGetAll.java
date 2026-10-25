/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.shop;

import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商家列表
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ShopGetAll", description = "商家列表")
public class ShopGetAll extends CerePlatformShop {

    /**
     * 商家用户id
     */
    @Schema(description = "商家用户id")
    private Long businessUserId;
}
