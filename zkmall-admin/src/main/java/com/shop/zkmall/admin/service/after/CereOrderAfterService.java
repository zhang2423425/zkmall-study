/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.after;

import com.shop.zkmall.admin.page.after.After;
import com.shop.zkmall.admin.page.after.AfterBuyer;
import com.shop.zkmall.admin.page.after.AfterDetail;
import com.shop.zkmall.admin.page.after.AfterDilevery;
import com.shop.zkmall.admin.param.after.AfterGetAllParam;
import com.shop.zkmall.admin.param.after.AfterGetDileveryParam;
import com.shop.zkmall.admin.param.after.AfterhandleParam;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderAfterService {

    Page<After> getAll(AfterGetAllParam param);

    AfterDetail getById(Long afterId) throws Exception;

    AfterBuyer getBuyer(Long buyerUserId);

    void handle(AfterhandleParam param, CerePlatformUser user) throws Exception;

    List<AfterDilevery> getDilevery(AfterGetDileveryParam param) throws Exception;

    void insert(CereOrderAfter cereOrderAfter);
}
