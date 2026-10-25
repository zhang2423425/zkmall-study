/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.label;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 标签返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "PlatformLabel", description = "标签返回数据实体类")
public class PlatformLabel extends CerePlatformLabel {

    /**
     * 条件
     */
    @Schema(description = "条件")
    private List<String> conditions;

    /**
     * 多选框条件数组
     */
    @Schema(description = "多选框条件数组")
    private List<Integer> consumptions;

    /**
     * 客户数
     */
    @Schema(description = "客户数")
    private Integer users;
}
