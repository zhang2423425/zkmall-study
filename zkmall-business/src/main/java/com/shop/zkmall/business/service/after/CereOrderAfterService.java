/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.after;

import com.shop.zkmall.business.page.after.After;
import com.shop.zkmall.business.param.after.AfterGetAllParam;
import com.shop.zkmall.business.param.after.AfterIdParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderAfterService {

    Page<After> getAll(AfterGetAllParam param);

    After getById(Long afterId);

    void refundSuccess(AfterIdParam param, CerePlatformBusiness user) throws Exception;

    void refundRefuse(AfterIdParam param, CerePlatformBusiness user);

    void success(AfterIdParam param, CerePlatformBusiness user);

    void refuse(AfterIdParam param, CerePlatformBusiness user);

    void confirmAndRefund(AfterIdParam param, CerePlatformBusiness user) throws Exception;

    void damaging(AfterIdParam param, CerePlatformBusiness user);

    void handleRefundSuc(String s, String transactionId, String orderNo, Integer paymentMode);

    void refundError(String orderFormid);

    List<Integer> selectAfterStateList(Long orderId);
}
