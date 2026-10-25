/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.logistics.impl;

import com.shop.zkmall.business.dao.logistics.CereLogisticsChargeDAO;
import com.shop.zkmall.business.page.logistics.Charge;
import com.shop.zkmall.business.service.logistics.CereLogisticsChargeService;
import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;
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
public class CereLogisticsChargeServiceImpl implements CereLogisticsChargeService {

    private final CereLogisticsChargeDAO cereLogisticsChargeDAO;

    @Override
    public void insertBatch(List<CereLogisticsCharge> charges) {
        cereLogisticsChargeDAO.insertBatch(charges);
    }

    @Override
    public void deleteByLogisticsId(Long logisticsId) {
        cereLogisticsChargeDAO.deleteByLogisticsId(logisticsId);
    }

    @Override
    public List<Charge> findByLogisticsId(Long logisticsId) {
        return cereLogisticsChargeDAO.findByLogisticsId(logisticsId);
    }
}
