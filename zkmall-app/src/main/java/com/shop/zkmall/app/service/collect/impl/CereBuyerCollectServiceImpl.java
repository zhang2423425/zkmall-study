/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.collect.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.collect.CereBuyerCollectDAO;
import com.shop.zkmall.app.dao.product.CereShopProductDAO;
import com.shop.zkmall.app.page.collect.CollectProduct;
import com.shop.zkmall.app.page.collect.CollectShop;
import com.shop.zkmall.app.param.collect.CollectGetAllParam;
import com.shop.zkmall.app.param.collect.CollectIdParam;
import com.shop.zkmall.app.param.collect.CollectParam;
import com.shop.zkmall.app.service.collect.CereBuyerCollectService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.collect.CereBuyerCollect;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.SpringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerCollectServiceImpl implements CereBuyerCollectService {

    private final CereBuyerCollectDAO cereBuyerCollectDAO;

    private final CereShopProductDAO cereShopProductDAO;

    private final CereShopCommentService cereShopCommentService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void collect(CollectParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(param.getProductId())) {
            //收藏商品,查询当前收藏该商品数据
            CereBuyerCollect cereBuyerCollect = cereBuyerCollectDAO.findByUserProduct(user.getBuyerUserId(), param.getProductId());
            if (cereBuyerCollect != null) {
                //收藏过,设置状态
                if (IntegerEnum.YES.getCode().equals(cereBuyerCollect.getState())) {
                    //如果是已收藏,改为未收藏
                    cereBuyerCollect.setState(IntegerEnum.NO.getCode());
                } else {
                    //反之
                    cereBuyerCollect.setState(IntegerEnum.YES.getCode());
                }
                cereBuyerCollect.setUpdateTime(time);
                cereBuyerCollectDAO.updateByPrimaryKeySelective(cereBuyerCollect);
            } else {
                //第一次收藏
                cereBuyerCollect = new CereBuyerCollect();
                cereBuyerCollect.setBuyerUserId(user.getBuyerUserId());
                cereBuyerCollect.setProductId(param.getProductId());
                cereBuyerCollect.setCreateTime(time);
                cereBuyerCollect.setCollectType(IntegerEnum.BUYER_COLLECT_PRODUCT.getCode());
                cereBuyerCollect.setState(IntegerEnum.YES.getCode());
                cereBuyerCollect.setSelected(IntegerEnum.NO.getCode());
                cereBuyerCollectDAO.insert(cereBuyerCollect);
            }
        } else if (!EmptyUtils.isEmpty(param.getShopId())) {
            //收藏店铺,,查询当前收藏该店铺数据
            CereBuyerCollect cereBuyerCollect = cereBuyerCollectDAO.findByUserShopId(user.getBuyerUserId(), param.getShopId());
            if (cereBuyerCollect != null) {
                //收藏过,设置状态
                if (IntegerEnum.YES.getCode().equals(cereBuyerCollect.getState())) {
                    //如果是已收藏,改为未收藏
                    cereBuyerCollect.setState(IntegerEnum.NO.getCode());
                } else {
                    //反之
                    cereBuyerCollect.setState(IntegerEnum.YES.getCode());
                }
                cereBuyerCollect.setUpdateTime(time);
                cereBuyerCollectDAO.updateByPrimaryKeySelective(cereBuyerCollect);
            } else {
                //第一次收藏
                cereBuyerCollect = new CereBuyerCollect();
                cereBuyerCollect.setBuyerUserId(user.getBuyerUserId());
                cereBuyerCollect.setShopId(param.getShopId());
                cereBuyerCollect.setCreateTime(time);
                cereBuyerCollect.setCollectType(IntegerEnum.BUYER_COLLECT_SHOPID.getCode());
                cereBuyerCollect.setState(IntegerEnum.YES.getCode());
                cereBuyerCollect.setSelected(IntegerEnum.NO.getCode());
                cereBuyerCollectDAO.insert(cereBuyerCollect);
            }
        }
    }

    @Override
    public CereBuyerCollect findByUserProduct(Long buyerUserId, Long productId) {
        return cereBuyerCollectDAO.findByUserProduct(buyerUserId, productId);
    }

    @Override
    public CereBuyerCollect findByUserShopId(Long buyerUserId, Long shopId) {
        return cereBuyerCollectDAO.findByUserShopId(buyerUserId, shopId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void cancel(CollectIdParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //批量取消收藏
        cereBuyerCollectDAO.cancel(param.getIds(), user.getBuyerUserId(), time);
    }

    @Override
    public void newCancel(CollectParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereBuyerCollectDAO.newCancel(param.getShopId(), user.getBuyerUserId(), time);
    }

    @Override
    public Page<CollectProduct> getAllProduct(CollectGetAllParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CollectProduct> list = cereBuyerCollectDAO.getAllProduct(user.getBuyerUserId(), param.getSearch());
        CereShopProductService cereShopProductService = SpringUtil.getBean(CereShopProductService.class);
        list.forEach(product -> {
            ProductBo productBo = cereShopProductService.fetchProductCache(product.getShopId(), product.getProductId(), product.getSkuId(), user, false);
            if (productBo != null) {
                product.setActivityType(productBo.getActivityType());
                product.setNumber(productBo.getNumber());
                //TODO 修改付款人数
                product.setUsers(productBo.getNumber());
            }
        });
        PageInfo<CollectProduct> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Page<CollectShop> getAllShop(CollectGetAllParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CollectShop> list = cereBuyerCollectDAO.getAllShop(user.getBuyerUserId(), param.getSearch());
        PageInfo<CollectShop> pageInfo = new PageInfo<>(list);
        list.forEach(a -> {
            a.setProductList(cereShopProductDAO.findRandom4ShopProducts(a.getShopId()));
            BigDecimal star = cereShopCommentService.selectShopStar(a.getShopId());
            a.setShopStar(star);
        });
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void selected(CollectIdParam param, CereBuyerUser user) {
        //批量选中
        cereBuyerCollectDAO.updateSelected(param.getIds());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(CollectIdParam param, CereBuyerUser user) {
        //批量删除
        cereBuyerCollectDAO.deleteByIds(param.getIds());
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerCollectDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public int findShopCollectCount(Long shopId) {
        return cereBuyerCollectDAO.findShopCollectCount(shopId);
    }

    @Override
    public long findAllCollectCount(Long buyerUserId) {
        return cereBuyerCollectDAO.selectCount(Wrappers.<CereBuyerCollect>lambdaQuery().eq(CereBuyerCollect::getBuyerUserId, buyerUserId).eq(CereBuyerCollect::getState, IntegerEnum.YES.getCode()));
    }

}
