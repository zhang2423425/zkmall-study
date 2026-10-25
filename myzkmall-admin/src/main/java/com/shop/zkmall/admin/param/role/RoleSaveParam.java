/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 添加角色请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RoleSaveParam", description = "添加角色请求")
public class RoleSaveParam {

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDescribe;
}
