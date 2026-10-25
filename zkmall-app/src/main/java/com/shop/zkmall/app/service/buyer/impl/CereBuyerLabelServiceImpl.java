/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer.impl;

import com.shop.zkmall.app.dao.buyer.CereBuyerLabelDAO;
import com.shop.zkmall.app.service.buyer.CereBuyerLabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerLabelServiceImpl implements CereBuyerLabelService {

    private final CereBuyerLabelDAO cereBuyerLabelDAO;

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerLabelDAO.updateBuyerData(buyerUserId, id);
    }
}
