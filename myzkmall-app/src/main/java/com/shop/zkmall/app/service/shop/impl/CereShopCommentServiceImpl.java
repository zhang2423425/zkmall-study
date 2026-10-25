/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.shop.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.shop.CereShopCommentDAO;
import com.shop.zkmall.app.page.comment.Comment;
import com.shop.zkmall.app.page.comment.CommentCount;
import com.shop.zkmall.app.page.comment.SelfComment;
import com.shop.zkmall.app.page.comment.WordComment;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.product.BuyerComment;
import com.shop.zkmall.app.param.comment.CommentParam;
import com.shop.zkmall.app.param.comment.CommentWorParam;
import com.shop.zkmall.app.param.comment.LikeParam;
import com.shop.zkmall.app.service.buyer.CereBuyerCommentLikeService;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopCommentServiceImpl implements CereShopCommentService {

    private final CereShopCommentDAO cereShopCommentDAO;

    private final CereBuyerCommentLikeService cereBuyerCommentLikeService;

    @Cacheable(value = "config", cacheManager = "caffeineCacheManager")
    @Override
    public List<BuyerComment> findByProductId(Long productId) {
        return cereShopCommentDAO.findByProductId(productId);
    }

    @Override
    public CereShopComment findShop(Long productId) {
        return cereShopCommentDAO.findShop(productId);
    }

    @Override
    public void insert(CereShopComment cereShopComment) {
        cereShopCommentDAO.insert(cereShopComment);
    }

    @Override
    public CommentCount getAll(CommentParam param, CereBuyerUser user) {
        CommentCount commentCount = new CommentCount();
        //查询全部评价数量
        commentCount.setTotal(cereShopCommentDAO.findTotal(user.getBuyerUserId(), null));
        //查询有图评价数量
        commentCount.setImageTotal(cereShopCommentDAO.findTotal(user.getBuyerUserId(), IntegerEnum.YES.getCode()));
        param.setBuyerUserId(user.getBuyerUserId());
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<SelfComment> list = cereShopCommentDAO.getAll(param);
        if (!EmptyUtils.isEmpty(list)) {
            list.forEach(selfComment -> {
                if (!EmptyUtils.isEmpty(selfComment.getAddTime())) {
                    selfComment.setIfAdd(IntegerEnum.YES.getCode());
                } else {
                    selfComment.setIfAdd(IntegerEnum.NO.getCode());
                }
                //设置规格属性值数组
                selfComment.setValues(EmptyUtils.getImages(selfComment.getValue()));
            });
        }
        PageInfo<SelfComment> pageInfo = new PageInfo<>(list);
        Page<SelfComment> page = new Page<>(pageInfo.getList(), pageInfo.getTotal());
        commentCount.setPage(page);
        return commentCount;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void like(LikeParam param, CereBuyerUser user) {
        cereBuyerCommentLikeService.insertOrUpdate(user.getBuyerUserId(), param.getCommentId(), param.getIfLike());
    }

    @Override
    public void update(CereShopComment cereShopComment) {
        cereShopCommentDAO.updateById(cereShopComment);
    }

    @Override
    public CereShopComment findByOrderId(Long orderId) {
        return cereShopCommentDAO.findByOrderId(orderId);
    }

    @Override
    public List<CerePlatformWord> findWords() {
        return cereShopCommentDAO.findWords();
    }

    @Override
    public Comment getById(Long commentId) {
        Comment comment = cereShopCommentDAO.getById(commentId);
        if (comment != null && !EmptyUtils.isEmpty(comment.getImage())) {
            comment.setImages(EmptyUtils.getImages(comment.getImage()));
        }
        return comment;
    }

    @Override
    public Page<WordComment> getProductAll(CommentWorParam param) throws Exception {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<WordComment> list = cereShopCommentDAO.getProductAll(param);
        if (!EmptyUtils.isEmpty(list)) {
            for (WordComment wordComment : list) {
                //计算追评时间和评价时间之间天数
                if (!EmptyUtils.isEmpty(wordComment.getAddTime())) {
                    wordComment.setDay(TimeUtils.differentDaysByMillisecond(wordComment.getCreateTime(), wordComment.getAddTime()));
                }
                //设置图片数组
                if (!EmptyUtils.isEmpty(wordComment.getImage())) {
                    wordComment.setImages(EmptyUtils.getImages(wordComment.getImage()));
                }
                if (!EmptyUtils.isEmpty(wordComment.getAddImage())) {
                    wordComment.setAddImages(EmptyUtils.getImages(wordComment.getAddImage()));
                }
            }
        }
        PageInfo<WordComment> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public int findProductNumber(Long orderId) {
        return cereShopCommentDAO.findProductNumber(orderId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereShopCommentDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    public CereShopComment findComment(Long buyerUserId, Long orderId, Long skuId) {
        return cereShopCommentDAO.findComment(buyerUserId, orderId, skuId);
    }

    @Override
    public CommentCount getCommentList(CommentParam param, CereBuyerUser user) {
        CommentCount commentCount = new CommentCount();
        param.setBuyerUserId(user.getBuyerUserId());
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<SelfComment> list = null;
        if (param.getType() == 1) {
            list = cereShopCommentDAO.getUnCommentList(param);
        } else {
            list = cereShopCommentDAO.getCommentList(param);
            if (!EmptyUtils.isEmpty(list)) {
                list.forEach(selfComment -> {
                    if (!EmptyUtils.isEmpty(selfComment.getAddTime())) {
                        selfComment.setIfAdd(IntegerEnum.YES.getCode());
                    } else {
                        selfComment.setIfAdd(IntegerEnum.NO.getCode());
                    }
                    //设置规格属性值数组
                    selfComment.setValues(EmptyUtils.getImages(selfComment.getValue()));
                });
            }
        }
        PageInfo<SelfComment> pageInfo = new PageInfo<>(list);
        Page<SelfComment> page = new Page<>(pageInfo.getList(), pageInfo.getTotal());
        commentCount.setPage(page);
        return commentCount;
    }

    @Override
    public long findCommentCount(Long buyerUserId) {
        return cereShopCommentDAO.selectCount(Wrappers.<CereShopComment>lambdaQuery().eq(CereShopComment::getBuyerUserId, buyerUserId));
    }

    @Override
    public List<BroadCastDTO> findRecentComment(Long productId, String oneHourAgo) {
        return cereShopCommentDAO.findRecentComment(productId, oneHourAgo);
    }

    @Override
    public BigDecimal selectShopStar(Long shopId) {
        BigDecimal star = cereShopCommentDAO.selectShopStar(shopId);
        if (star == null) {
            star = BigDecimal.ZERO;
        }
        return star.setScale(1, RoundingMode.HALF_UP);
    }
}
