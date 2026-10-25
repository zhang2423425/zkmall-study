/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.index.HotSellProduct;
import com.shop.zkmall.business.page.shop.Shop;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

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

    CerePlatformShop findByShopName(@Param("shopName") String shopName);

    Shop findShop(@Param("shopId") Long shopId);

    void updateState(CerePlatformShop cerePlatformShop);

    Integer getTotal(@Param("shopId") Long shopId);

    Integer getStayOrders(@Param("shopId") Long shopId);

    Integer getStayAfters(@Param("shopId") Long shopId);

    BigDecimal getMoney(@Param("shopId") Long shopId);

    Integer getVisit(@Param("shopId") Long shopId, @Param("startTime") String startTime,
                     @Param("endTime") String endTime);

    List<HotSellProduct> getHotProducts(@Param("shopId") Long shopId, @Param("condition") Integer condition);

    CerePlatformShop check(@Param("shopPhone") String shopPhone);

    CerePlatformShop findByPhone(@Param("shopPhone") String shopPhone);

    CerePlatformShop checkShopIdByName(@Param("shopName") String shopName, @Param("shopId") Long shopId);

    CerePlatformShop checkShopIdByPhone(@Param("shopPhone") String shopPhone, @Param("shopId") Long shopId);

    BigDecimal findVisits(@Param("shopId") Long shopId, @Param("condition") Integer condition, @Param("type") Integer type);

    Integer findPayUsers(@Param("productId") Long productId);

    List<CereBusinessShop> findIds();

    Long findShopRoleId(@Param("id") Long id);

    List<CerePlatformShop> selectAll();
}
