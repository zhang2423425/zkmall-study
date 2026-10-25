/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.user;

import com.shop.zkmall.business.page.role.PlatformUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 商家用户数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PlatformBusiness", description = "商家用户数据")
public class Business {

    /**
     * 商家用户id
     */
    @Schema(description = "商家用户id")
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
    private String password;

    /**
     * 商家昵称
     */
    @Schema(description = "商家昵称")
    private String name;

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
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;

    @Schema(description = "是否默认用户 1是 0否")
    private Integer isDefault;

    /**
     * 角色数据
     */
    @Schema(description = "角色数据")
    private List<PlatformUserRole> roles;

    /**
     * 角色id数组
     */
    @Schema(description = "角色id数组")
    private List<Long> ids;
}
