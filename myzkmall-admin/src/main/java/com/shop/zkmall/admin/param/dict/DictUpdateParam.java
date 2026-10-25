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
 * 更新字典请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DictUpdateParam", description = "更新字典请求")
public class DictUpdateParam {

    /**
     * 字典id
     */
    @Schema(description = "字典id")
    private Long dictId;

    /**
     * 父节点id
     */
    @Schema(description = "父节点id")
    private Long dictPid;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述")
    private String dictDescribe;
}
