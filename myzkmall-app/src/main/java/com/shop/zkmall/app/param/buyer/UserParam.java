/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserParam", description = "用户请求")
public class UserParam {

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
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPassword;
}
