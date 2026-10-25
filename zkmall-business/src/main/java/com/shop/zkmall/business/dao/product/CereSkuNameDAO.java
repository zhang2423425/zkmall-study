/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.param.product.DeleteSkuParam;
import com.shop.zkmall.business.param.product.SkuNameParam;
import com.shop.zkmall.business.param.product.SkuNameValueParam;
import com.shop.zkmall.business.param.product.SkuValueParam;
import com.shop.zkmall.commons.domain.product.CereSkuName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereSkuNameDAO extends BaseMapper<CereSkuName> {

    int deleteByPrimaryKey(@Param("skuId") Long skuId);

    int insertSelective(CereSkuName record);

    int updateByPrimaryKeySelective(CereSkuName record);

    int updateByPrimaryKey(CereSkuName record);

    void insertBatch(@Param("list") List<CereSkuName> list);

    void deleteByIds(@Param("ids") List<Long> ids);

    void deleteByProductId(@Param("productId") Long productId);

    List<SkuNameValueParam> findBySkuId(@Param("skuId") Long skuId);

    List<SkuNameParam> findNameBySkuId(@Param("skuId") Long skuId);

    List<SkuValueParam> findByNameAndSkuId(@Param("skuId") Long skuId, @Param("skuName") String skuName);

    CereSkuName findValueBySkuId(@Param("skuId") Long skuId);

    List<SkuNameParam> findNameByProductId(@Param("productId") Long productId);

    List<SkuValueParam> findByName(@Param("skuName") String skuName, @Param("productId") Long productId);

    CereSkuName findValueByProductId(@Param("productId") Long productId);

    List<Long> findDeleteSkuIds(@Param("list") List<DeleteSkuParam> list, @Param("productId") Long productId);

    List<CereSkuName> findSkuNameListByProductId(Long productId);
}
