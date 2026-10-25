/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after;


import com.shop.zkmall.app.page.after.AfterDetail;
import com.shop.zkmall.app.page.after.Afters;
import com.shop.zkmall.app.param.after.AfterGetAllParam;
import com.shop.zkmall.app.param.after.AfterGetByIdParam;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.app.param.after.PlatformParam;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderAfterService {

    void submit(AfterParam param, CereBuyerUser user) throws Exception;

    BigDecimal getReturnPrice(AfterParam param, CereBuyerUser user);

    Page<Afters> getAll(AfterGetAllParam param, CereBuyerUser user);

    AfterDetail getById(Long afterId) throws Exception;

    void returnRefund(AfterGetByIdParam param, CereBuyerUser user);

    void returnGoods(AfterGetByIdParam param, CereBuyerUser user);

    void platform(PlatformParam param, CereBuyerUser user);

    void update(CereOrderAfter cereOrderAfter);

    Long getPostSaleNumByMonth(Integer hours, Long buyerUserId);

    void deleteAfterById(Long id);

    List<Integer> selectAfterStateList(Long orderId);

    int findAfterCount(Long buyerUserId);

    List<CereOrderAfter> selectByOrderIdList(List<Long> orderIdList);
}
