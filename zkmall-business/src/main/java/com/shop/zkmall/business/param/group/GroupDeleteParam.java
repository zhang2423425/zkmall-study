/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 删除分组请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GroupDeleteParam", description = "删除分组请求")
public class GroupDeleteParam {

    /**
     * 商品分组id
     */
    @Schema(description = "商品分组id")
    private Long shopGroupId;
}
