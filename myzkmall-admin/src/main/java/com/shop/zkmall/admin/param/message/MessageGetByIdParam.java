/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取短信配置列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "MessageGetByIdParam", description = "获取短信配置列表请求")
public class MessageGetByIdParam {

    /**
     * 短信id
     */
    @Schema(description = "短信id")
    private Long messageId;
}
