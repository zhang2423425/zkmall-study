/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 添加字典请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "DictSaveParam", description = "添加字典请求")
public class DictSaveParam {

    /**
     * 父节点id
     */
    @Schema(description = "父节点id")
    private Long dictPid;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述")
    private String dictDescribe;
}
