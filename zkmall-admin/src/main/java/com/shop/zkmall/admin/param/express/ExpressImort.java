/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.express;

import com.shop.zkmall.commons.poi.IsNeeded;
import lombok.Data;

/**
 * 导入快递公司编码数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class ExpressImort {

    /**
     * 快递公司
     */
    @IsNeeded
    private String company;

    /**
     * 快递公司编码
     */
    @IsNeeded
    private String code;
}
