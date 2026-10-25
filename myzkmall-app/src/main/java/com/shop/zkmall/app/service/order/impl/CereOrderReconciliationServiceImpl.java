/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order.impl;

import com.shop.zkmall.app.dao.order.CereOrderReconciliationDAO;
import com.shop.zkmall.app.service.order.CereOrderReconciliationService;
import com.shop.zkmall.commons.domain.order.CereOrderReconciliation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderReconciliationServiceImpl implements CereOrderReconciliationService {

    private final CereOrderReconciliationDAO cereOrderReconciliationDAO;

    @Override
    public void insert(CereOrderReconciliation reconciliation) {
        cereOrderReconciliationDAO.insert(reconciliation);
    }

    @Override
    public void updateByOrderId(CereOrderReconciliation reconciliation) {
        cereOrderReconciliationDAO.updateByOrderId(reconciliation);
    }
}
