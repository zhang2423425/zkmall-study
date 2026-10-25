/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.admin;

import com.shop.zkmall.admin.param.admin.IndexCharts;
import com.shop.zkmall.admin.param.admin.IndexStats;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface AdminService {

    IndexStats indexStats();

    IndexCharts indexCharts();
}
