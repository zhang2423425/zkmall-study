/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.business;

import com.shop.zkmall.business.page.shop.PlatformBusiness;
import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.param.business.BusinessForgetParam;
import com.shop.zkmall.business.param.user.*;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformBusinessService {

    PlatformBusiness findByUserName(String username);

    PlatformBusiness findByPhone(String phone);

    PlatformBusiness findById(Long userId);

    void forgetPassword(BusinessForgetParam user, CerePlatformBusiness cerePlatformBusiness);

    CerePlatformBusiness findByToken(String token);

    void insert(CerePlatformBusiness cerePlatformBusiness);

    void updateToken(CerePlatformBusiness cerePlatformBusiness);

    void save(BusinessSaveUser business, CerePlatformBusiness user);

    void update(BusinessUpdateUser business, CerePlatformBusiness user);

    Business getById(Long businessUserId);

    void delete(BusinessDeleteUser param, CerePlatformBusiness user);

    Page<CerePlatformBusiness> getAll(BusinessGetAllUser param);

    CerePlatformBusiness checkUserName(String shopPhone, Long id);

    void updatePassword(BusinessForgetParam passwordParam, CerePlatformBusiness user);

    void updateAvatar(CerePlatformBusiness user);

    void updatePhone(UserUpdatePhoneParam param, CerePlatformBusiness user);

    String findAdminPhone(Long shopId);
}
