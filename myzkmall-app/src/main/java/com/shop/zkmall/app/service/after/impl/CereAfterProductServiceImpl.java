/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.zkmall.app.dao.after.CereAfterProductAttributeDAO;
import com.shop.zkmall.app.dao.after.CereAfterProductDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.app.service.after.CereAfterProductService;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereAfterProductServiceImpl implements CereAfterProductService {

    private final CereAfterProductDAO cereAfterProductDAO;

    private final CereAfterProductAttributeDAO cereAfterProductAttributeDAO;

    @Override
    public void insert(CereAfterProduct afterProduct) {
        cereAfterProductDAO.insert(afterProduct);
    }

    @Override
    public List<CereAfterProductAttribute> findValuesBySkuId(Long skuId) {
        return cereAfterProductDAO.findValuesBySkuId(skuId);
    }

    @Override
    public List<CartSku> findSkusByAfterId(Long afterId) {
        return cereAfterProductDAO.findSkusByAfterId(afterId);
    }

    @Override
    public List<CereAfterProduct> findSkuBySkus(AfterParam param) {
        return cereAfterProductDAO.findSkuBySkus(param);
    }

    @Override
    public void deleteByAfterId(Long id) {
        LambdaQueryWrapper<CereAfterProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereAfterProduct::getAfterId, id);
        List<CereAfterProduct> afterProductList = cereAfterProductDAO.selectList(wrapper);

        if (CollectionUtils.isNotEmpty(afterProductList)) {
            cereAfterProductDAO.delete(wrapper);

            List<Long> afterProductIdList = afterProductList.stream().map(CereAfterProduct::getAfterProductId).collect(Collectors.toList());
            LambdaQueryWrapper<CereAfterProductAttribute> attributeWrapper = new LambdaQueryWrapper<>();
            attributeWrapper.in(CereAfterProductAttribute::getAfterProductId, afterProductIdList);
            cereAfterProductAttributeDAO.delete(attributeWrapper);
        }
    }

    @Override
    public List<CereAfterProduct> findByOrderIdForCheck(Long orderId) {
        return cereAfterProductDAO.findByOrderIdForCheck(orderId);
    }
}
