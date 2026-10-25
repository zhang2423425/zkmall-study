/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.product.CereProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereProductImageDAO extends BaseMapper<CereProductImage> {

    int insertSelective(CereProductImage record);

    void insertBatch(@Param("list") List<CereProductImage> list);

    void deleteByProductId(@Param("productId") Long productId);

    List<String> findByProductId(@Param("productId") Long productId);

    List<CereProductImage> selectListByProductId(@Param("productId") Long productId);
}
