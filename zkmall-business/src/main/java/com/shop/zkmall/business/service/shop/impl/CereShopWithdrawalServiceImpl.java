/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CereShopWithdrawalDAO;
import com.shop.zkmall.business.param.finance.FinanceSaveWithdralwalParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.order.CereShopOrderService;
import com.shop.zkmall.business.service.shop.CereShopWithdrawalService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CereShopBank;
import com.shop.zkmall.commons.domain.shop.CereShopWithdrawal;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopWithdrawalServiceImpl implements CereShopWithdrawalService {

    private final CereShopWithdrawalDAO cereShopWithdrawalDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereShopOrderService cereShopOrderService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(FinanceSaveWithdralwalParam param, CerePlatformBusiness user) {
        //查询提现中金额
        BigDecimal stayMoney = cereShopOrderService.getWithdrawableStayMoney(param.getShopId());
        //查询总的可提现金额
        BigDecimal withdrawableMoney = cereShopOrderService.getAllWithdrawableMoney(param.getShopId());
        //查询已提现的金额
        BigDecimal money = cereShopOrderService.getWithdrawableMoney(param.getShopId());
        //计算可提现金额=总的提现金额-提现中金额-已提现金额
        BigDecimal total = withdrawableMoney.subtract(stayMoney).subtract(money);
        if (param.getWithdrawalMoney().compareTo(total) == 1) {
            throw new CoBusinessException(CoReturnFormat.BALANCE_NOT_ENOUGH);
        }
        String time = TimeUtils.yyMMddHHmmss();
        //查询当前商家绑定账户信息
        CereShopBank cereShopBank = cereShopWithdrawalDAO.findBank(param.getShopId());
        if (cereShopBank != null) {
            CereShopWithdrawal withdrawal = new CereShopWithdrawal();
            withdrawal.setShopId(param.getShopId());
            withdrawal.setShopId(param.getShopId());
            withdrawal.setShopCode(param.getShopCode());
            withdrawal.setShopName(param.getShopName());
            withdrawal.setBankCard(param.getBankCard());
            withdrawal.setBankName(param.getBankName());
            withdrawal.setCollectionName(cereShopBank.getCardName());
            withdrawal.setWithdrawalMoney(param.getWithdrawalMoney());
            withdrawal.setApplyTime(time);
            withdrawal.setState(IntegerEnum.WITHDRAWAL_STAY.getCode());
            cereShopWithdrawalDAO.insert(withdrawal);
            //新增日志
            cerePlatformLogService.addLog(user, "财务管理", "商户端操作", "添加提现申请", withdrawal.getWithdrawalId(), time);
        }
    }
}
