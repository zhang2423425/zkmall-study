/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 菜单分配请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RoleDistributionParam", description = "菜单分配请求")
public class RoleDistributionParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 权限id数组
     */
    @Schema(description = "权限id数组")
    private List<Long> permissionIds;
}
