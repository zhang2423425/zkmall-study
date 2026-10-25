/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.log;

import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformLogService {

    void addLog(CerePlatformUser user, String module, String operation, String describe, String id, String time);

}
