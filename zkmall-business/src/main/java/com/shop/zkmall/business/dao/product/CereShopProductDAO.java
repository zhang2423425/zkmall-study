/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.canvas.CanvasProduct;
import com.shop.zkmall.business.page.canvas.CanvasProductParam;
import com.shop.zkmall.business.page.canvas.CanvasShop;
import com.shop.zkmall.business.page.member.ProductMember;
import com.shop.zkmall.business.page.product.ShopProduct;
import com.shop.zkmall.business.param.product.ProductGetAllParam;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopProductDAO extends BaseMapper<CereShopProduct> {

    int deleteByPrimaryKey(Long productId);

    CereShopProduct selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(CereShopProduct record);

    int updateByPrimaryKeyWithBLOBs(CereShopProduct record);

    int updateByPrimaryKey(CereShopProduct record);

    ShopProduct getById(@Param("productId") Long productId);

    List<ShopProduct> getAll(ProductGetAllParam param);

    List<ShopProduct> getAllByPage(ProductGetAllParam param);

    @MapKey("productId")
    Map<Long, ShopProduct> getProductExtInfo(List<Long> productIdList);

    CereShopProduct checkName(@Param("shopId") Long shopId, @Param("classifyId") Long classifyId, @Param("productName") String productName);

    List<CanvasProduct> getProducts(CanvasProductParam param);

    List<CanvasShop> getShops(CanvasProductParam param);

    List<Long> findAllZeroStockNumber();

    void updateBatchShelveState(@Param("ids") List<Long> ids, @Param("time") String time);

    List<CereShopProduct> selectAll();

    List<ProductMember> getProductMembers(@Param("productId") Long productId);

    int deleteShopCartByProductId(@Param("productId") Long productId);
}
