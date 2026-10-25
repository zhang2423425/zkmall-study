/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.log.impl;

import com.shop.zkmall.app.dao.log.CerePlatformLogDAO;
import com.shop.zkmall.app.service.log.CerePlatformLogService;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.log.CerePlatformLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformLogServiceImpl implements CerePlatformLogService {

    private final CerePlatformLogDAO cerePlatformLogDAO;

    @Override
    public void addLog(CereBuyerUser user, String module, String operation, String describe, Long id, String time) {
        CerePlatformLog log = new CerePlatformLog();
        log.setPlatformUserId(user.getBuyerUserId());
        log.setModule(module);
        log.setOperation(operation);
        log.setOperationDescribtion(describe);
        log.setOnly(String.valueOf(id));
        log.setCreateTime(time);
        cerePlatformLogDAO.insert(log);
    }
}
