/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.shop;

import com.shop.zkmall.commons.domain.shop.CereShopBank;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺收款账户数据
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ShopBankDetail", description = "店铺收款账户数据")
public class ShopBankDetail extends CereShopBank {

    /**
     * 绑定手机号
     */
    @Schema(description = "绑定手机号")
    private String phone;
}
