/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_business 平台商家用户实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatformBusiness", description = "平台商家用户实体类")
public class CerePlatformBusiness implements Serializable {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商家用户id
     */
    @Schema(description = "商家用户id")
    @TableId(type = IdType.AUTO)
    private Long businessUserId;

    /**
     * 商家登录账号
     */
    @Schema(description = "商家登录账号")
    private String username;

    /**
     * 商家登录密码
     */
    @Schema(description = "商家登录密码")
    @JsonIgnore
    private String password;

    /**
     * 商家昵称
     */
    @Schema(description = "商家昵称")
    private String name;

    /**
     * 商家头像
     */
    @Schema(description = "商家头像")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 商家性别
     */
    @Schema(description = "商家性别")
    private String sex;

    /**
     * 商家邮箱
     */
    @Schema(description = "商家邮箱")
    private String email;

    /**
     * 用户token
     */
    @Schema(description = "用户token 停用")
    private String token;

    /**
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "是否默认用户 1是 0否")
    private Integer isDefault;

}
