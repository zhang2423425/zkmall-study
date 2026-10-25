/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RolePermission", description = "角色菜单数据")
public class RolePermission {

    /**
     * 菜单层级数据
     */
    @Schema(description = "菜单层级数据")
    private List<Permission> permissions;

    /**
     * 已选中的菜单id数组
     */
    @Schema(description = "已选中的菜单id数组")
    private List<Long> ids;
}
