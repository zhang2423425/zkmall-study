/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取下拉数据请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DictGetSelectParam", description = "获取下拉数据请求")
public class DictGetSelectParam {

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;
}
