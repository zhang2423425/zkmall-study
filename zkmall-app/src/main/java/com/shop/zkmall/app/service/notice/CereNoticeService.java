/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.notice;

import com.shop.zkmall.app.page.notice.BuyerNotice;
import com.shop.zkmall.app.page.notice.NoticeDetail;
import com.shop.zkmall.app.param.notice.NoticeIdParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.domain.notice.CereNotice;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereNoticeService {

    Page<BuyerNotice> getAll(PageParam pageParam, CereBuyerUser user);

    NoticeDetail getById(NoticeIdParam param);

    List<BuyerNotice> getGongGaoAll(CereBuyerUser user);

    void insert(CereNotice cereNotice);

    void insertBatch(List<CereNotice> notices);

    void updateBuyerData(Long buyerUserId, Long id);

    int getNotRead(Long buyerUserId, String createTime);

    void update(CereNotice cereNotice);

    @Deprecated
    int getRead(Long buyerUserId);

    List<Long> findIdsByOrderId(Long orderId);

    void deleteByIds(List<Long> noticeIds);

    void deleteBuyerNotice(List<Long> noticeIds, Long buyerUserId);

    List<BuyerNotice> getNotices();

    void readAll(Long buyerUserId);

    void removeById(Long noticeId, Long buyerUserId);

    void readNotice(Long noticeId, Long buyerUserId);
}
