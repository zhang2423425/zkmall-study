package com.shop.zkmall.admin.service.user;

import com.shop.zkmall.commons.domain.user.CerePlatformUser;

public interface CerePlatformUserService {

    CerePlatformUser findByPhone(String phone);


    CerePlatformUser findById(long lL);

    CerePlatformUser findByUsername(String username);
}
