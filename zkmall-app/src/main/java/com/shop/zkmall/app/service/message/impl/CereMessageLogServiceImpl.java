/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.message.impl;

import com.shop.zkmall.app.dao.message.CereMessageLogDAO;
import com.shop.zkmall.app.service.message.CereMessageLogService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.message.CereMessageLog;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereMessageLogServiceImpl implements CereMessageLogService {

    private final CereMessageLogDAO cereMessageLogDAO;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(String phone, String template, Map<String, String> map, String subject, String resultCode) {
        String time = TimeUtils.yyMMddHHmmss();
        CereMessageLog log = new CereMessageLog();
        log.setCreateTime(time);
        log.setPhone(phone);
        log.setTemplate(template);
        log.setSubject(subject);
        if (StringEnum.MESSAGE_RESULT_CODE.getCode().equals(resultCode)) {
            //发送成功
            log.setState(IntegerEnum.MESSAGE_SUCCESS.getCode());
        } else {
            //发送失败
            log.setState(IntegerEnum.MESSAGE_ERROR.getCode());
        }
        //根据模板编码查询短信所属信息
        CerePlatfromMessage message = cereMessageLogDAO.findMessage(template);
        if (message != null) {
            log.setMessageId(message.getMessageId());
            log.setMessage(message.getTemplateDescribe());
            //设置内容,替换占位符
            if (!EmptyUtils.isEmpty(map)) {
                if (message.getTemplateDescribe().contains("{")) {
                    String format = MessageFormat.format(message.getTemplateDescribe(), map);
                    log.setMessage(format);
                }
            }
            cereMessageLogDAO.insert(log);
        }
    }
}
