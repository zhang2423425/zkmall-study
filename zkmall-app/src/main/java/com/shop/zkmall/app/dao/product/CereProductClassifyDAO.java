/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.classify.Classify;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.index.ProductClassify;
import com.shop.zkmall.app.page.index.ProductImage;
import com.shop.zkmall.app.param.classify.ClassifyProductParam;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereProductClassifyDAO extends BaseMapper<CereProductClassify> {

    int deleteByPrimaryKey(Long classifyId);

    int insertSelective(CereProductClassify record);

    CereProductClassify selectByPrimaryKey(Long classifyId);

    int updateByPrimaryKeySelective(CereProductClassify record);

    int updateByPrimaryKey(CereProductClassify record);

    List<ProductClassify> findAll();

    List<Classify> getFirstClassify(@Param("classifyId") Long classifyId);

    List<Classify> findThreeClassify();

    List<Product> getClassifyProducts(ClassifyProductParam param);

    List<Product> getClassifyProducts2(ClassifyProductParam param);

    @MapKey("productId")
    Map<Long, ProductImage> getClassifyImage(List<Product> list);
}
