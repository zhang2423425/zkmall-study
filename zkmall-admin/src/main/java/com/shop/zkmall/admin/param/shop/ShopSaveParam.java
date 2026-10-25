/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 添加商家请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopSaveParam", description = "添加商家请求")
public class ShopSaveParam {

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

    /**
     * 店铺账号
     */
    @Schema(description = "店铺账号")
    @NotBlank(message = "店铺账号不能为空")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @Schema(description = "店铺密码")
    @NotBlank(message = "店铺密码不能为空")
    private String shopPassword;

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
