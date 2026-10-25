/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.canvas.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.canvas.CerePlatformCanvasPageDAO;
import com.shop.zkmall.admin.param.canvas.CanvasPageQueryParam;
import com.shop.zkmall.admin.service.canvas.CerePlatformCanvasPageService;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import com.shop.zkmall.commons.domain.common.Page;
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
public class CerePlatformCanvasPageServiceImpl implements CerePlatformCanvasPageService {

    private final CerePlatformCanvasPageDAO cerePlatformCanvasPageDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveCanvasPage(CerePlatformCanvasPage canvas, CerePlatformUser user) {
        String describe;
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(canvas.getCanvasId())) {
            describe = "更新画布";
            canvas.setUpdateTime(time);
            //更新画布
            cerePlatformCanvasPageDAO.updateByPrimaryKeySelective(canvas);
        } else {
            describe = "新增画布";
            canvas.setCreateTime(time);
            canvas.setUpdateTime(time);
            //新增画布
            cerePlatformCanvasPageDAO.insertSelective(canvas);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "画布管理", "平台端操作", describe, String.valueOf(canvas.getCanvasId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public void delCanvasPage(CerePlatformCanvasPage canvas, CerePlatformUser user) {
        cerePlatformCanvasPageDAO.deleteByPrimaryKey(canvas.getCanvasId());
        //新增日志
        cerePlatformLogService.addLog(user, "画布管理", "平台端操作", "删除画布", String.valueOf(canvas.getCanvasId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public Page<CerePlatformCanvasPage> getAll(CanvasPageQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformCanvasPage> list = cerePlatformCanvasPageDAO.selectHomePageCanvasList(param);
        PageInfo<CerePlatformCanvasPage> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public CerePlatformCanvasPage getCanvas(CanvasPageQueryParam canvas) {
        return cerePlatformCanvasPageDAO.selectByPrimaryKey(canvas.getCanvasId());
    }

    @Override
    public int settingHomePage(CanvasPageQueryParam param, CerePlatformUser user) {
        int cnt = cerePlatformCanvasPageDAO.cleanHomePage(param.getTerminal());
        cnt += cerePlatformCanvasPageDAO.settingHomePage(param.getCanvasId());
        //新增日志
        cerePlatformLogService.addLog(user, "新画布管理", "平台端操作", "设置为首页", String.valueOf(param.getCanvasId()), TimeUtils.yyMMddHHmmss());
        return cnt;
    }

    @Override
    public List<CerePlatformCanvasPage> getTemplatePage(CanvasPageQueryParam param) {
        return cerePlatformCanvasPageDAO.getTemplatePage(param);
    }
}
