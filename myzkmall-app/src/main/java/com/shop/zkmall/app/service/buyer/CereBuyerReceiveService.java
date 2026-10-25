/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer;

import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerReceiveService {

    CereBuyerReceive findlatelyReceiveByUserId(Long buyerUserId);

    CereBuyerReceive save(CereBuyerReceive receive, CereBuyerUser user);

    void update(CereBuyerReceive receive, CereBuyerUser user);

    void delete(Long receiveId, CereBuyerUser user);

    CereBuyerReceive getById(Long receiveId);

    Page<CereBuyerReceive> getAll(PageParam param, CereBuyerUser user);

    List<CereBuyerReceive> getSelect(Long buyerUserId);

    CereBuyerReceive findById(Long receiveId);

    void updateBuyerData(Long buyerUserId, Long id);
}
