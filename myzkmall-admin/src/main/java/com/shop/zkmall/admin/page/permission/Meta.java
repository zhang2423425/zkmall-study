/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 权限属性
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Meta", description = "权限属性")
public class Meta {

    /**
     * 权限名称
     */
    @Schema(description = "名称")
    private String title;

    /**
     * 图标地址
     */
    @Schema(description = "路由URI")
    private String icon;
}
