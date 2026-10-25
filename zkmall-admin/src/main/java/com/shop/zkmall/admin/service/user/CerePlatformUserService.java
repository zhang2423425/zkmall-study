/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.user;

import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.param.user.*;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformUserService {

    CerePlatformUser findByUserName(String userName);

    CerePlatformUser findByPhone(String phone);

    CerePlatformUser findById(Long userId);

    void save(UserSaveParam userParam, CerePlatformUser user);

    void update(UserUpdateParam userParam, CerePlatformUser user);

    void delete(UserDeleteParam userParam, CerePlatformUser user);

    PlatformUser getById(Long platformUserId);

    Page<PlatformUser> getAll(UserGetAllParam userParam);

    CerePlatformUser findByToken(String token);

    void forgetPassword(UserForgetPasswordParam user, CerePlatformUser cerePlatformUser);

    void updateToken(CerePlatformUser cerePlatformUser);

    void updatePassword(UserForgetPasswordParam passwordParam, CerePlatformUser user);

    void updatePhone(UserUpdatePhoneParam param, CerePlatformUser user);

    String getAdminPhone();

    void updateAvatar(CerePlatformUser user);
}
