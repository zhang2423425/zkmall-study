/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.shop;

import com.shop.zkmall.app.page.comment.Comment;
import com.shop.zkmall.app.page.comment.CommentCount;
import com.shop.zkmall.app.page.comment.WordComment;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.product.BuyerComment;
import com.shop.zkmall.app.param.comment.CommentParam;
import com.shop.zkmall.app.param.comment.CommentWorParam;
import com.shop.zkmall.app.param.comment.LikeParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopCommentService {

    List<BuyerComment> findByProductId(Long productId);

    CereShopComment findShop(Long productId);

    void insert(CereShopComment cereShopComment);

    CommentCount getAll(CommentParam param, CereBuyerUser user);

    void like(LikeParam param, CereBuyerUser user);

    void update(CereShopComment cereShopComment);

    CereShopComment findByOrderId(Long orderId);

    List<CerePlatformWord> findWords();

    Comment getById(Long commentId);

    Page<WordComment> getProductAll(CommentWorParam param) throws Exception;

    int findProductNumber(Long orderId);

    void updateBuyerData(Long buyerUserId, Long id);

    CereShopComment findComment(Long buyerUserId, Long orderId, Long skuId);

    CommentCount getCommentList(CommentParam param, CereBuyerUser user);

    long findCommentCount(Long buyerUserId);

    List<BroadCastDTO> findRecentComment(Long productId, String oneHourAgo);

    BigDecimal selectShopStar(Long shopId);
}
