/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.product.CanvasProduct;
import com.shop.zkmall.admin.page.product.CanvasShop;
import com.shop.zkmall.admin.page.product.ProductDetail;
import com.shop.zkmall.admin.page.product.ShopProduct;
import com.shop.zkmall.admin.param.product.CanvasAdminProductParam;
import com.shop.zkmall.admin.param.product.ProductGetAllParam;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import com.shop.zkmall.commons.domain.product.CereSkuName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopProductDAO extends BaseMapper<CereShopProduct> {
    
    int deleteByPrimaryKey(Long productId);

    int insertSelective(CereShopProduct record);

    CereShopProduct selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(CereShopProduct record);

    int updateByPrimaryKeyWithBLOBs(CereShopProduct record);

    int updateByPrimaryKey(CereShopProduct record);

    int clearBrand(Long brandId);

    List<CanvasProduct> getProducts(CanvasAdminProductParam param);

    List<CanvasShop> getShops(CanvasAdminProductParam param);

    List<ShopProduct> getAll(ProductGetAllParam param);

    ProductDetail getById(@Param("productId") Long productId);

    int unShelveByShopId(Long shopId);

    List<Long> findProudctIdListByShopId(Long shopId);

    List<CereSkuName> findSkuNameListByProductId(Long productId);

    List<CanvasProduct> getRecommendActivityRealInfo(CanvasAdminProductParam param);

}
