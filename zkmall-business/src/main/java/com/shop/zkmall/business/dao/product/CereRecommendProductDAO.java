/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.shop.zkmall.commons.domain.product.CereRecommendProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereRecommendProductDAO {

    int insertOrUpdate(CereRecommendProduct cereRecommendProduct);

    List<CereRecommendProduct> selectByProductIdList(List<Long> productIdList);
}
