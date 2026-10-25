/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.pay.impl;

import com.shop.zkmall.app.dao.pay.CerePayLogDAO;
import com.shop.zkmall.app.service.pay.CerePayLogService;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePayLogServiceImpl implements CerePayLogService {

    private final CerePayLogDAO cerePayLogDAO;

    @Override
    public void insert(CerePayLog payLog) {
        cerePayLogDAO.insert(payLog);
    }
}
