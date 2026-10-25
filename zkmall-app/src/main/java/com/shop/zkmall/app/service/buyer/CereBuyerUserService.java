/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer;

import com.shop.zkmall.app.page.buyer.MyUser;
import com.shop.zkmall.app.page.login.BuyerUser;
import com.shop.zkmall.app.param.buyer.UserParam;
import com.shop.zkmall.app.param.index.LoginParam;
import com.shop.zkmall.app.param.index.LoginPhoneParam;
import com.shop.zkmall.app.param.index.UpdateAliPhoneParam;
import com.shop.zkmall.app.param.index.UpdateWxPhoneParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerUserService {

    CereBuyerUser findByToken(String token);

    CereBuyerUser selectByBuyerUserId(Long buyerUserId);

    BuyerUser wxLogin(LoginParam param);

    BuyerUser alipayLogin(LoginParam param);

    BuyerUser login(LoginParam param);

    void insert(CereBuyerUser buyerUser);

    void update(CereBuyerUser buyerUser, CereBuyerUser user);

    void updatePhone(UserParam param, CereBuyerUser user);

    void relievePhone(UserParam param, CereBuyerUser user);

    void updatePassword(UserParam param, CereBuyerUser user);

    MyUser getUser(Long buyerUserId);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);

    BuyerUser setWxPhone(LoginPhoneParam param);

    BuyerUser updateWxPhone(UpdateWxPhoneParam param);

    BuyerUser wxAppLogin(LoginPhoneParam param);

    BuyerUser updateAliPhone(UpdateAliPhoneParam param);

    void updateGrowth(Long buyerUserId, Integer growth);

    void delete(CereBuyerUser user);

    void updateLastLoginIp(Long buyerUserId, String ip);
}
