/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.shop.CereShopWithdrawalDAO;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.shop.CereShopWithdrawalService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopWithdrawal;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopWithdrawalServiceImpl implements CereShopWithdrawalService {

    private final CereShopWithdrawalDAO cereShopWithdrawalDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final EncodeUtil encodeUtil;

    @Override
    public Page<CereShopWithdrawal> getAll(WithdrawalGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CereShopWithdrawal> list = cereShopWithdrawalDAO.getAll(param);
        PageInfo<CereShopWithdrawal> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public CereShopWithdrawal getById(Long withdrawalId) {
        CereShopWithdrawal withdrawal = cereShopWithdrawalDAO.getById(withdrawalId);
        withdrawal.setBankCard(encodeUtil.encodeInfo(withdrawal.getBankCard()));
        return withdrawal;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(WithdrawalHandleParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopWithdrawal cereShopWithdrawal = new CereShopWithdrawal();
        cereShopWithdrawal.setWithdrawalId(param.getWithdrawalId());
        cereShopWithdrawal.setHandleTime(time);
        cereShopWithdrawal.setState(IntegerEnum.WITHDRAWAL_ALREADY.getCode());
        cereShopWithdrawalDAO.updateById(cereShopWithdrawal);
        //新增日志
        cerePlatformLogService.addLog(user, "提现申请管理", "平台端操作", "处理提现申请", String.valueOf(param.getWithdrawalId()), time);
    }
}
