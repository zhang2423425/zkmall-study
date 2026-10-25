/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.shop;

import com.shop.zkmall.commons.domain.shop.CereShopPersonal;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入驻申请个人请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CereShopPersonalParam", description = "入驻申请个人请求")
public class CereShopPersonalParam extends CereShopPersonal {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String shopPhone;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    @NotBlank(message = "店铺负责人不能为空")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @Schema(description = "负责人电话")
    @NotBlank(message = "负责人电话不能为空")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    @NotBlank(message = "店铺地址不能为空")
    private String shopAdress;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;
}
