/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.collect.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.collect.CereBuyerFootprintDAO;
import com.shop.zkmall.app.page.collect.BuyerFootprint;
import com.shop.zkmall.app.page.collect.FootprintProduct;
import com.shop.zkmall.app.param.collect.FootprintIdParam;
import com.shop.zkmall.app.service.collect.CereBuyerFootprintService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.collect.CereBuyerFootprint;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerFootprintServiceImpl implements CereBuyerFootprintService {

    private final CereBuyerFootprintDAO cereBuyerFootprintDAO;

    @Override
    public void insert(CereBuyerFootprint cereBuyerFootprint) {
        cereBuyerFootprintDAO.insert(cereBuyerFootprint);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(FootprintIdParam param, CereBuyerUser user) {
        //批量删除足迹
        cereBuyerFootprintDAO.deleteByIds(param.getIds(), user.getBuyerUserId(), param.getTimes());
    }

    @Override
    public Page<BuyerFootprint> getAll(PageParam param, Long buyerUserId) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        //查询当前客户所有足迹根据浏览时间分组
        List<BuyerFootprint> list = cereBuyerFootprintDAO.getAll(buyerUserId);
        CereShopProductService cereShopProductService = SpringUtil.getBean(CereShopProductService.class);
        if (!EmptyUtils.isEmpty(list)) {
            //遍历时间,查询所有商品数据
            list.forEach(footprint -> {
                List<FootprintProduct> productList = cereBuyerFootprintDAO.findProducts(footprint.getCreateTime(), buyerUserId);
                productList.forEach(product -> {
                    ProductBo productBo = cereShopProductService.fetchProductCache(product.getShopId(), product.getProductId(), product.getSkuId(), null, false);
                    if (productBo != null) {
                        product.setActivityType(productBo.getActivityType());
                        product.setOriginalPrice(productBo.getOriginalPrice());
                        product.setPrice(productBo.getPrice());
                        product.setUsers(productBo.getNumber());
                    }
                });
                footprint.setProducts(productList);
            });
        }
        PageInfo<BuyerFootprint> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void selected(FootprintIdParam param, CereBuyerUser user) {
        //选中足迹
        cereBuyerFootprintDAO.updateSelected(param.getIds());
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerFootprintDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public long findFootprintCount(Long buyerUserId) {
        return cereBuyerFootprintDAO.selectCount(Wrappers.<CereBuyerFootprint>lambdaQuery().eq(CereBuyerFootprint::getBuyerUserId, buyerUserId));
    }

}
