/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer;

import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerSearchService {

    List<CereBuyerSearch> getHistory(Long buyerUserId);

    void insert(CereBuyerSearch cereBuyerSearch);

    void delete(Long searchId);

    CereBuyerSearch findBySearch(String search, Long buyerUserId);

    void updateBuyerData(Long buyerUserId, Long id);

    List<String> selectHotSearch();
}
