/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.brand.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.shop.zkmall.admin.dao.brand.BrandDAO;
import com.shop.zkmall.admin.dao.product.CereShopProductDAO;
import com.shop.zkmall.admin.param.brand.BrandGetAllParam;
import com.shop.zkmall.admin.param.brand.BrandParam;
import com.shop.zkmall.admin.service.brand.BrandService;
import com.shop.zkmall.commons.domain.brand.Brand;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    private final CereShopProductDAO cereShopProductDAO;

    @Override
    public int save(BrandParam brand) {
        Brand tmp = new Brand();
        String now = TimeUtils.yyMMddHHmmss();
        BeanUtils.copyProperties(brand, tmp);
        tmp.setCreateTime(now);
        tmp.setUpdateTime(now);
        return brandDAO.insert(tmp);
    }

    @Override
    public int update(BrandParam brand) {
        Brand tmp = new Brand();
        String now = TimeUtils.yyMMddHHmmss();
        BeanUtils.copyProperties(brand, tmp);
        tmp.setUpdateTime(now);
        return brandDAO.updateById(tmp);
    }

    @Override
    public int delete(Long id) {
        cereShopProductDAO.clearBrand(id);
        return brandDAO.deleteById(id);
    }

    @Override
    public Brand getById(Long id) {
        return brandDAO.selectById(id);
    }

    @Override
    public Page<Brand> getAll(BrandGetAllParam param) {
        com.github.pagehelper.Page<Brand> page = PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Brand> brandList = brandDAO.selectList(Wrappers.<Brand>lambdaQuery().orderByDesc(Brand::getCreateTime));
        return new Page<>(brandList, page.getTotal());
    }

}
