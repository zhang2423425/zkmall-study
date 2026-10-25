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
 * 权限数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "MenuButton", description = "权限数据")
public class MenuButton {

    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private Long permissionId;

    /**
     * 父节点id
     */
    @Schema(description = "父节点id")
    private Long permissionPid;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    private String resourceType;

    /**
     * path路径
     */
    @Schema(description = "path路径")
    private String path;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String permissionName;

    /**
     * 图标地址
     */
    @Schema(description = "图标地址")
    private String icon;

    /**
     * 属性
     */
    @Schema(description = "属性")
    private Meta meta;

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<MenuButton> children;

    /**
     * 按钮数据
     */
    @Schema(description = "按钮数据")
    private List<MenuButton> buttons;
}
