/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.order.ShopOrder;
import com.shop.zkmall.admin.page.order.ShopOrderAfter;
import com.shop.zkmall.admin.page.product.Product;
import com.shop.zkmall.admin.page.product.SkuDetail;
import com.shop.zkmall.admin.param.order.OrderGetAllParam;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopOrderDAO extends BaseMapper<CereShopOrder> {

    int deleteByPrimaryKey(Long orderId);

    int insertSelective(CereShopOrder record);

    CereShopOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(CereShopOrder record);

    int updateByPrimaryKey(CereShopOrder record);

    void updateAfterState(CereShopOrder cereShopOrder);

    void updateState(CereShopOrder cereShopOrder);

    void updateBatchStock(@Param("skus") List<CereProductSku> skus);

    List<ShopOrder> getAll(OrderGetAllParam param);

    ShopOrder getById(@Param("orderId") Long orderId);

    Integer getOrderTotals(@Param("buyerUserId") Long buyerUserId);

    List<Product> getProducts(@Param("orderId") Long orderId);

    List<SkuDetail> findSkuAttribute(@Param("orderProductId") Long orderProductId);

    List<ShopOrderAfter> findAfter();

    Integer selectChannelOrderUserCount(String channelCode);

    Integer selectChannelOrderCount(String channelCode);

    BigDecimal selectChannelOrderAmount(String channelCode);

    List<Map<String, Object>> selectSalesVolumeBySkuIdList(List<Long> skuIdList);
}
