/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.buyer.CereBuyerReceiveDAO;
import com.shop.zkmall.app.service.buyer.CereBuyerReceiveService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
public class CereBuyerReceiveServiceImpl implements CereBuyerReceiveService {

    private final CereBuyerReceiveDAO cereBuyerReceiveDAO;

    @Cacheable(value = "config", cacheManager = "caffeineCacheManager")
    @Override
    public CereBuyerReceive findlatelyReceiveByUserId(Long buyerUserId) {
        return cereBuyerReceiveDAO.findlatelyReceiveByUserId(buyerUserId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public CereBuyerReceive save(CereBuyerReceive receive, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        receive.setCreateTime(time);
        receive.setUpdateTime(time);
        receive.setBuyerUserId(user.getBuyerUserId());
        if (IntegerEnum.YES.getCode().equals(receive.getIfDefault())) {
            //如果设置为默认,查询是否有其他默认地址
            CereBuyerReceive cereBuyerReceive = cereBuyerReceiveDAO.findDefaultByUserIdAndSelf(user.getBuyerUserId(), null);
            if (cereBuyerReceive != null) {
                //更新为非默认
                cereBuyerReceive.setIfDefault(IntegerEnum.NO.getCode());
                cereBuyerReceiveDAO.updateByPrimaryKeySelective(cereBuyerReceive);
            }
        }
        cereBuyerReceiveDAO.insert(receive);
        return receive;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CereBuyerReceive receive, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        receive.setUpdateTime(time);
        if (IntegerEnum.YES.getCode().equals(receive.getIfDefault())) {
            //如果设置为默认,查询是否有其他默认地址
            CereBuyerReceive cereBuyerReceive = cereBuyerReceiveDAO.findDefaultByUserIdAndSelf(user.getBuyerUserId(), receive.getReceiveId());
            if (cereBuyerReceive != null) {
                //更新为非默认
                cereBuyerReceive.setIfDefault(IntegerEnum.NO.getCode());
                cereBuyerReceiveDAO.updateByPrimaryKeySelective(cereBuyerReceive);
            }
        }
        cereBuyerReceiveDAO.updateByPrimaryKeySelective(receive);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long receiveId, CereBuyerUser user) {
        cereBuyerReceiveDAO.deleteByPrimaryKey(receiveId);
    }

    @Override
    public CereBuyerReceive getById(Long receiveId) {
        return cereBuyerReceiveDAO.getById(receiveId);
    }

    @Override
    public Page<CereBuyerReceive> getAll(PageParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CereBuyerReceive> list = cereBuyerReceiveDAO.getAll(user.getBuyerUserId());
        PageInfo<CereBuyerReceive> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public List<CereBuyerReceive> getSelect(Long buyerUserId) {
        return cereBuyerReceiveDAO.getAll(buyerUserId);
    }

    @Override
    @Cacheable(value = "config", cacheManager = "caffeineCacheManager")
    public CereBuyerReceive findById(Long receiveId) {
        return cereBuyerReceiveDAO.findById(receiveId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerReceiveDAO.updateBuyerData(buyerUserId, id);
    }
}
