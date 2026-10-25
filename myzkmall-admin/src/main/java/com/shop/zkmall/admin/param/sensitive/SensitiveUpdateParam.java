/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.sensitive;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新敏感词设置
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SensitiveUpdateParam", description = "更新敏感词设置")
public class SensitiveUpdateParam {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 敏感词库
     */
    @Schema(description = "敏感词库")
    private String sensitiveWord;

    /**
     * 是否开启 1-是 0-否
     */
    @Schema(description = "是否开启 1-是 0-否")
    private Integer state;

    /**
     * 处理措施  1-禁止发布 2-需审核
     */
    @Schema(description = "处理措施  1-禁止发布 2-需审核")
    private Integer handleMeasures;
}
