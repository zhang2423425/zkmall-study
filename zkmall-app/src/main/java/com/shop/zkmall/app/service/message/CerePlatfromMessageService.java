/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.message;

import com.shop.zkmall.app.param.message.MessageDeleteParam;
import com.shop.zkmall.app.param.message.MessageGetAllParam;
import com.shop.zkmall.app.param.message.MessageSaveParam;
import com.shop.zkmall.app.param.message.MessageUpdateParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatfromMessageService {

    void save(MessageSaveParam param, CereBuyerUser user);

    void update(MessageUpdateParam param, CereBuyerUser user);

    void delete(MessageDeleteParam param, CereBuyerUser user);

    CerePlatfromMessage getById(Long messageId);

    Page<CerePlatfromMessage> getAll(MessageGetAllParam param);
}
