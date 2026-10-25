/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product.impl;

import com.shop.zkmall.business.page.member.MemberPrice;
import com.shop.zkmall.business.service.product.CereProductMemberService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Service
public class CereProductMemberServiceImpl implements CereProductMemberService {

    @Override
    public List<MemberPrice> getProductMembers(Long skuId) {
        return Collections.emptyList();
    }

    @Override
    public void deleteByProductId(Long productId) {
    }


}
