/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 每周访问人数实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "VisitWeek", description = "每周访问人数实体类")
public class VisitWeek {

    /**
     * 周时间
     */
    @Schema(description = "周时间")
    private List<String> time;

    /**
     * 访问人数
     */
    @Schema(description = "访问人数")
    private List<Integer> total;
}
