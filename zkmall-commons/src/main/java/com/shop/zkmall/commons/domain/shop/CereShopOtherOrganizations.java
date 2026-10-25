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
 * cere_shop_other_organizations 店铺其他组织认证信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereShopOtherOrganizations", description = "店铺其他组织认证信息实体类")
public class CereShopOtherOrganizations implements Serializable {
    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 组织名称
     */
    @Schema(description = "组织名称")
    @NotBlank(message = "组织名称不能为空")
    private String otherName;

    /**
     * 组织机构代码
     */
    @Schema(description = "组织机构代码")
    @NotBlank(message = "组织机构代码不能为空")
    private String otherCode;

    /**
     * 地址  省-市-区
     */
    @Schema(description = "地址  省-市-区")
    @NotBlank(message = "地址不能为空")
    private String otherRegion;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String otherAdress;

    /**
     * 营业期限开始时间
     */
    @Schema(description = "营业期限开始时间")
    @NotBlank(message = "营业期限开始时间不能为空")
    private String otherStartTime;

    /**
     * 营业期限结束时间
     */
    @Schema(description = "营业期限结束时间")
    @NotBlank(message = "营业期限结束时间不能为空")
    private String otherEndTime;

    /**
     * 机构证件或其他证明材料图片地址（多个以逗号隔开）
     */
    @Schema(description = "机构证件或其他证明材料图片地址（多个以逗号隔开）")
    @NotBlank(message = "机构证件或其他证明材料图片不能为空")
    private String otherLicense;

    /**
     * 负责人姓名
     */
    @Schema(description = "负责人姓名")
    @NotBlank(message = "负责人姓名不能为空")
    private String otherOperator;

    /**
     * 证件类型 （取数据字典）
     */
    @Schema(description = "证件类型 （取数据字典）")
    @NotNull(message = "证件类型不能为空")
    private Long otherCardType;

    /**
     * 负责人身份证号码
     */
    @Schema(description = "负责人身份证号码")
    @NotBlank(message = "负责人身份证号码不能为空")
    private String otherIdCard;

    /**
     * 证件有效开始时间
     */
    @Schema(description = "证件有效开始时间")
    @NotBlank(message = "证件有效开始时间不能为空")
    private String otherCardStartTime;

    /**
     * 证件有效结束时间
     */
    @Schema(description = "证件有效结束时间")
    @NotBlank(message = "证件有效结束时间不能为空")
    private String otherCardEndTime;

    /**
     * 身份证正面照
     */
    @Schema(description = "身份证正面照")
    @NotBlank(message = "身份证正面照不能为空")
    private String otherCardPositive;

    /**
     * 身份证反面照
     */
    @Schema(description = "身份证反面照")
    @NotBlank(message = "身份证反面照不能为空")
    private String otherCardSide;

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
