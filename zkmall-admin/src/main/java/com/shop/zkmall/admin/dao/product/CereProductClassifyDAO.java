/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.product.ProductClassify;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import com.shop.zkmall.commons.domain.product.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    CereProductClassify getById(@Param("classifyId") Long classifyId);

    List<CereProductClassify> getAll();

    List<CereShopProduct> checkProduct(@Param("classifyId") Long classifyId,
                                       @Param("classifyLevel") Integer classifyLevel);

    List<ProductClassify> findByPid(@Param("classifyId") Long classifyId);

    void updateBatchLevelHierarchy(@Param("list") List<CereProductClassify> list);

    List<Classify> findAll();

    List<Classify> findChilds();

    void deleteByIds(@Param("ids") List<Long> ids);

    List<CereProductClassify> selectByPid(@Param("classifyId") Long classifyId);

    int deleteByPid(@Param("classifyId") Long classifyId);

    List<CereProductClassify> selectByPidList(@Param("ids") List<Long> ids);

    List<CereShopProduct> checkProductByIdList(@Param("ids") List<Long> ids);
}
