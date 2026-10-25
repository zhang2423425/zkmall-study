/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 获取验证码请求实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GetCodeParam", description = "获取验证码参数实体")
public class BuyerGetCodeParam {

    /**
     * 电话
     */
    @NotBlank(message = "手机号不能为空")
    @Schema(description = "电话")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "图形验证码")
    private String code;

    @Schema(description = "验证码key值,配合图形验证码使用")
    private String captchaKey;
}
