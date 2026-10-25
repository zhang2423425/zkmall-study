/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order.impl;

import com.shop.zkmall.app.dao.order.CereOrderProductAttributeDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.OrderProductAttribute;
import com.shop.zkmall.app.service.order.CereOrderProductAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderProductAttributeServiceImpl implements CereOrderProductAttributeService {

    private final CereOrderProductAttributeDAO cereOrderProductAttributeDAO;

    @Override
    public void insertBatch(List<OrderProductAttribute> attributes) {
        cereOrderProductAttributeDAO.insertBatch(attributes);
    }

    @Override
    public List<OrderProductAttribute> findBySkus(List<CartSku> skus) {
        return cereOrderProductAttributeDAO.findBySkus(skus);
    }
}
