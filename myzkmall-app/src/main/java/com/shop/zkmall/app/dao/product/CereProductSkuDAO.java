/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.product.ProductSkus;
import com.shop.zkmall.app.page.product.ProductStockInfo;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.domain.product.SkuName;
import com.shop.zkmall.commons.domain.product.SkuValue;
import com.shop.zkmall.commons.domain.tool.CereComposeSkuInfo;
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

    List<SkuName> findSkuNames(@Param("productId") Long productId);

    List<SkuValue> findSkuValues(@Param("skuName") String skuName, @Param("productId") Long productId);

    ProductSkus findSku(@Param("skuId") Long skuId);

    ProductSkus findFirstSku(@Param("productId") Long productId);

    List<Sku> findSimpleSkuByProductId(@Param("productId") Long productId);

    List<Sku> findSkuByProductId(@Param("productId") Long productId);

    String findValueBySkuId(@Param("skuId") Long skuId);

    Integer findStockNumberBySkuId(@Param("skuId") Long skuId);

    List<CartSku> findStockNumberBySkus(@Param("skus") List<ProductSku> skus);

    void updateBatch(@Param("list") List<CartSku> list);

    List<CartSku> findSkuByOrderId(@Param("orderId") Long orderId);

    List<CartSku> findSkuByIds(@Param("ids") List<Long> ids, @Param("orderId") Long orderId);

    List<CereProductSku> findStockNumberByOrderId(@Param("orderId") Long orderId);

    List<CereProductSku> findAll();

    List<SkuValue> findOneValues(@Param("skuId") Long skuId);

    Integer findStockNumber(@Param("skuId") Long skuId);

    List<SkuValue> findValuesByProductId(@Param("productId") Long productId);

    List<CartSku> findOriginalSkuBySkus(@Param("skus") List<ProductSku> skus);

    void updateBatchSkus(@Param("list") List<CereProductSku> list);

    List<CereComposeSkuInfo> selectSkuInfo(Long pId);

    ProductStockInfo findProductStockInfo(Long productId);

    ProductStockInfo findSkuStockInfo(Long skuId);

    int rollbackStock(@Param("skuId") Long skuId, @Param("buyNumber") Integer buyNumber);

    List<Long> selectSkuIdListByProductId(@Param("productId") Long productId);

    List<CereProductSku> selectProductIAndSkuId(Long shopId);

    List<SkuValue> findSkuValueBySkuId(@Param("skuName") String skuName,
                                       @Param("skuId") Long skuId);

    List<CereProductSku> selectMatchSceneList(@Param("shopId") Long shopId,
                                              @Param("filterActivityTypeList") List<Integer> filterActivityTypeList);

    List<CereProductSku> selectSceneTypeList(@Param("shopId") Long shopId,
                                             @Param("sceneType") Integer sceneType);

    List<Sku> findStockNumberByProductId(Long productId);

    List<CartSku> findCartSkuList(@Param("skuIdList") List<Long> skuIdList);

    Long totalStockNumber(@Param("productId") Long productId);
}
