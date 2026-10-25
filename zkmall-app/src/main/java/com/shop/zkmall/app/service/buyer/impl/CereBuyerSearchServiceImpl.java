/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer.impl;

import com.shop.zkmall.app.dao.buyer.CereBuyerSearchDAO;
import com.shop.zkmall.app.service.buyer.CereBuyerSearchService;
import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;
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
public class CereBuyerSearchServiceImpl implements CereBuyerSearchService {

    private final CereBuyerSearchDAO cereBuyerSearchDAO;

    @Override
    public List<CereBuyerSearch> getHistory(Long buyerUserId) {
        return cereBuyerSearchDAO.getHistory(buyerUserId);
    }

    @Override
    public void insert(CereBuyerSearch cereBuyerSearch) {
        cereBuyerSearchDAO.insert(cereBuyerSearch);
    }

    @Override
    public void delete(Long searchId) {
        cereBuyerSearchDAO.deleteByPrimaryKey(searchId);
    }

    @Override
    public CereBuyerSearch findBySearch(String search, Long buyerUserId) {
        return cereBuyerSearchDAO.findBySearch(search, buyerUserId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerSearchDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public List<String> selectHotSearch() {
        return cereBuyerSearchDAO.selectHotSearch();
    }
}
