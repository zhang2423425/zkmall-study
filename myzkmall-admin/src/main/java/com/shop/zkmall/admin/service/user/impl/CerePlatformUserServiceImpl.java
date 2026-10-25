package com.shop.zkmall.admin.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.admin.dao.user.CerePlatformUserDAO;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CerePlatformUserServiceImpl implements CerePlatformUserService {

    private final CerePlatformUserDAO cerePlatformUserDao;

    @Override
    public CerePlatformUser findByPhone(String phone) {

        return cerePlatformUserDao.findByPhone(phone);

    }

    @Override
    public CerePlatformUser findById(long lL) {
        PlatformUser user = cerePlatformUserDao.getById(1L);
        CerePlatformUser cerePlatformUser = BeanUtil.copyProperties(user, CerePlatformUser.class);
        return cerePlatformUser;


    }

    @Override
    public CerePlatformUser findByUsername(String username) {
        return cerePlatformUserDao.findByUserName(username);
    }
}
