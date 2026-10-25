/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.page.finance.BondCount;
import com.shop.zkmall.business.page.finance.ShopBond;
import com.shop.zkmall.business.param.finance.BondParam;
import com.shop.zkmall.business.service.activity.CereActivitySignService;
import com.shop.zkmall.commons.domain.common.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author zkmall zkmallvip@163.com
 */
@Service
public class CereActivitySignServiceImpl implements CereActivitySignService {

    @Override
    public void deleteById(Long signId) {
    }

    @Override
    public void deleteDetailsBySignId(Long signId) {
    }

    @Override
    public void deleteLogBySignId(Long signId) {

    }

    @Override
    public BondCount getAllBond(BondParam param) {
        BondCount count = new BondCount();
        //查询保证金总额
        PageHelper.startPage(param.getPage(), param.getPageSize());
        PageInfo<ShopBond> pageInfo = new PageInfo<>(Collections.emptyList());
        Page<ShopBond> page = new Page<>(pageInfo.getList(), pageInfo.getTotal());
        count.setPage(page);
        return count;
    }

}
