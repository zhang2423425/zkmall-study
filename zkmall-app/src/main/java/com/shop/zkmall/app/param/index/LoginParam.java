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
 * 登录请求参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LoginParam", description = "登录请求参数")
public class LoginParam {

    /**
     * APP端类型 1-注册 2-登录
     */
    @Schema(description = "APP端类型 1-注册 2-登录")
    private Integer type;

    /**
     * 微信code 或 支付宝auth_code
     */
    @Schema(description = "微信code 或 支付宝auth_code")
    private String code;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String verificationCode;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 访问终端 0-未知 1-APP 2-微信小程序 3-H5 4-支付宝小程序 5-PC
     */
    @Schema(description = "访问终端 0-未知 1-APP 2-微信小程序 3-H5 4-支付宝小程序 5-PC")
    private Integer terminal;

    /**
     * 操作系统 1-Android 2-IOS
     */
    @Schema(description = "操作系统 1-Android 2-IOS")
    private Integer system;

    /**
     * 所在地区
     */
    @Schema(description = "所在地区")
    private String city;

    /**
     * 微信头像
     */
    @Schema(description = "微信头像")
    private String headImage;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 渠道id
     */
    private String channelCode;
}
