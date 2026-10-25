/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.business;

import com.shop.zkmall.commons.config.RsaProperties;
import com.shop.zkmall.commons.utils.RsaUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商家登录请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BusinessLoginParam", description = "商家登录请求")
public class BusinessLoginParam {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    /**
     * 是否7日内自动登录
     */
    @Schema(description = "是否7日内自动登录")
    private Boolean rememberMe;

    /**
     * RSA解密
     *
     * @return 解密对象
     */
    public BusinessLoginParam decrypt() throws Exception {
        BusinessLoginParam res = new BusinessLoginParam();
        res.setUsername(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.username));
        res.setPassword(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.password));
        res.setCode(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.code));
        res.setRememberMe(this.rememberMe);
        return res;
    }
}
