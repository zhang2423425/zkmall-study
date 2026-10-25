/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.common;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("通用参数")
public class CommonIdParam {

    @Schema(description = "参数id")
    private Long id;

}
