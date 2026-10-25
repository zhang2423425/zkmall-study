/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.business.dao.shop.CereShopOtherOrganizationsDAO;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopOtherOrganizationsService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopOtherOrganizations;
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
public class CereShopOtherOrganizationsServiceImpl implements CereShopOtherOrganizationsService {

    private final CereShopOtherOrganizationsDAO cereShopOtherOrganizationsDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final CerePlatformShopDAO cerePlatformShopDAO;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void organizations(CereShopOtherOrganizations otherOrganizations, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        otherOrganizations.setCreateTime(time);
        cereShopOtherOrganizationsDAO.insert(otherOrganizations);
        //更新店铺认证状态
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        cerePlatformShop.setShopId(otherOrganizations.getShopId());
        cerePlatformShop.setAuthenticationState(IntegerEnum.UNDER_REVIEW.getCode());
        cerePlatformShop.setAuthenType(IntegerEnum.ORGANIZATION.getCode());
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateState(cerePlatformShop);
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "提交其他组织认证信息", otherOrganizations.getShopId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateOrganizations(CereShopOtherOrganizations otherOrganizations, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        otherOrganizations.setUpdateTime(time);
        cereShopOtherOrganizationsDAO.updateData(otherOrganizations);
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "修改其他组织认证信息", otherOrganizations.getShopId(), time);
    }

    @Override
    public CereShopOtherOrganizations findByShopId(Long shopId) {
        return cereShopOtherOrganizationsDAO.findByShopId(shopId);
    }

}
