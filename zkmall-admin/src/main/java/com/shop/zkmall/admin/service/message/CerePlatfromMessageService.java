/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.message;

import com.shop.zkmall.admin.param.message.MessageDeleteParam;
import com.shop.zkmall.admin.param.message.MessageGetAllParam;
import com.shop.zkmall.admin.param.message.MessageSaveParam;
import com.shop.zkmall.admin.param.message.MessageUpdateParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatfromMessageService {

    void save(MessageSaveParam param, CerePlatformUser user);

    void update(MessageUpdateParam param, CerePlatformUser user);

    void delete(MessageDeleteParam param, CerePlatformUser user);

    CerePlatfromMessage getById(Long messageId);

    Page<CerePlatfromMessage> getAll(MessageGetAllParam param);

}
