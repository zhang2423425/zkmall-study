/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.logistics.impl;

import com.shop.zkmall.app.dao.logistics.CereOrderLogisticsDAO;
import com.shop.zkmall.app.service.logistics.CereOrderLogisticsService;
import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
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
public class CereOrderLogisticsServiceImpl implements CereOrderLogisticsService {

    private final CereOrderLogisticsDAO cereOrderLogisticsDAO;

    @Override
    public List<CereOrderLogistics> findLogistics(Long shopId) {
        return cereOrderLogisticsDAO.findLogistics(shopId);
    }

    @Override
    public List<CereLogisticsCharge> findCharges(Long logisticsId) {
        return cereOrderLogisticsDAO.findCharges(logisticsId);
    }
}
