/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.buyer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.buyer.CereBuyerWithdrawalDAO;
import com.shop.zkmall.admin.page.buyer.BuyerWithdrawal;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.zkmall.admin.service.buyer.CereBuyerWithdrawalService;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerWithdrawal;
import com.shop.zkmall.commons.domain.common.Page;
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
public class CereBuyerWithdrawalServiceImpl implements CereBuyerWithdrawalService {

    private final CereBuyerWithdrawalDAO cereBuyerWithdrawalDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final EncodeUtil encodeUtil;

    @Override
    public Page<BuyerWithdrawal> getAll(BuyerWithdrawalGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<BuyerWithdrawal> list = cereBuyerWithdrawalDAO.getAll(param);
        for (BuyerWithdrawal with : list) {
            with.setPhone(encodeUtil.encodePhone(with.getPhone()));
        }
        PageInfo<BuyerWithdrawal> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public BuyerWithdrawal getById(Long withdrawalId) {
        BuyerWithdrawal with = cereBuyerWithdrawalDAO.getById(withdrawalId);
        if (with != null) {
            with.setName(encodeUtil.encodeInfo(with.getName()));
            with.setPhone(encodeUtil.encodePhone(with.getPhone()));
        }
        return with;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(BuyerWithdrawalGetByIdParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        String describe;
        CereBuyerWithdrawal cereBuyerWithdrawal = new CereBuyerWithdrawal();
        cereBuyerWithdrawal.setWithdrawalId(param.getWithdrawalId());
        if (IntegerEnum.WITHDRAWAL_SUCCESS.getCode().equals(param.getState())) {
            describe = "同意用户提现申请";
            cereBuyerWithdrawal.setState(IntegerEnum.WITHDRAWAL_SUCCESS.getCode());
        } else {
            describe = "拒绝用户提现申请";
            cereBuyerWithdrawal.setState(IntegerEnum.WITHDRAWAL_ERROR.getCode());
        }
        cereBuyerWithdrawal.setHandleTime(time);
        cereBuyerWithdrawalDAO.updateByPrimaryKeySelective(cereBuyerWithdrawal);
        //新增日志
        cerePlatformLogService.addLog(user, "用户提现申请", "平台端操作", describe, String.valueOf(param.getWithdrawalId()), time);
    }
}
