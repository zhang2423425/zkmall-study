/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除和修改查询商家用户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BusinessDeleteUser", description = "删除和修改查询商家用户请求")
public class BusinessDeleteUser implements Serializable {
    /**
     * 商家用户id
     */
    @Schema(description = "商家用户id")
    private Long businessUserId;

}
