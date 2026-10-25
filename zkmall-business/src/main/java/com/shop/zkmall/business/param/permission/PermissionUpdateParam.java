/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新权限请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PermissionUpdateParam", description = "更新权限请求")
public class PermissionUpdateParam {

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
     * 名称
     */
    @Schema(description = "名称")
    private String permissionName;

    /**
     * 路由URI
     */
    @Schema(description = "路由URI")
    private String permissionUri;

    /**
     * 组件
     */
    @Schema(description = "组件")
    private String permission;

    /**
     * 图标地址
     */
    @Schema(description = "图标地址")
    private String icon;

    /**
     * 文字描述
     */
    @Schema(description = "文字描述")
    private String describe;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    private String resourceType;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sort;

    /**
     * 权限所属项目
     */
    @Schema(description = "权限所属项目")
    private Integer project;
}
