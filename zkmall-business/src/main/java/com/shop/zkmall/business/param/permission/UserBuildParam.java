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
 * 获取权限请求实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserBuildParam", description = "获取权限请求实体")
public class UserBuildParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long businessUserId;
}
