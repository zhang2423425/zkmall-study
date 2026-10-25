/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_user 平台账户实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatformUser", description = "平台账户实体类")
public class CerePlatformUser implements Serializable {

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    @TableId(type = IdType.AUTO)
    private Long platformUserId;

    /**
     * 平台登录账号
     */
    @Schema(description = "平台登录账号")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 账户手机号
     */
    @Schema(description = "账户手机号")
    private String phone;

    /**
     * 账户登录密码
     */
    @Schema(description = "账户登录密码")
    @JsonIgnore
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 账户token
     */
    @Schema(description = "账户token")
    private String token;

    /**
     * 是否启用   1-是 0-否
     */
    @Schema(description = "是否启用   1-是 0-否")
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

}
