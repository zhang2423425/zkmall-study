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
 * 删除账户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserDeleteParam", description = "删除账户请求")
public class UserDeleteParam {

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long platformUserId;
}
