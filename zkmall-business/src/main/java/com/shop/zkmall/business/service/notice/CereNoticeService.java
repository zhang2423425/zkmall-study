/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.notice;

import com.shop.zkmall.business.page.operate.OperateCoupon;
import com.shop.zkmall.commons.domain.notice.CereNotice;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereNoticeService {

    void addOperate(Long shopCrowdId, List<OperateCoupon> coupons, Long shopId, Long shopOperateId);

    void insert(CereNotice cereNotice);
}
