/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.sensitive.impl;

import com.shop.zkmall.admin.dao.sensitive.CerePlatformSensitiveDAO;
import com.shop.zkmall.admin.param.sensitive.SensitiveSaveParam;
import com.shop.zkmall.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.sensitive.CerePlatformSensitiveService;
import com.shop.zkmall.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformSensitiveServiceImpl implements CerePlatformSensitiveService {

    private final CerePlatformSensitiveDAO cerePlatformSensitiveDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(SensitiveSaveParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        String describe;
        CerePlatformSensitive sensitive = new CerePlatformSensitive();
        sensitive.setState(param.getState());
        sensitive.setHandleMeasures(param.getHandleMeasures());
        sensitive.setSensitiveWord(param.getSensitiveWord());
        if (!EmptyUtils.isEmpty(param.getId())) {
            //修改
            describe = "修改敏感词设置";
            sensitive.setId(param.getId());
            cerePlatformSensitiveDAO.updateById(sensitive);
        } else {
            //新增
            describe = "添加敏感词设置";
            cerePlatformSensitiveDAO.insert(sensitive);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "敏感词管理", "平台端操作", describe, String.valueOf(sensitive.getId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(SensitiveUpdateParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformSensitive sensitive = new CerePlatformSensitive();
        sensitive.setId(param.getId());
        sensitive.setState(param.getState());
        sensitive.setHandleMeasures(param.getHandleMeasures());
        sensitive.setSensitiveWord(param.getSensitiveWord());
        cerePlatformSensitiveDAO.updateById(sensitive);
        //新增日志
        cerePlatformLogService.addLog(user, "敏感词管理", "平台端操作", "修改敏感词设置", String.valueOf(sensitive.getId()), time);
    }

    @Override
    public List<CerePlatformSensitive> get() {
        return cerePlatformSensitiveDAO.get();
    }
}
