/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.product.ProductDetail;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.param.shop.ShopParam;
import com.shop.zkmall.commons.domain.product.CereSkuMemberRealInfo;
import com.shop.zkmall.commons.domain.product.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereSkuMemberRealInfoDAO extends BaseMapper<CereSkuMemberRealInfo> {

    boolean insertOrUpdate(CereSkuMemberRealInfo memberRealInfo);

    int updateSkuPrice(CereSkuMemberRealInfo memberRealInfo);

    int deleteByProductId(Long productId);

    int deleteBySkuId(Long skuId);

    int deleteByProductIdList(List<Long> normalProductIdList);

    int increSalesVolumeBy(@Param("productId") Long productId,
                           @Param("addedNumber") Integer addedNumber);

    int updateSelective(@Param("realInfo") CereSkuMemberRealInfo skuRealInfo,
                        @Param("resetSalesVolume") boolean resetSalesVolume);

    int clearMemberProductInfo(Long skuId);

    ProductDetail findProductDetailBySkuId(@Param("skuId") Long skuId,
                                           @Param("shopId") Long shopId,
                                           @Param("memberLevelId") Long memberLevelId);

    List<Sku> findSkuListByProductId(@Param("productId") Long productId,
                                     @Param("memberLevelId") Long memberLevelId);

    List<Product> getSearchProducts(SearchParam param);

    List<Product> getShopProducts(ShopParam param);

    int increSalesUserCount(List<Long> updateProductIdList);

    List<CartSku> findSkuListBySkuIdList(@Param("skuIdList") List<Long> skuIdList,
                                         @Param("memberLevelId") Long memberLevelId);

    List<CartSku> findCartSku(@Param("buyerUserId") Long buyerUserId,
                              @Param("memberLevelId") Long memberLevelId);

    CereSkuMemberRealInfo selectByPrimaryKey(@Param("skuId") Long skuId,
                                             @Param("memberLevelId") Long memberLevelId);

    int updateByPrimaryKeySelective(CereSkuMemberRealInfo updater);

    List<Sku> findSkuListByProductIdList(@Param("productIdList") List<Long> productIdList,
                                         @Param("memberLevelId") Long memberLevelId);
}
