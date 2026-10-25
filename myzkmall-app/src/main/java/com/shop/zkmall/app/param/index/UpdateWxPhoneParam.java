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
 * 手机号合并微信请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UpdateWxPhoneParam", description = "手机号合并微信请求")
public class UpdateWxPhoneParam {

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String verificationCode;

    /**
     * 微信openid
     */
    @Schema(description = "微信openid")
    private String wechatOpenId;

    /**
     * 绑定手机号
     */
    @Schema(description = "绑定手机号")
    private String phone;

    /**
     * 微信头像
     */
    @Schema(description = "微信头像")
    private String headImage;

    /**
     * ip
     */
    private String ip;

    /**
     * 渠道编码
     */
    private String channelCode;
}
