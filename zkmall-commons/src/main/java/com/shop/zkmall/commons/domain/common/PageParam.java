/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页通用实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PageParam", description = "分页通用实体")
public class PageParam {

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer page;

    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数")
    private Integer pageSize;
}
