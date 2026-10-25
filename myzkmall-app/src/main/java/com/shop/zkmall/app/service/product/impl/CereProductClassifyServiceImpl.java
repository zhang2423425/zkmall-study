/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.product.CereProductClassifyDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.app.page.classify.Classify;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.index.ProductClassify;
import com.shop.zkmall.app.page.index.ProductImage;
import com.shop.zkmall.app.page.shop.Shop;
import com.shop.zkmall.app.param.classify.ClassifyParam;
import com.shop.zkmall.app.param.classify.ClassifyProductParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.service.product.CereProductClassifyService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereProductClassifyServiceImpl implements CereProductClassifyService {

    private final CerePlatformShopDAO cerePlatformShopDAO;

    private final CereProductClassifyDAO cereProductClassifyDAO;

    private final CerePlatformShopService cerePlatformShopservice;

    private final CereShopProductService cereShopProductService;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    @Value("${querySkuRealInfo}")
    private boolean querySkuRealInfo;

    @Override
    public List<ProductClassify> findAll() {
        return cereProductClassifyDAO.findAll();
    }

    @Override
    public List<Classify> getFirstClassify(ClassifyParam param) {
        List<Classify> classifies;
        if (EmptyUtils.isEmpty(param.getClassifyId())) {
            //查询所有一级类目
            classifies = cereProductClassifyDAO.getFirstClassify(null);
        } else {
            //查询所有二级类目
            classifies = cereProductClassifyDAO.getFirstClassify(param.getClassifyId());
            //查询所有三级类目
            List<Classify> childs = cereProductClassifyDAO.findThreeClassify();
            //封装三级类目到对应二级类目子级
            if (!EmptyUtils.isEmpty(classifies)) {
                classifies.forEach(a -> {
                    List<Classify> classifyList = new ArrayList<>();
                    if (!EmptyUtils.isEmpty(childs)) {
                        childs.forEach(classify -> {
                            if (a.getClassifyId().equals(classify.getClassifyPid())) {
                                classifyList.add(classify);
                            }
                        });
                        a.setChilds(classifyList);
                    }
                });
            }
        }
        return classifies;
    }

    @Override
    public Page<Product> getClassifyProducts(ClassifyProductParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        if (user != null) {
            param.setBuyerUserId(user.getBuyerUserId());
        }
        List<Product> list;
        if (querySkuRealInfo) {
            param.setMemberLevelId(0L);
            SearchParam searchParam = new SearchParam();
            BeanUtils.copyProperties(param, searchParam);
            searchParam.setSearch(param.getProductName());
            list = cereSkuMemberRealInfoDAO.getSearchProducts(searchParam);
        } else {
            list = cereProductClassifyDAO.getClassifyProducts(param);
            if (!EmptyUtils.isEmpty(list)) {
                list.forEach(a -> {
                    //查询付款人数
                    a.setUsers(cerePlatformShopservice.findPayUsers(a.getProductId()));
                    //设置活动信息
                    ProductBo productBo = cereShopProductService.fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), user, true);
                    if (productBo != null) {
                        a.setActivityType(productBo.getActivityType());
                        a.setOriginalPrice(productBo.getOriginalPrice());
                        a.setPrice(productBo.getPrice());
                        a.setTotal(productBo.getTotal());
                        //a.setNumber(productBo.getNumber());
                    }
                });
            }
        }
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Page<Product> getClassifyProducts2(ClassifyProductParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        if (user != null) {
            param.setBuyerUserId(user.getBuyerUserId());
        }
        //获取产品信息
        List<Product> list = getProducts(param, user);

        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    private List<Product> getProducts(ClassifyProductParam param, CereBuyerUser user) {
        //查询产品信息
        List<Product> list = cereProductClassifyDAO.getClassifyProducts2(param);
        //查询商铺信息
        Map<Long, Shop> shopMap = cerePlatformShopDAO.getClassifyShop(list);
        //查询图片信息
        Map<Long, ProductImage> productImageMap = cereProductClassifyDAO.getClassifyImage(list);
        if (!EmptyUtils.isEmpty(list)) {
            list.forEach(a -> {
                //设置店铺名称、店铺logo
                a.setShopName(shopMap.get(a.getShopId()).getShopName());
                a.setShopLogo(shopMap.get(a.getShopId()).getShopLogo());
                //设置图片路径
                a.setImage(productImageMap.get(a.getProductId()).getImage());
                //查询付款人数
                a.setUsers(cerePlatformShopservice.findPayUsers(a.getProductId()));
                //设置活动信息
                ProductBo productBo = cereShopProductService.fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), user, true);
                if (productBo != null) {
                    a.setActivityType(productBo.getActivityType());
                    a.setOriginalPrice(productBo.getOriginalPrice());
                    a.setPrice(productBo.getPrice());
                    a.setTotal(productBo.getTotal());
                }
            });
        }
        return list;
    }

}
