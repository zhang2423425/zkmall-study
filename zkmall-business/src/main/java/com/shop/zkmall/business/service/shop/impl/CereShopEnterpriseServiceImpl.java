/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.business.dao.shop.CereShopEnterpriseDAO;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopEnterpriseService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopEnterprise;
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
public class CereShopEnterpriseServiceImpl implements CereShopEnterpriseService {

    private final CerePlatformShopDAO cerePlatformShopDAO;

    private final CereShopEnterpriseDAO cereShopEnterpriseDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void enterprise(CereShopEnterprise enterprise, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        enterprise.setCreateTime(time);
        cereShopEnterpriseDAO.insert(enterprise);
        //更新店铺认证状态
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        cerePlatformShop.setShopId(enterprise.getShopId());
        cerePlatformShop.setAuthenticationState(IntegerEnum.UNDER_REVIEW.getCode());
        cerePlatformShop.setAuthenType(IntegerEnum.ENTERPRICE.getCode());
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateState(cerePlatformShop);
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "提交企业认证信息", enterprise.getShopId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateEnterprise(CereShopEnterprise enterprise, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        enterprise.setUpdateTime(time);
        cereShopEnterpriseDAO.updateData(enterprise);
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "修改企业认证信息", enterprise.getShopId(), time);
    }

    @Override
    public CereShopEnterprise findByShopId(Long shopId) {
        return cereShopEnterpriseDAO.findByShopId(shopId);
    }

}
