/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.after;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 售后列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "AfterGetAllParam", description = "售后列表请求")
public class AfterGetAllParam extends PageParam {

    /**
     * 售后状态 1-售后中 2-已完成
     */
    @Schema(description = "售后状态 1-售后中 2-已完成")
    private Integer state;

    @Schema(description = "搜索关键词")
    private String keyword;

}
