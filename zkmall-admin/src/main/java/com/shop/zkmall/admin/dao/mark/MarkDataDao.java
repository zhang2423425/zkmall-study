/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.mark;

import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.domain.order.CereOrderProductAttribute;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface MarkDataDao {

    List<CereBuyerUser> findUsers();

    CereBuyerReceive findReceive(@Param("buyerUserId") Long buyerUserId);

    List<CereProductSku> findProducts();

    CereOrderProductAttribute findBySkuId(@Param("skuId") Long skuId);

    List<CereShopOrder> findOrders();

    List<CereOrderProduct> findAfterProducts(@Param("orderId") Long orderId);

    CereAfterProductAttribute findAfterAttribute(@Param("orderProductId") Long orderProductId);

}
