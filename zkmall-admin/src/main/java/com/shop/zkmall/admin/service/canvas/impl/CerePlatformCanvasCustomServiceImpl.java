/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.canvas.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.canvas.CerePlatformCanvasCustomDAO;
import com.shop.zkmall.admin.dao.canvas.CerePlatformCanvasDAO;
import com.shop.zkmall.admin.param.canvas.CanvasPageParam;
import com.shop.zkmall.admin.service.canvas.CerePlatformCanvasCustomService;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasCustom;
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
public class CerePlatformCanvasCustomServiceImpl implements CerePlatformCanvasCustomService {

    private final CerePlatformCanvasCustomDAO cerePlatformCanvasCustomDAO;

    private final CerePlatformCanvasDAO cerePlatformCanvasDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) {
        String describe;
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(canvasCustom.getId())) {
            describe = "更新自定义页面";
            canvasCustom.setUpdateTime(time);
            //更新自定义页面
            cerePlatformCanvasCustomDAO.updateByPrimaryKeySelective(canvasCustom);
        } else {
            describe = "新增自定义页面";
            canvasCustom.setCreateTime(time);
            canvasCustom.setUpdateTime(time);
            //新增自定义页面
            cerePlatformCanvasCustomDAO.insertSelective(canvasCustom);
            //4种终端都新增对应的画布
            for (int i = 1; i <= 4; i++) {
                CerePlatformCanvas canvas = new CerePlatformCanvas();
                canvas.setCreateTime(time);
                canvas.setUpdateTime(time);
                canvas.setJson("[]");
                canvas.setName("");
                canvas.setShopId(0L);
                canvas.setTerminal(i);
                canvas.setType(2);
                canvas.setCustomId(canvasCustom.getId());
                cerePlatformCanvasDAO.insertSelective(canvas);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "画布管理", "平台端操作", describe, String.valueOf(canvasCustom.getId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public void delCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) {
        cerePlatformCanvasCustomDAO.deleteByPrimaryKey(canvasCustom.getId());
        //删除自定义页面
        cerePlatformCanvasDAO.deleteByCustomId(canvasCustom.getId());
        //新增日志
        cerePlatformLogService.addLog(user, "画布管理", "平台端操作", "删除自定义页面", String.valueOf(canvasCustom.getId()), TimeUtils.yyMMddHHmmss());

    }

    @Override
    public Page<CerePlatformCanvasCustom> selectCanvasCustomList(CanvasPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformCanvasCustom> list = cerePlatformCanvasCustomDAO.selectCanvasCustomList(param);
        PageInfo<CerePlatformCanvasCustom> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }
}
