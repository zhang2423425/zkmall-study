/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.notice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.notice.CereNoticeDAO;
import com.shop.zkmall.admin.param.notice.NoticeGetAllParam;
import com.shop.zkmall.admin.param.notice.NoticeIdParam;
import com.shop.zkmall.admin.param.notice.NoticeParam;
import com.shop.zkmall.admin.service.notice.CereNoticeService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.notice.CereNotice;
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
public class CereNoticeServiceImpl implements CereNoticeService {

    private final CereNoticeDAO cereNoticeDAO;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(NoticeParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereNotice cereNotice = new CereNotice();
        cereNotice.setCreateTime(time);
        cereNotice.setNoticeTitle(param.getNoticeTitle());
        cereNotice.setNoticeContent(param.getNoticeContent());
        cereNotice.setNoticeType(param.getNoticeType());
        cereNotice.setReceive(param.getReceive());
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeDAO.insert(cereNotice);
    }

    @Override
    public void delete(NoticeIdParam param, CerePlatformUser user) {
        cereNoticeDAO.deleteByPrimaryKey(param.getNoticeId());
    }

    @Override
    public CereNotice getById(Long noticeId) {
        return cereNoticeDAO.selectByPrimaryKey(noticeId);
    }

    @Override
    public Page<CereNotice> getAll(NoticeGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CereNotice> list = cereNoticeDAO.getAll(param);
        PageInfo<CereNotice> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public List<CereNotice> getNotices() {
        return cereNoticeDAO.getNotices();
    }
}
