/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.brand.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shop.zkmall.business.dao.brand.BrandDAO;
import com.shop.zkmall.business.service.brand.BrandService;
import com.shop.zkmall.commons.domain.brand.Brand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandDAO brandDAO;

    @Override
    public List<Brand> getBrandList() {
        return brandDAO.selectList(Wrappers.emptyWrapper());
    }

}
