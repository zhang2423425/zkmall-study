/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_return 店铺退货地址信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopReturn", description = "店铺退货地址信息实体类")
public class CereShopReturn implements Serializable {
    /**
     * 关联店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 退货地址
     */
    @Schema(description = "退货地址")
    private String returnAdress;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String returnPerson;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String returnPhone;

}
