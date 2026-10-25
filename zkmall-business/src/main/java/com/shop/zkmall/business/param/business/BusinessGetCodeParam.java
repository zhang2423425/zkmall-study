/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.business;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商家获取验证码请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BusinessGetCodeParam", description = "商家获取验证码请求")
public class BusinessGetCodeParam {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 图形验证码
     */
    private String code;
}
