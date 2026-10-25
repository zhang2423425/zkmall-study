/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.label.impl;

import com.shop.zkmall.admin.dao.label.CereBuyerLabelDAO;
import com.shop.zkmall.admin.param.buyer.BuyerSaveUserLabelParam;
import com.shop.zkmall.admin.service.label.CereBuyerLabelService;
import com.shop.zkmall.commons.domain.label.CereBuyerLabel;
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
public class CereBuyerLabelServiceImpl implements CereBuyerLabelService {

    private final CereBuyerLabelDAO cereBuyerLabelDAO;

    @Override
    public void insert(CereBuyerLabel cereBuyerLabel) {
        cereBuyerLabelDAO.insert(cereBuyerLabel);
    }

    @Override
    public void deleteLabelUser(List<Long> ids) {
        cereBuyerLabelDAO.deleteLabelUser(ids);
    }

    @Override
    public void insertBatch(List<CereBuyerLabel> collect) {
        cereBuyerLabelDAO.insertBatch(collect);
    }

    @Override
    public List<Long> findAlreadyByUser(BuyerSaveUserLabelParam param) {
        return cereBuyerLabelDAO.findAlreadyByUser(param);
    }

    @Override
    public List<Long> findByBuyerUserId(Long buyerUserId) {
        return cereBuyerLabelDAO.findByBuyerUserId(buyerUserId);
    }

}
