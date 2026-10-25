/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.user;

import com.shop.zkmall.commons.config.RsaProperties;
import com.shop.zkmall.commons.utils.RsaUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新管理员手机号请求实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserUpdatePhoneParam", description = "更新管理员手机号请求实体")
public class UserUpdatePhoneParam {

    /**
     * 旧手机号
     */
    @Schema(description = "旧手机号")
    private String phone;

    /**
     * 新手机号
     */
    @Schema(description = "新手机号")
    private String newPhone;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    public UserUpdatePhoneParam decrypt() throws Exception {
        UserUpdatePhoneParam res = new UserUpdatePhoneParam();
        res.setPhone(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.phone));
        res.setNewPhone(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.newPhone));
        res.setCode(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.code));
        return res;
    }
}
