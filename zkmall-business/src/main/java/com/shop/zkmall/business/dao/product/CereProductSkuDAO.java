/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.product.Sku;
import com.shop.zkmall.business.page.tool.ToolProduct;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereProductSkuDAO extends BaseMapper<CereProductSku> {

    int deleteByPrimaryKey(Long skuId);

    int insertSelective(CereProductSku record);

    CereProductSku selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(CereProductSku record);

    int updateByPrimaryKey(CereProductSku record);

    void insertBatch(@Param("list") List<CereProductSku> list);

    void deleteByIds(@Param("ids") List<Long> ids);

    void deleteByProductId(@Param("productId") Long productId);

    List<Sku> findByProductId(@Param("productId") Long productId);

    Integer findVolumeByProductId(@Param("productId") Long productId);

    int findStockNumber(@Param("skuId") Long skuId);

    List<ToolProduct> getToolSkus(@Param("productId") Long productId);

    List<CereProductSku> findStockNumberByOrderId(@Param("orderId") Long orderId);

    int findStockNumberBySkuId(@Param("skuId") Long skuId);

    void updateBatchSkus(@Param("list") List<CereProductSku> list);

    List<CereProductSku> selectStockNumberProductIdList(List<Long> productIdList);
}
