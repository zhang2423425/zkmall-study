/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.index;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 首页概况请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "IndexParam", description = "首页概况请求")
public class IndexParam {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 时间条件 1-今天 2-昨天 3-最近七天 4-最近30天
     */
    @Schema(description = "时间条件 1-今天 2-昨天 3-最近七天 4-最近30天")
    private Integer condition;
}
