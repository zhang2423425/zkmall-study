/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.after;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereAfterProductDAO extends BaseMapper<CereAfterProduct> {

    int insertSelective(CereAfterProduct record);

    List<CereAfterProductAttribute> findValuesBySkuId(@Param("skuId") Long skuId);

    List<CartSku> findSkusByAfterId(@Param("afterId") Long afterId);

    List<CereAfterProduct> findSkuBySkus(AfterParam param);

    List<CereAfterProduct> findByOrderIdForCheck(Long orderId);
}
