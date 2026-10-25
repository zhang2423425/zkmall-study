/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺信息返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Shop", description = "店铺信息返回数据实体类")
public class Shop {

    /**
     * 处理id
     */
    @Schema(description = "处理id ")
    private Long checkId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 入驻处理状态  0-未处理 1-通过 2-拒绝
     */
    @Schema(description = "入驻处理状态  0-未处理 1-通过 2-拒绝")
    private Long checkState;

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
     * 店铺手机号
     */
    @Schema(description = "店铺手机号")
    private String shopPhone;

    /**
     * 主体类型 1-个人 2-个体工商户 3-企业 4-其他组织
     */
    @Schema(description = "主体类型 1-个人 2-个体工商户 3-企业 4-其他组织")
    private Integer authenType;

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
     * 申请时间
     */
    @Schema(description = "申请时间")
    private String createTime;

    /**
     * 客服电话
     */
    @Schema(description = "客服电话")
    private String servicePhone;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    /**
     * 证件类型
     */
    private Long cardType;

    /**
     * 证件类型名称
     */
    @Schema(description = "证件类型名称")
    private String cardTypeName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 身份证有效期
     */
    @Schema(description = "身份证有效期")
    private String cardTime;

    /**
     * 身份证正面照
     */
    @Schema(description = "身份证正面照")
    private String cardPositive;

    /**
     * 身份证反面照
     */
    @Schema(description = "身份证反面照")
    private String cardSide;

    /**
     * 手持证件照
     */
    @Schema(description = "手持证件照")
    private String cardHand;

    /**
     * 生效日期
     */
    @Schema(description = "生效日期")
    private String effectiveDate;

    /**
     * 生效时限（年）
     */
    @Schema(description = "生效时限（年）")
    private Integer effectiveYear;

    /**
     * 主体信息-商户名称
     */
    @Schema(description = "主体信息-商户名称")
    private String subjectName;

    /**
     * 主体信息-统一社会信用代码
     */
    @Schema(description = "主体信息-统一社会信用代码")
    private String subjectCode;

    /**
     * 主体信息-地址
     */
    @Schema(description = "主体信息-地址")
    private String subjectAdress;

    /**
     * 主体信息-详细地址
     */
    @Schema(description = "主体信息-详细地址")
    private String adress;

    /**
     * 主体信息-营业期限开始时间
     */
    @Schema(description = "主体信息-营业期限开始时间")
    private String subjectStartTime;

    /**
     * 主体信息-营业期限结束时间
     */
    @Schema(description = "主体信息-营业期限结束时间")
    private String subjectEndTime;

    /**
     * 主体信息-营业执照图片地址
     */
    @Schema(description = "主体信息-营业执照图片地址")
    private List<String> subjectLicense;

    @Schema(description = "店铺logo")
    private String shopLogo;

    @Schema(description = "店铺评分")
    private BigDecimal shopStar;
}
