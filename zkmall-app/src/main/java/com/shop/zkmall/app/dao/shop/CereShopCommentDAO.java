/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.comment.Comment;
import com.shop.zkmall.app.page.comment.SelfComment;
import com.shop.zkmall.app.page.comment.WordComment;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.product.BuyerComment;
import com.shop.zkmall.app.param.comment.CommentParam;
import com.shop.zkmall.app.param.comment.CommentWorParam;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopCommentDAO extends BaseMapper<CereShopComment> {

    int insertSelective(CereShopComment record);

    List<BuyerComment> findByProductId(@Param("productId") Long productId);

    CereShopComment findShop(@Param("productId") Long productId);

    List<SelfComment> getAll(CommentParam param);

    Integer findTotal(@Param("buyerUserId") Long buyerUserId, @Param("state") Integer state);

    CereShopComment findByOrderId(@Param("orderId") Long orderId);

    List<CerePlatformWord> findWords();

    Comment getById(@Param("commentId") Long commentId);

    List<WordComment> getProductAll(CommentWorParam param);

    int findProductNumber(@Param("orderId") Long orderId);

    void updateBuyerData(@Param("buyerUserId") Long buyerUserId, @Param("id") Long id);

    CereShopComment findComment(@Param("buyerUserId") Long buyerUserId, @Param("orderId") Long orderId, @Param("skuId") Long skuId);

    List<SelfComment> getCommentList(CommentParam param);

    List<SelfComment> getUnCommentList(CommentParam param);

    List<BroadCastDTO> findRecentComment(@Param("productId") Long productId,
                                         @Param("oneHourAgo") String oneHourAgo);

    BigDecimal selectShopStar(Long shopId);
}
