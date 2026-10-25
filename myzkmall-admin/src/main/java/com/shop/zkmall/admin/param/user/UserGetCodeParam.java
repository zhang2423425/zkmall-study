/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取验证码请求实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GetCodeParam", description = "获取验证码参数实体")
public class UserGetCodeParam {

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

    private String code;
}
