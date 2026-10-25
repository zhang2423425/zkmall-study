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
 * cere_shop_bank 店铺收款账户信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopBank", description = "店铺收款账户信息实体类")
public class CereShopBank implements Serializable {
    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 持卡人姓名
     */
    @Schema(description = "持卡人姓名")
    private String cardName;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    private String cardNumber;

    /**
     * 所属银行（取数据字典）
     */
    @Schema(description = "所属银行（取数据字典）")
    private Long bank;

}
