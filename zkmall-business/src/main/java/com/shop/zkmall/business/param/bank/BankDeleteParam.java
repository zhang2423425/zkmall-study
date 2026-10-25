/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 解除绑定请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BankDeleteParam", description = "解除绑定请求")
public class BankDeleteParam {

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
}
