/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 删除物流方案请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "LogistDeleteParam", description = "删除物流方案请求")
public class LogistDeleteParam {

    /**
     * 物流方案id
     */
    @Schema(description = "物流方案id")
    private Long logisticsId;
}
