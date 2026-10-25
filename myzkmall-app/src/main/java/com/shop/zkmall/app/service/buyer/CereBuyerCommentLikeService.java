/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer;

import com.shop.zkmall.commons.domain.buyer.CereBuyerCommentLike;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerCommentLikeService {

    CereBuyerCommentLike findByUserIdAndCommentId(Long buyerUserId, Long commentId);

    void insert(CereBuyerCommentLike like);

    void update(CereBuyerCommentLike like);

    void updateBuyerData(Long buyerUserId, Long id);

    void insertOrUpdate(Long buyerUserId, Long commentId, Integer ifLike);
}
