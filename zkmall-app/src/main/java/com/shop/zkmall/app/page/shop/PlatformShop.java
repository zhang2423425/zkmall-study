/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.shop;

import com.shop.zkmall.commons.domain.shop.CereShopEnterprise;
import com.shop.zkmall.commons.domain.shop.CereShopIndividualBusinesses;
import com.shop.zkmall.commons.domain.shop.CereShopOtherOrganizations;
import com.shop.zkmall.commons.domain.shop.CereShopPersonal;
import com.shop.zkmall.commons.domain.shop.CereShopReturn;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 店铺信息返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Shop", description = "店铺信息返回数据实体类")
public class PlatformShop {

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
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 店铺简介
     */
    @Schema(description = "店铺简介")
    private String shopBrief;

    /**
     * 店铺手机号
     */
    @Schema(description = "店铺手机号")
    private String shopPhone;

    /**
     * 主体类型
     */
    @Schema(description = "主体类型")
    private Integer authenType;

    /**
     * 认证状态  1-立即认证 2-认证详情 3-立即签约 4-查看详情 5-查看详情
     */
    @Schema(description = "认证状态  1-立即认证 2-认证详情 3-立即签约 4-查看详情 5-查看详情")
    private Integer authenticationState;

    /**
     * 退货地址信息
     */
    @Schema(description = "退货地址信息")
    private CereShopReturn shopReturn;

    /**
     * 个人信息
     */
    @Schema(description = "个人信息")
    private CereShopPersonal personal;

    /**
     * 个体工商户信息
     */
    @Schema(description = "个体工商户信息")
    private CereShopIndividualBusinesses individualBusinesses;

    /**
     * 企业信息
     */
    @Schema(description = "企业信息")
    private CereShopEnterprise enterprise;

    /**
     * 其他组织信息
     */
    @Schema(description = "其他组织信息")
    private CereShopOtherOrganizations otherOrganizations;
}
