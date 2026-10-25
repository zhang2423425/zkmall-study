/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.page.product.MemberProduct;
import com.shop.zkmall.admin.param.product.CanvasAdminProductParam;
import com.shop.zkmall.admin.service.product.CereProductMemberService;
import com.shop.zkmall.commons.domain.common.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author zkmall zkmallvip@163.com
 */
@Service
public class CereProductMemberServiceImpl implements CereProductMemberService {

    @Override
    public Page<MemberProduct> getMemberProducts(CanvasAdminProductParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        PageInfo<MemberProduct> pageInfo = new PageInfo<>(Collections.emptyList());
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }
}
