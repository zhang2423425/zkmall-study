/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.OrderProductAttribute;
import com.shop.zkmall.commons.domain.order.CereOrderProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderProductAttributeDAO extends BaseMapper<CereOrderProductAttribute> {

    int insertSelective(CereOrderProductAttribute record);

    void insertBatch(@Param("list") List<OrderProductAttribute> list);

    List<OrderProductAttribute> findBySkus(@Param("skus") List<CartSku> skus);
}
