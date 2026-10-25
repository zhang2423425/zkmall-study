/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新角色请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RoleUpdateParam", description = "更新角色请求")
public class RoleUpdateParam {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDescribe;
}
