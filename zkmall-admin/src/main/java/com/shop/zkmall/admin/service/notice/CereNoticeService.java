/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.notice;

import com.shop.zkmall.admin.param.notice.NoticeGetAllParam;
import com.shop.zkmall.admin.param.notice.NoticeIdParam;
import com.shop.zkmall.admin.param.notice.NoticeParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereNoticeService {

    void save(NoticeParam param, CerePlatformUser user);

    void delete(NoticeIdParam param, CerePlatformUser user);

    CereNotice getById(Long noticeId);

    Page<CereNotice> getAll(NoticeGetAllParam param);

    List<CereNotice> getNotices();
}
