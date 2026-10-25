/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.notice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.notice.CereBuyerNoticeDAO;
import com.shop.zkmall.app.dao.notice.CereNoticeDAO;
import com.shop.zkmall.app.page.notice.BuyerNotice;
import com.shop.zkmall.app.page.notice.NoticeDetail;
import com.shop.zkmall.app.param.notice.NoticeIdParam;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.domain.notice.CereBuyerNotice;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.exception.CoBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereNoticeServiceImpl implements CereNoticeService {

    private final CereNoticeDAO cereNoticeDAO;

    private final CereBuyerNoticeDAO cereBuyerNoticeDAO;

    @Override
    public Page<BuyerNotice> getAll(PageParam pageParam, CereBuyerUser user) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getPageSize());
        Long buyerUserId = null;
        String createTime = null;
        if (user != null) {
            buyerUserId = user.getBuyerUserId();
            createTime = user.getCreateTime();
        }
        List<BuyerNotice> list = cereNoticeDAO.getAll(buyerUserId, createTime);
        PageInfo<BuyerNotice> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public NoticeDetail getById(NoticeIdParam param) {
        return cereNoticeDAO.getById(param.getNoticeId());
    }

    @Override
    public List<BuyerNotice> getGongGaoAll(CereBuyerUser user) {
        Long buyerUserId = null;
        if (user != null) {
            buyerUserId = user.getBuyerUserId();
        }
        return cereNoticeDAO.getGongGaoAll(buyerUserId);
    }

    @Override
    public void insert(CereNotice cereNotice) {
        cereNoticeDAO.insert(cereNotice);
    }

    @Override
    public void insertBatch(List<CereNotice> notices) {
        cereNoticeDAO.insertBatch(notices);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereNoticeDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public int getNotRead(Long buyerUserId, String createTime) {
        return cereNoticeDAO.getNotRead(buyerUserId, createTime);
    }

    @Override
    public void update(CereNotice cereNotice) {
        cereNoticeDAO.updateByPrimaryKeySelective(cereNotice);
    }

    @Override
    public int getRead(Long buyerUserId) {
        return cereNoticeDAO.getRead(buyerUserId);
    }

    @Override
    public List<Long> findIdsByOrderId(Long orderId) {
        return cereNoticeDAO.findIdsByOrderId(orderId);
    }

    @Override
    public void deleteByIds(List<Long> noticeIds) {
        cereNoticeDAO.deleteByIds(noticeIds);
    }

    @Override
    public void deleteBuyerNotice(List<Long> noticeIds, Long buyerUserId) {
        cereNoticeDAO.deleteBuyerNotice(noticeIds, buyerUserId);
    }

    @Override
    public List<BuyerNotice> getNotices() {
        return cereNoticeDAO.getNotices();
    }

    @Override
    public void readAll(Long buyerUserId) {
        cereNoticeDAO.readAll(buyerUserId);
        List<BuyerNotice> notReadMessageList = cereNoticeDAO.selectNotReadMessage(buyerUserId);
        for (BuyerNotice notice : notReadMessageList) {
            CereBuyerNotice buyerNotice = new CereBuyerNotice();
            buyerNotice.setBuyerUserId(buyerUserId);
            buyerNotice.setNoticeId(notice.getNoticeId());
            cereBuyerNoticeDAO.insert(buyerNotice);
        }
    }

    @Override
    public void removeById(Long noticeId, Long buyerUserId) {
        CereNotice notice = cereNoticeDAO.selectByPrimaryKey(noticeId);
        if (notice != null && IntegerEnum.NOTICE_TYPE_SYSTEM.getCode().equals(notice.getNoticeType())) {
            cereNoticeDAO.deleteBuyerNotice(Collections.singletonList(noticeId), buyerUserId);
        } else {
            CereBuyerNotice buyerNotice = cereBuyerNoticeDAO.selectBuyerNotice(noticeId, buyerUserId);
            if (buyerNotice != null) {
                buyerNotice.setStatus(1);
                cereBuyerNoticeDAO.updateStatus(buyerNotice);
            } else {
                buyerNotice = new CereBuyerNotice();
                buyerNotice.setBuyerUserId(buyerUserId);
                buyerNotice.setNoticeId(noticeId);
                buyerNotice.setStatus(1);
                cereBuyerNoticeDAO.insert(buyerNotice);
            }
        }
    }

    @Override
    public void readNotice(Long noticeId, Long buyerUserId) {
        CereNotice cereNotice = cereNoticeDAO.selectByPrimaryKey(noticeId);
        if (cereNotice == null) {
            return;
        }
        // 公告和站内信，已读 需要 记录到 cere_buyer_notice表
        if (!(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode().equals(cereNotice.getNoticeType()))) {
            CereBuyerNotice buyerNotice = cereBuyerNoticeDAO.selectBuyerNotice(noticeId, buyerUserId);
            if (buyerNotice == null) {
                buyerNotice = new CereBuyerNotice();
                buyerNotice.setNoticeId(noticeId);
                buyerNotice.setBuyerUserId(buyerUserId);
                cereBuyerNoticeDAO.insert(buyerNotice);
            }
        } else {
            // 未读状态
            if (!IntegerEnum.YES.getCode().equals(cereNotice.getIfRead())) {
                cereNotice.setIfRead(IntegerEnum.YES.getCode());
                cereNoticeDAO.updateByPrimaryKeySelective(cereNotice);
            }
        }
    }
}
