/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.logistics.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.dao.logistics.CereOrderLogisticsDAO;
import com.shop.zkmall.business.page.logistics.Charge;
import com.shop.zkmall.business.page.logistics.Logistics;
import com.shop.zkmall.business.param.logistics.LogistDeleteParam;
import com.shop.zkmall.business.param.logistics.LogistSaveParam;
import com.shop.zkmall.business.param.logistics.LogistUpdateParam;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.logistics.CereLogisticsChargeService;
import com.shop.zkmall.business.service.logistics.CereOrderLogisticsService;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderLogisticsServiceImpl implements CereOrderLogisticsService {

    private final CereOrderLogisticsDAO cereOrderLogisticsDAO;

    private final CereLogisticsChargeService cereLogisticsChargeService;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(LogistSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereOrderLogistics cereOrderLogistics = new CereOrderLogistics();
        cereOrderLogistics.setLogisticsName(param.getLogisticsName());
        cereOrderLogistics.setChargeType(param.getChargeType());
        cereOrderLogistics.setCreateTime(time);
        cereOrderLogistics.setShopId(param.getShopId());
        //新增物流方案
        cereOrderLogisticsDAO.insert(cereOrderLogistics);
        //新增计费明细数据
        if (!EmptyUtils.isEmpty(param.getCharges())) {
            List<CereLogisticsCharge> collect = param.getCharges().stream()
                    .map(a -> {
                        CereLogisticsCharge charge = new CereLogisticsCharge();
                        charge.setLogisticsId(cereOrderLogistics.getLogisticsId());
                        charge.setRegion(EmptyUtils.getFenString(a.getRegions()));
                        charge.setWeight(a.getWeight());
                        charge.setPrice(a.getPrice());
                        charge.setSecondPrice(a.getSecondPrice());
                        charge.setSecondWeight(a.getSecondWeight());
                        return charge;
                    }).collect(Collectors.toList());
            cereLogisticsChargeService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "物流方案管理", "商户端操作", "添加物流方案", cereOrderLogistics.getLogisticsId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(LogistUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereOrderLogistics cereOrderLogistics = new CereOrderLogistics();
        cereOrderLogistics.setLogisticsId(param.getLogisticsId());
        cereOrderLogistics.setLogisticsName(param.getLogisticsName());
        cereOrderLogistics.setChargeType(param.getChargeType());
        cereOrderLogistics.setUpdateTime(time);
        cereOrderLogistics.setShopId(param.getShopId());
        cereOrderLogisticsDAO.updateByPrimaryKeySelective(cereOrderLogistics);
        //清空计费明细
        cereLogisticsChargeService.deleteByLogisticsId(param.getLogisticsId());
        //新增计费明细
        if (!EmptyUtils.isEmpty(param.getCharges())) {
            List<CereLogisticsCharge> collect = param.getCharges().stream()
                    .map(a -> {
                        CereLogisticsCharge charge = new CereLogisticsCharge();
                        charge.setLogisticsId(cereOrderLogistics.getLogisticsId());
                        charge.setRegion(EmptyUtils.getFenString(a.getRegions()));
                        charge.setWeight(a.getWeight());
                        charge.setPrice(a.getPrice());
                        charge.setSecondPrice(a.getSecondPrice());
                        charge.setSecondWeight(a.getSecondWeight());
                        return charge;
                    }).collect(Collectors.toList());
            cereLogisticsChargeService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "物流方案管理", "商户端操作", "修改物流方案", param.getLogisticsId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(LogistDeleteParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereOrderLogisticsDAO.deleteByPrimaryKey(param.getLogisticsId());
        //清空计费明细
        cereLogisticsChargeService.deleteByLogisticsId(param.getLogisticsId());
        //新增日志
        cerePlatformLogService.addLog(user, "物流方案管理", "商户端操作", "删除物流方案", param.getLogisticsId(), time);
    }

    @Override
    public Logistics getById(Long logisticsId) {
        //查询物流方案信息
        Logistics logistics = cereOrderLogisticsDAO.getById(logisticsId);
        if (logistics != null) {
            //查询计费明细数据
            List<Charge> list = cereLogisticsChargeService.findByLogisticsId(logisticsId);
            if (!EmptyUtils.isEmpty(list)) {
                //封装城市数组
                list.forEach(a -> a.setRegions(EmptyUtils.getFenStrings(a.getRegion())));
            }
            logistics.setCharges(list);
        }
        return logistics;
    }

    @Override
    public Page<Logistics> getAll(ShopParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Logistics> list = cereOrderLogisticsDAO.getAll(param);
        PageInfo<Logistics> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }
}
