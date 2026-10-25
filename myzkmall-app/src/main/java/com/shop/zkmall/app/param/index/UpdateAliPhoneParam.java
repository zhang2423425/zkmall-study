/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 手机号合并支付宝请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UpdateAliPhoneParam", description = "手机号合并支付宝请求")
public class UpdateAliPhoneParam {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long buyerUserId;

    /**
     * 绑定手机号
     */
    @Schema(description = "绑定手机号")
    private String phone;

    /**
     * 手机号是否加密
     */
    @Schema(description = "手机号是否加密")
    private Boolean encrypted;

    /**
     * ip
     */
    private String ip;
}
