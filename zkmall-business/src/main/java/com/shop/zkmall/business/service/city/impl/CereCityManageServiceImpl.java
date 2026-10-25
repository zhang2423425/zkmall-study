/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.city.impl;

import com.shop.zkmall.business.dao.city.CereCityManageDAO;
import com.shop.zkmall.business.page.city.City;
import com.shop.zkmall.business.param.logistics.LogistCityParam;
import com.shop.zkmall.business.service.city.CereCityManageService;
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
public class CereCityManageServiceImpl implements CereCityManageService {

    private final CereCityManageDAO cereCityManageDAO;

    @Override
    public List<City> getCitySelect(LogistCityParam param) {
        return cereCityManageDAO.getCitySelect(param);
    }
}
