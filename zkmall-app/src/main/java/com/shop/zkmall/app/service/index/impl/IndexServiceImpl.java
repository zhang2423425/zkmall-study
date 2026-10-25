/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.index.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.page.index.Index;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.index.ProductClassify;
import com.shop.zkmall.app.page.index.RecommendShop;
import com.shop.zkmall.app.param.index.IndexParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.service.buyer.CereBuyerSearchService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.index.IndexService;
import com.shop.zkmall.app.service.product.CereProductClassifyService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final CerePlatformShopService cerePlatformShopservice;

    private final CereProductClassifyService cereProductClassifyService;

    private final CereBuyerSearchService cereBuyerSearchService;

    private final CereShopProductService cereShopProductService;

    private final CereBuyerUserService cereBuyerUserService;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    @Value("${querySkuRealInfo}")
    private boolean querySkuRealInfo;

    @Override
    public Index index(IndexParam param, CereBuyerUser user) {
        Index index = new Index();
        /*if (user != null) {
            //用户登录过
        } else {
            //第一次进来
        }*/
        //查询所有一级类目
        List<ProductClassify> classifies = cereProductClassifyService.findAll();
        //查询每周排名前十的好店推荐数据
        List<RecommendShop> shops = cerePlatformShopservice.findRecommendShop();
        if (!EmptyUtils.isEmpty(shops)) {
            shops.stream()
                    //查询该店销量最高商品数据
                    .peek(shop -> shop = cerePlatformShopservice.findVolumeProductByShopId(shop.getShopId()));
        }
        //查询好物推荐数据(销量前10商品数据)
        List<RecommendShop> products = cerePlatformShopservice.findRecommendProducts();
        if (!EmptyUtils.isEmpty(products)) {
            products.forEach(product -> {
                //设置付款人数
                product.setUsers(cerePlatformShopservice.findPayUsers(product.getProductId()));
                //设置活动信息
                ProductBo productBo = cereShopProductService.fetchProductCache(product.getShopId(), product.getProductId(), product.getSkuId(), user, false);
                if (productBo != null) {
                    product.setActivityType(productBo.getActivityType());
                    product.setOriginalPrice(productBo.getOriginalPrice());
                    product.setPrice(productBo.getPrice());
                    product.setTotal(productBo.getTotal());
                }
            });
        }
        index.setClassifies(classifies);
        index.setShops(shops);
        index.setProducts(products);
        return index;
    }

    @Override
    public List<CereBuyerSearch> getHistory(CereBuyerUser user) {
        if (user != null) {
            return cereBuyerSearchService.getHistory(user.getBuyerUserId());
        }
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public Page<Product> getSearchProducts(SearchParam param, CereBuyerUser user) {
        //过滤表情包参数
        param.setSearch(EmptyUtils.filterEmoji(param.getSearch()));
        if (!EmptyUtils.isEmpty(param.getSearch()) && user != null) {
            //查询当前搜索记录是否存在
            CereBuyerSearch cereBuyerSearch = cereBuyerSearchService.findBySearch(param.getSearch(), user.getBuyerUserId());
            if (cereBuyerSearch == null) {
                //如果没有,新增搜索记录
                cereBuyerSearch = new CereBuyerSearch();
                cereBuyerSearch.setBuyerUserId(user.getBuyerUserId());
                cereBuyerSearch.setSearch(param.getSearch());
                cereBuyerSearchService.insert(cereBuyerSearch);
            }
        }
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Product> list;
        if (querySkuRealInfo) {
            param.setMemberLevelId(0L);
            list = cereSkuMemberRealInfoDAO.getSearchProducts(param);
        } else {
            list = cereShopProductService.getSearchProducts(param);
            if (!EmptyUtils.isEmpty(list)) {
                list.forEach(a -> {
                    //查询付款人数
                    a.setUsers(cerePlatformShopservice.findPayUsers(a.getProductId()));
                    //设置活动相关信息
                    ProductBo productBo = cereShopProductService.fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), user, false);
                    if (productBo != null) {
                        a.setActivityType(productBo.getActivityType());
                        a.setOriginalPrice(productBo.getOriginalPrice());
                        a.setPrice(productBo.getPrice());
                    }
                });
                if (param.getType() != null) {
                    if (param.getType() == 1) {
                        list = list.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
                    } else if (param.getType() == 2) {
                        list = list.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
                    }
                }
            }
        }
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void deleteSearch(SearchParam param) {
        cereBuyerSearchService.delete(param.getSearchId());
    }

    @Override
    public CerePlatformCanvas getCanvas(CerePlatformCanvas canvas) {
        return cereBuyerUserService.getCanvas(canvas);
    }

}
