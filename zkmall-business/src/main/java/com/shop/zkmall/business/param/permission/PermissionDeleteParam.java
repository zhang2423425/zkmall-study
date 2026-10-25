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
 * 删除权限请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PermissionDeleteParam", description = "删除权限请求")
public class PermissionDeleteParam {

    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private Long permissionId;
}
