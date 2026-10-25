/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class BroadCastParam {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "拼团id")
    private Long shopGroupWorkId;

}
