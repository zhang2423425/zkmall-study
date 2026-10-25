/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.notice.impl;

import com.shop.zkmall.business.dao.notice.CereNoticeDAO;
import com.shop.zkmall.business.page.operate.OperateCoupon;
import com.shop.zkmall.business.service.notice.CereNoticeService;
import com.shop.zkmall.commons.domain.notice.CereNotice;
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
public class CereNoticeServiceImpl implements CereNoticeService {

    private final CereNoticeDAO cereNoticeDAO;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void addOperate(Long shopCrowdId, List<OperateCoupon> coupons, Long shopId, Long shopOperateId) {
        String time = TimeUtils.yyMMddHHmmss();
    }

    @Override
    public void insert(CereNotice cereNotice) {
        cereNoticeDAO.insert(cereNotice);
    }
}
