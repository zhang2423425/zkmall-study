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
 * 手机号授权
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LoginPhoneParam", description = "手机号授权")
public class LoginPhoneParam {

    /**
     * 微信openID
     */
    @Schema(description = "微信openID")
    private String wechatOpenId;

    /**
     * 加密秘钥
     */
    @Schema(description = "加密秘钥")
    private String sessionKey;

    /**
     * 加密数据
     */
    @Schema(description = "加密数据")
    private String encryptedData;

    /**
     * 加密算法初始向量
     */
    @Schema(description = "加密算法初始向量")
    private String iv;

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
     * 渠道编码
     */
    private String channelCode;
}
