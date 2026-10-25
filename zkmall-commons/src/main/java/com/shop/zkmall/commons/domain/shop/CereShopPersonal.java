/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_personal 店铺个人认证信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopPersonal", description = "店铺个人认证信息实体类")
public class CereShopPersonal implements Serializable {
    /**
     * 关联店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 个人姓名
     */
    @Schema(description = "个人姓名")
    private String personalName;

    /**
     * 证件类型 （取数据字典）
     */
    @Schema(description = "证件类型 （取数据字典）")
    @NotNull(message = "证件类型不能为空")
    private Long personalCardType;

    /**
     * 法人身份证号码
     */
    @Schema(description = "法人身份证号码")
    @NotBlank(message = "法人身份证号码不能为空")
    private String personalIdCard;

    /**
     * 证件有效开始时间
     */
    @Schema(description = "证件有效开始时间")
    @NotBlank(message = "证件有效开始时间不能为空")
    private String personalCardStartTime;

    /**
     * 证件有效结束时间
     */
    @Schema(description = "证件有效结束时间")
    @NotBlank(message = "证件有效结束时间不能为空")
    private String personalCardEndTime;

    /**
     * 法人身份证正面照
     */
    @Schema(description = "法人身份证正面照")
    @NotBlank(message = "法人身份证正面照不能为空")
    private String personalCardPositive;

    /**
     * 法人身份证反面照
     */
    @Schema(description = "法人身份证反面照")
    @NotBlank(message = "法人身份证反面照不能为空")
    private String personalCardSide;

    /**
     * 手持证件照
     */
    @Schema(description = "手持证件照")
    @NotBlank(message = "手持证件照不能为空")
    private String personalCardHand;

    /**
     * 管理员手机号
     */
    @Schema(description = "管理员手机号")
    private String administratorsPhone;

    /**
     * 管理员邮箱
     */
    @Schema(description = "管理员邮箱")
    private String administratorsEmail;

    /**
     * 开户银行（取数据字典）
     */
    @Schema(description = "开户银行（取数据字典）")
    private Long accountOpenBank;

    /**
     * 开户银行地区   省-市
     */
    @Schema(description = "开户银行地区   省-市")
    private String accountBankRegion;

    /**
     * 银行账户
     */
    @Schema(description = "银行账户")
    private String accountBankCard;

    /**
     * 商户简称
     */
    @Schema(description = "商户简称")
    private String shopAbbreviation;

    /**
     * 客服电话
     */
    @Schema(description = "客服电话")
    @NotBlank(message = "客服电话不能为空")
    private String servicePhone;

    /**
     * 提供服务（取数据字典）
     */
    @Schema(description = "提供服务（取数据字典）")
    private Long serviceProviding;

    /**
     * 店铺首页截图
     */
    @Schema(description = "店铺首页截图")
    private String shopIndexImage;

    /**
     * 店铺管理后台截图
     */
    @Schema(description = "店铺管理后台截图")
    private String shopBackstageImage;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

}
