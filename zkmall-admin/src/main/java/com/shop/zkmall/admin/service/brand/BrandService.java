/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.brand;

import com.shop.zkmall.admin.param.brand.BrandGetAllParam;
import com.shop.zkmall.admin.param.brand.BrandParam;
import com.shop.zkmall.commons.domain.brand.Brand;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface BrandService {

    int save(BrandParam brand);

    int update(BrandParam brand);

    int delete(Long id);

    Brand getById(Long id);

    Page<Brand> getAll(BrandGetAllParam param);

}
