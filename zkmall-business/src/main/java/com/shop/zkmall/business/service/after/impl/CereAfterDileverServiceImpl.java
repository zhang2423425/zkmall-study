/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.after.impl;

import com.shop.zkmall.business.dao.after.CereAfterDileverDAO;
import com.shop.zkmall.business.service.after.CereAfterDileverService;
import com.shop.zkmall.commons.domain.after.CereAfterDilever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereAfterDileverServiceImpl implements CereAfterDileverService {

    private final CereAfterDileverDAO cereAfterDileverDAO;

    @Override
    public void insert(CereAfterDilever cereAfterDilever) {
        cereAfterDileverDAO.insert(cereAfterDilever);
    }
}
