/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.order.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.order.CereShopOrderDAO;
import com.shop.zkmall.admin.page.order.ShopOrder;
import com.shop.zkmall.admin.page.product.Product;
import com.shop.zkmall.admin.param.order.OrderGetAllParam;
import com.shop.zkmall.admin.service.order.CereShopOrderService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopOrderServiceImpl implements CereShopOrderService {

    private final CereShopOrderDAO cereShopOrderDAO;

    private final EncodeUtil encodeUtil;

    @Override
    public void updateAfterState(CereShopOrder cereShopOrder) {
        cereShopOrderDAO.updateAfterState(cereShopOrder);
    }

    @Override
    public void insert(CereShopOrder order) {
        cereShopOrderDAO.insert(order);
    }

    @Override
    public void updateState(CereShopOrder cereShopOrder) {
        cereShopOrderDAO.updateState(cereShopOrder);
    }

    @Override
    public void updateBatchStock(List<CereProductSku> skus) {
        cereShopOrderDAO.updateBatchStock(skus);
    }

    @Override
    public Page<ShopOrder> getAll(OrderGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<ShopOrder> list = cereShopOrderDAO.getAll(param);
        for (ShopOrder order : list) {
            order.setCustomerName(encodeUtil.encodeInfo(order.getCustomerName()));
            order.setReceiveName(encodeUtil.encodeInfo(order.getReceiveName()));
            order.setReceivePhone(encodeUtil.encodePhone(order.getReceivePhone()));
            List<Product> products = cereShopOrderDAO.getProducts(order.getOrderId());
            order.setProducts(products);
            order.setTotal(cereShopOrderDAO.getOrderTotals(order.getBuyerUserId()));
            if (order.getPaymentState() == 0) {
                order.setPaymentMode(null);
            }
        }
        PageInfo<ShopOrder> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public ShopOrder getById(Long orderId) {
        ShopOrder shopOrder = cereShopOrderDAO.getById(orderId);
        if (shopOrder != null) {
            //设置详细地址
            shopOrder.setReceiveAdress(encodeUtil.encodeInfo(shopOrder.getReceiveAdress() + " " + shopOrder.getAddress()));
            //根据买家账户查询下单总数
            shopOrder.setTotal(cereShopOrderDAO.getOrderTotals(shopOrder.getBuyerUserId()));
            //查询商品信息
            List<Product> products = cereShopOrderDAO.getProducts(orderId);
            if (!EmptyUtils.isEmpty(products)) {
                //封装规格属性数据
                products.forEach(product -> product.setSkuDetails(cereShopOrderDAO.findSkuAttribute(product.getOrderProductId())));
            }
            shopOrder.setProducts(products);
            shopOrder.setReceiveName(encodeUtil.encodeInfo(shopOrder.getReceiveName()));
            shopOrder.setCustomerName(encodeUtil.encodeInfo(shopOrder.getCustomerName()));
            shopOrder.setReceivePhone(encodeUtil.encodePhone(shopOrder.getReceivePhone()));
        }
        return shopOrder;
    }

    @Override
    public Map<Long, Integer> selectSalesVolumeBySkuIdList(List<Long> skuIdList) {
        List<Map<String, Object>> map = cereShopOrderDAO.selectSalesVolumeBySkuIdList(skuIdList);
        Map<Long, Integer> salesVolumeMap = new HashMap<>();
        map.forEach(item -> {
            salesVolumeMap.put(Long.parseLong(item.get("sku_id").toString()), Integer.parseInt(item.get("sales_volume").toString()));
        });
        for (Long skuId : skuIdList) {
            salesVolumeMap.putIfAbsent(skuId, 0);
        }
        return salesVolumeMap;
    }
}
