/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.shop;

import com.shop.zkmall.commons.domain.shop.CereShopReturn;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改店铺logo请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopUpdateLogoParam", description = "修改店铺logo请求")
public class ShopUpdateLogoParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String shopLogo;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String shopBrief;

    /**
     * 注册手机号
     */
    @Schema(description = "注册手机号")
    private String shopPhone;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 退货地址信息
     */
    @Schema(description = "店铺地址")
    private CereShopReturn shopReturn;

}
