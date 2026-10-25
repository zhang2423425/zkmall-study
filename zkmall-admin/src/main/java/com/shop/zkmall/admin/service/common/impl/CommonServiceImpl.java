/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.common.impl;

import com.shop.zkmall.admin.service.common.CommonService;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Service
public class CommonServiceImpl implements CommonService {

    /**
     * 生成店铺编号
     */
    @Override
    public String getShopCode() {
        return "SJ" + RandomStringUtil.getRandomCode(8, 0);
    }
}
