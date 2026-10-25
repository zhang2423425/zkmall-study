/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_shop 商家信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatformShop", description = "商家信息实体类")
public class CerePlatformShop implements Serializable {

    /**
     * 商家id
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "商家id")
    private Long shopId;

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String shopCode;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String shopBrief;

    /**
     * 店铺账号
     */
    @Schema(description = "店铺账号")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @Schema(description = "店铺密码")
    private String shopPassword;

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
    private String shopAdressProvince;

    /**
     * 店铺地址城市
     */
    @Schema(description = "店铺地址城市")
    private String shopAdressCity;

    /**
     * 店铺地址-详细地址
     */
    @Schema(description = "店铺地址-详细地址")
    private String shopAdressDetail;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String shopLogo;

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
     * 合同状态 1-有效 0-无效
     */
    @Schema(description = "合同状态 1-有效 0-无效")
    private Integer contractState;

    /**
     * 认证状态 1-未认证 2-认证中 3-已认证
     */
    @Schema(description = "认证状态 1-未认证 2-认证中 3-已认证")
    private Integer authenticationState;

    /**
     * 入驻处理状态  0-未处理 1-通过 2-拒绝
     */
    @Schema(description = "入驻处理状态  0-未处理 1-通过 2-拒绝")
    private Integer checkState;

    /**
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 主体类型
     */
    @Schema(description = "主体类型")
    private Integer authenType;

    /**
     * 微信收款码
     */
    @Schema(description = "微信收款码")
    private String wxImage;

    /**
     * 支付宝收款码
     */
    @Schema(description = "支付宝收款码")
    private String ailpayImage;

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

    /**
     * 审核直播间
     */
    @Schema(description = "审核直播间 1-开启 0-关闭")
    private Integer auditLive;

    /**
     * 审核直播间商品
     */
    @Schema(description = "审核直播间商品 1-开启 0-关闭")
    private Integer auditLiveProduct;

    @Schema(description = "是否为人气商家 false-否 true-是")
    private boolean hotFlag = false;

}
