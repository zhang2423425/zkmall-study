/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新账户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserUpdateParam", description = "更新账户请求")
public class UserUpdateParam {

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long platformUserId;

    /**
     * 账号或电话
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

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
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 角色id数组
     */
    @Schema(description = "角色id数组")
    private List<Long> roleIds;
}
