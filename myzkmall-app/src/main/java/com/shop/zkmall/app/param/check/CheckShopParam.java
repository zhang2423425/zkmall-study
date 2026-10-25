/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.check;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询是否申请请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CheckShopParam", description = "查询是否申请请求")
public class CheckShopParam {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String shopPhone;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;
}
