/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CereShopBankDAO;
import com.shop.zkmall.business.page.shop.ShopBankDetail;
import com.shop.zkmall.business.param.bank.BankDeleteParam;
import com.shop.zkmall.business.param.bank.BankSaveParam;
import com.shop.zkmall.business.param.bank.BankUpdateParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopBankService;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopBank;
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
public class CereShopBankServiceImpl implements CereShopBankService {

    private final CereShopBankDAO cereShopBankDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(BankSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank = new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBank.setCardName(param.getCardName());
        cereShopBank.setCardNumber(param.getCardNumber());
        cereShopBank.setBank(param.getBank());
        cereShopBankDAO.insert(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user, "商家账户", "商户端操作", "绑定账户", param.getShopId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(BankUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank = new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBank.setCardName(param.getCardName());
        cereShopBank.setCardNumber(param.getCardNumber());
        cereShopBank.setBank(param.getBank());
        cereShopBankDAO.updateData(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user, "商家账户", "商户端操作", "更换账户", param.getShopId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(BankDeleteParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank = new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBankDAO.deleteData(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user, "商家账户", "商户端操作", "解除绑定", param.getShopId(), time);
    }

    @Override
    public ShopBankDetail getById(Long shopId) {
        return cereShopBankDAO.getById(shopId);
    }

    @Override
    public CerePlatformShop findByPhone(Long shopId, String phone) {
        return cereShopBankDAO.findByPhone(shopId, phone);
    }
}
