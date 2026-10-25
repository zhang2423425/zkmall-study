/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.login;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUser", description = "登录返回数据")
public class BuyerUser {
    /**
     * 微信昵称
     */
    @Schema(description = "微信昵称")
    private String wechatName;

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
     * 请求token
     */
    @Schema(description = "请求token")
    private String token;

    /**
     * 刷新token
     */
    @Schema(description = "刷新token")
    private String refreshToken;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 是否启用状态 1-是 0-否
     */
    @Schema(description = "是否启用状态 1-是 0-否")
    private Integer state;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    @Schema(description = "是否加入黑名单 1-是 0-否")
    private Integer ifBlack;

    /**
     * 微信加密秘钥
     */
    @Schema(description = "微信加密秘钥")
    private String sessionKey;

    /**
     * 是否第一次登录 1-是 0-否
     */
    @Schema(description = "是否第一次登录 1-是 0-否")
    private Integer ifFirst;

    /**
     * 头像图片
     */
    @Schema(description = "头像图片")
    private String headImage;

    /**
     * 支付宝小程序用户id
     */
    @Schema(description = "支付宝小程序用户id")
    private String aliUserId;

    /**
     * 会员等级id
     */
    @Schema(description = "会员等级id")
    private Long memberLevelId;

    /**
     * 会员等级名称
     */
    @Schema(description = "会员等级名称")
    private String memberLevelName;

    /**
     * 成长值
     */
    @Schema(description = "成长值")
    private Integer growth;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Integer credit;

    /**
     * 下一级需要的成长值
     */
    @Schema(description = "下一级需要的成长值")
    private Integer nextLevelGrowth;

    /**
     * 下一级会员等级名称
     */
    private String nextLevelName;
}
