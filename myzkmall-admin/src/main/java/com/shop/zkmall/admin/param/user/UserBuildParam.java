/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取权限请求实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserBuildParam", description = "获取权限请求实体")
public class UserBuildParam {

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long platformUserId;
}
