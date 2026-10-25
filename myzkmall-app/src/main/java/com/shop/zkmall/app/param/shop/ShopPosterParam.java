/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 店铺生成海报的请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class ShopPosterParam {

    /**
     * 推广类型 1-推广店铺 2-邀请下级
     */
    @Schema(description = "推广类型 1-推广店铺 2-邀请下级")
    private Integer type;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 访问终端 1-APP 2-微信小程序 3-H5 4-支付宝小程序
     */
    @Schema(description = "访问终端 1-APP 2-微信小程序 3-H5 4-支付宝小程序")
    private Integer terminal;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private Long distributorId;
}
