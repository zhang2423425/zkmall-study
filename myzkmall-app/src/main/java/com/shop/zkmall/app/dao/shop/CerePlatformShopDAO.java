/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.banner.ShopBanner;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.index.RecommendShop;
import com.shop.zkmall.app.page.settlement.SettlementShop;
import com.shop.zkmall.app.page.shop.PlatformShop;
import com.shop.zkmall.app.page.shop.Shop;
import com.shop.zkmall.app.page.shop.ShopClassify;
import com.shop.zkmall.app.page.shop.ShopIndex;
import com.shop.zkmall.app.param.shop.ShopParam;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformShopDAO extends BaseMapper<CerePlatformShop> {

    int deleteByPrimaryKey(Long shopId);

    int insertSelective(CerePlatformShop record);

    CerePlatformShop selectByPrimaryKey(Long shopId);

    int updateByPrimaryKeySelective(CerePlatformShop record);

    int updateByPrimaryKey(CerePlatformShop record);

    List<RecommendShop> findRecommendShop();

    RecommendShop findVolumeProductByShopId(@Param("shopId") Long shopId);

    List<RecommendShop> findRecommendProducts();

    Integer findPayUsers(@Param("productId") Long productId);

    List<Product> getShopProducts(ShopParam param);

    Shop findNumber(@Param("shopId") Long shopId);

    List<ShopClassify> getShopClassify(@Param("shopId") Long shopId);

    List<Product> getExtensionProduct(ShopParam param);

    List<ShopBanner> getShopBanner(@Param("shopId") Long shopId);

    List<SettlementShop> getShops(ShopParam param);

    /** 每个店铺默认查10个商品 */
    List<CartSku> findSkuByShopParam(ShopParam param);

    void updateState(CerePlatformShop cerePlatformShop);

    PlatformShop findShop(@Param("shopId") Long shopId);

    CerePlatformShop findByShopName(@Param("shopName") String shopName);

    CerePlatformShop findByPhone(@Param("shopPhone") String shopPhone);

    CerePlatformShop checkShopIdByName(@Param("shopName") String shopName, @Param("shopId") Long shopId);

    CerePlatformShop checkShopIdByPhone(@Param("shopPhone") String shopPhone, @Param("shopId") Long shopId);

    CerePlatformShop check(@Param("shopPhone") String shopPhone);

    ShopIndex findShopIndex(@Param("shopId") Long shopId);

    Integer findShopNumber(@Param("shopId") Long shopId);

    @MapKey("shopId")
    Map<Long, Shop> getClassifyShop(List<Product> list);

    List<ShopIndex> selectHotShopList();
}
