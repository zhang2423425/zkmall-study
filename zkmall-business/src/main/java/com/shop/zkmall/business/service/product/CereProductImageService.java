/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product;

import com.shop.zkmall.commons.domain.product.CereProductImage;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductImageService {

    void insertBatch(List<CereProductImage> list);

    void deleteByProductId(Long productId);

    List<String> findByProductId(Long productId);

    List<CereProductImage> selectListByProductId(Long productId);
}
