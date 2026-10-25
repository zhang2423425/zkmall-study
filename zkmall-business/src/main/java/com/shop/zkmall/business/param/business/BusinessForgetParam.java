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
 * 商家忘记密码请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BusinessForgetParam", description = "商家忘记密码请求")
public class BusinessForgetParam {

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
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPassword;

    /**
     * RSA解密
     *
     * @return 解密对象
     */
    public BusinessForgetParam decrypt() throws Exception {
        BusinessForgetParam res = new BusinessForgetParam();
        res.setUsername(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.username));
        res.setPassword(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.password));
        res.setCode(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.code));
        res.setNewPassword(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.newPassword));
        return res;
    }
}
