/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.dao.shop.CereShopGroupDAO;
import com.shop.zkmall.business.page.group.Group;
import com.shop.zkmall.business.page.group.GroupDetail;
import com.shop.zkmall.business.page.group.GroupProduct;
import com.shop.zkmall.business.page.order.Product;
import com.shop.zkmall.business.param.group.*;
import com.shop.zkmall.business.param.product.ProductGetGroupParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopGroupService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopGroup;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopGroupServiceImpl implements CereShopGroupService {

    private final CereShopGroupDAO cereShopGroupDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    public List<CereShopGroup> getGroupSelect(ProductGetGroupParam param) {
        return cereShopGroupDAO.getGroupSelect(param.getShopId());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopGroupSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopGroup group = new CereShopGroup();
        group.setGroupDescribe(param.getGroupDescribe());
        group.setGroupName(param.getGroupName());
        group.setShopId(param.getShopId());
        group.setGroupImage(param.getGroupImage());
        group.setCreateTime(time);
        group.setUpdateTime(time);
        group.setGroupDescribe(param.getGroupDescribe());
        cereShopGroupDAO.insert(group);
        if (!EmptyUtils.isEmpty(param.getIds())) {
            //手动添加商品
            cereShopGroupDAO.addProduct(param.getIds(), group.getShopGroupId());
        }
        if (!EmptyUtils.isEmpty(param.getCondition()) && !EmptyUtils.isEmpty(param.getConditions())) {
            List<Long> ids = null;
            //智能添加,查询满足条件的商品id数组
            if (IntegerEnum.GROUP_CONDITION_ALL.getCode().equals(param.getCondition())) {
                //全部满足
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    ids = cereShopGroupDAO.findProductIds(condition);
                }
            } else {
                Map<Long, Long> map = new HashMap<>();
                //任意满足一个条件
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    List<Long> productIds = cereShopGroupDAO.findProductIds(condition);
                    if (!EmptyUtils.isEmpty(productIds)) {
                        productIds.forEach(id -> {
                            map.put(id, id);
                        });
                    }
                }
                ids = map.keySet().stream().collect(Collectors.toList());
            }
            if (!EmptyUtils.isEmpty(ids)) {
                cereShopGroupDAO.addProduct(ids, group.getShopGroupId());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "商品分组管理", "商户端操作", "添加商品分组", group.getShopGroupId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopGroupUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopGroup group = new CereShopGroup();
        group.setShopGroupId(param.getShopGroupId());
        group.setGroupDescribe(param.getGroupDescribe());
        group.setGroupName(param.getGroupName());
        group.setShopId(param.getShopId());
        group.setGroupImage(param.getGroupImage());
        group.setGroupDescribe(param.getGroupDescribe());
        group.setUpdateTime(time);
        cereShopGroupDAO.updateByPrimaryKeySelective(group);
        if (!EmptyUtils.isEmpty(param.getIds())) {
            //清空之前添加的商品
            cereShopGroupDAO.deleteProduct(group.getShopGroupId());
            //手动添加商品
            cereShopGroupDAO.addProduct(param.getIds(), group.getShopGroupId());
        }
        if (!EmptyUtils.isEmpty(param.getCondition()) && !EmptyUtils.isEmpty(param.getConditions())) {
            List<Long> ids = null;
            //智能添加,查询满足条件的商品id数组
            if (IntegerEnum.GROUP_CONDITION_ALL.getCode().equals(param.getCondition())) {
                //全部满足
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    ids = cereShopGroupDAO.findProductIds(condition);
                }
            } else {
                Map<Long, Long> map = new HashMap<>();
                //任意满足一个条件
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    List<Long> productIds = cereShopGroupDAO.findProductIds(condition);
                    if (!EmptyUtils.isEmpty(productIds)) {
                        productIds.forEach(id -> {
                            map.put(id, id);
                        });
                    }
                }
                ids = map.keySet().stream().collect(Collectors.toList());
            }
            if (!EmptyUtils.isEmpty(ids)) {
                cereShopGroupDAO.addProduct(ids, group.getShopGroupId());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "商品分组管理", "商户端操作", "修改商品分组", group.getShopGroupId(), time);
    }

    @Override
    public List<Product> getProductIds(ShopGroupSaveParam param) {
        List<Product> list = null;
        List<Long> ids = null;
        if (!EmptyUtils.isEmpty(param.getCondition()) && !EmptyUtils.isEmpty(param.getConditions())) {
            //智能添加,查询满足条件的商品id数组
            if (IntegerEnum.GROUP_CONDITION_ALL.getCode().equals(param.getCondition())) {
                //全部满足
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    ids = cereShopGroupDAO.findProductIds(condition);
                }
            } else {
                Map<Long, Long> map = new HashMap<>();
                //任意满足一个条件
                for (GroupCondition condition : param.getConditions()) {
                    condition.setShopId(param.getShopId());
                    condition.setIds(ids);
                    List<Long> productIds = cereShopGroupDAO.findProductIds(condition);
                    if (!EmptyUtils.isEmpty(productIds)) {
                        productIds.forEach(id -> {
                            map.put(id, id);
                        });
                    }
                }
                ids = map.keySet().stream().collect(Collectors.toList());
            }
        }
        if (!EmptyUtils.isEmpty(ids)) {
            //查询商品数据
            list = cereShopGroupDAO.findProductByIds(ids);
        }
        return list;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(GroupDeleteParam group, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereShopGroupDAO.deleteByPrimaryKey(group.getShopGroupId());
        //清除该分组与商品关联
        cereShopGroupDAO.updateGroup(group.getShopGroupId());
        //新增日志
        cerePlatformLogService.addLog(user, "商品分组管理", "商户端操作", "删除商品分组", group.getShopGroupId(), time);
    }

    @Override
    public GroupDetail getById(Long shopGroupId) {
        GroupDetail detail = cereShopGroupDAO.getById(shopGroupId);
        if (detail != null) {
            //设置商品数据
            detail.setProducts(cereShopGroupDAO.findProducts(shopGroupId));
        }
        return detail;
    }

    @Override
    public Page<Group> getAll(GroupGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Group> list = cereShopGroupDAO.getAll(param);
        PageInfo<Group> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Page<GroupProduct> getProducts(GroupProductParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<GroupProduct> list = cereShopGroupDAO.getProducts(param);
        PageInfo<GroupProduct> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

}
