/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.shop;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取商家列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ShopGetAllParam", description = "获取商家列表请求")
public class ShopGetAllParam extends PageParam {

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @Schema(description = "店铺编码")
    private String shopCode;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    private String chargePersonName;

    /**
     * 合同状态 1-有效 0-无效
     */
    @Schema(description = "合同状态 1-有效 0-无效")
    private Integer contractState;
}
