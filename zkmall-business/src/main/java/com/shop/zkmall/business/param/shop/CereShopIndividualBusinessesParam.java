/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.shop;

import com.shop.zkmall.commons.domain.shop.CereShopIndividualBusinesses;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入驻申请个体工商户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CereShopIndividualBusinessesParam", description = "入驻申请个体工商户请求")
public class CereShopIndividualBusinessesParam extends CereShopIndividualBusinesses {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String shopPhone;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @Schema(description = "负责人电话")
    private String chargePersonPhone;

    /**
     * 店铺地址省份
     */
    @Schema(description = "店铺地址省份")
    @NotBlank(message = "店铺地址身份不能为空")
    private String shopAdressProvince;

    /**
     * 店铺地址城市
     */
    @Schema(description = "店铺地址城市")
    @NotBlank(message = "店铺地址城市不能为空")
    private String shopAdressCity;

    /**
     * 店铺地址-详细地址
     */
    @Schema(description = "店铺地址-详细地址")
    private String shopAdressDetail;

    /**
     * 主体类型
     */
    @Schema(description = "主体类型")
    private Integer authenType;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;
}
