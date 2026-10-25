/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.sensitive;

import com.shop.zkmall.admin.param.sensitive.SensitiveSaveParam;
import com.shop.zkmall.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.zkmall.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformSensitiveService {

    void save(SensitiveSaveParam param, CerePlatformUser user);

    void update(SensitiveUpdateParam param, CerePlatformUser user);

    List<CerePlatformSensitive> get();

}
