/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.user;

import com.shop.zkmall.admin.page.role.PlatformUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 账户返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PlatformUser", description = "账户返回数据实体类")
public class PlatformUser {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long platformUserId;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 状态 1-启用 0-停用
     */
    @Schema(description = "状态 1-启用 0-停用")
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

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
