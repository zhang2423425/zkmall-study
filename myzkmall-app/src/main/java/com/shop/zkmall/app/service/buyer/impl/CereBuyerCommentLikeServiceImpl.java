/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer.impl;

import com.shop.zkmall.app.dao.buyer.CereBuyerCommentLikeDAO;
import com.shop.zkmall.app.service.buyer.CereBuyerCommentLikeService;
import com.shop.zkmall.commons.domain.buyer.CereBuyerCommentLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerCommentLikeServiceImpl implements CereBuyerCommentLikeService {

    private final CereBuyerCommentLikeDAO cereBuyerCommentLikeDAO;

    @Cacheable(value = "config", cacheManager = "caffeineCacheManager")
    @Override
    public CereBuyerCommentLike findByUserIdAndCommentId(Long buyerUserId, Long commentId) {
        return cereBuyerCommentLikeDAO.findByUserIdAndCommentId(buyerUserId, commentId);
    }

    @Override
    public void insert(CereBuyerCommentLike like) {
        cereBuyerCommentLikeDAO.insert(like);
    }

    @Override
    public void update(CereBuyerCommentLike like) {
        cereBuyerCommentLikeDAO.updateByPrimaryKeySelective(like);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereBuyerCommentLikeDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public void insertOrUpdate(Long buyerUserId, Long commentId, Integer ifLike) {
        cereBuyerCommentLikeDAO.insertOrUpdate(buyerUserId, commentId, ifLike);
    }
}
