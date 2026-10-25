/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CereShopReturnDAO;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopReturnService;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CereShopReturn;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopReturnServiceImpl implements CereShopReturnService {

    private final CereShopReturnDAO cereShopReturnDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    public CereShopReturn findByShopId(Long shopId) {
        return cereShopReturnDAO.findByShopId(shopId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateReturn(CereShopReturn cereShopReturn, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereShopReturnDAO.updateData(cereShopReturn);
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "修改店铺退货地址", cereShopReturn.getShopId(), time);
    }

    @Override
    public void update(CereShopReturn shopReturn) {
        cereShopReturnDAO.updateData(shopReturn);
    }

    @Override
    public void insert(CereShopReturn shopReturn) {
        cereShopReturnDAO.insert(shopReturn);
    }
}
