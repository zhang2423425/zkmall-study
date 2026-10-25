/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.shop.CereShopCommentDAO;
import com.shop.zkmall.admin.page.shop.CommentDetail;
import com.shop.zkmall.admin.page.shop.ShopComment;
import com.shop.zkmall.admin.param.comment.CommentDeleteParam;
import com.shop.zkmall.admin.param.comment.CommentGetAllParam;
import com.shop.zkmall.admin.param.comment.CommentUpdateParam;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.shop.CereShopCommentService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopCommentServiceImpl implements CereShopCommentService {

    private final CereShopCommentDAO cereShopCommentDAO;

    private final CerePlatformLogService cerePlatformLogService;


    @Override
    public Page<ShopComment> getAll(CommentGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<ShopComment> list = cereShopCommentDAO.getAll(param);
        list.forEach(s -> {
            String name = s.getName();
            if (name != null) {
                String desensitizationName = StringUtils.showStartAndEnd(name, 1, 1);
                s.setName(desensitizationName);
            }
        });
        PageInfo<ShopComment> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public CommentDetail getById(Long commentId) throws Exception {
        //查询评论详情
        CommentDetail detail = cereShopCommentDAO.getById(commentId);
        if (detail != null) {
            if (!EmptyUtils.isEmpty(detail.getAddComment())) {
                //计算评价和追评时间之差
                detail.setTime(TimeUtils.differentDaysByMillisecond(detail.getCreateTime(), detail.getAddTime()));
            }
            //查询敏感词
            detail.setSensitiveWord(cereShopCommentDAO.findSensitiveWord());
            //设置图片数组
            if (!EmptyUtils.isEmpty(detail.getImage())) {
                detail.setImages(Arrays.asList(detail.getImage().split(",")));
            }
            //设置追加图片数组
            if (!EmptyUtils.isEmpty(detail.getAddImage())) {
                detail.setAddImages(Arrays.asList(detail.getAddImage().split(",")));
            }
        }
        return detail;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CommentUpdateParam param, CerePlatformUser user) {
        //显示或隐藏评论
        String time = TimeUtils.yyMMddHHmmss();
        CereShopComment comment = new CereShopComment();
        comment.setUpdateTime(time);
        comment.setCommentId(param.getCommentId());
        comment.setState(param.getState());
        cereShopCommentDAO.updateById(comment);
        //新增日志
        cerePlatformLogService.addLog(user, "评论管理", "平台端操作", "修改评论", String.valueOf(comment.getCommentId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(CommentDeleteParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereShopCommentDAO.deleteById(param.getCommentId());
        //新增日志
        cerePlatformLogService.addLog(user, "评论管理", "平台端操作", "删除评论", String.valueOf(param.getCommentId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void allow(CommentUpdateParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopComment cereShopComment = new CereShopComment();
        cereShopComment.setCommentId(param.getCommentId());
        cereShopComment.setIfSensitive(IntegerEnum.YES.getCode());
        cereShopComment.setState(IntegerEnum.NO.getCode());
        cereShopComment.setUpdateTime(time);
        cereShopCommentDAO.updateById(cereShopComment);
        //新增日志
        cerePlatformLogService.addLog(user, "评论管理", "平台端操作", "允许展示评论", String.valueOf(param.getCommentId()), time);
    }

    @Override
    public BigDecimal selectShopStar(Long shopId) {
        return cereShopCommentDAO.selectShopStar(shopId);
    }
}
