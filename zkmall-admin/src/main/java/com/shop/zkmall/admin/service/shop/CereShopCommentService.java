/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop;

import com.shop.zkmall.admin.page.shop.CommentDetail;
import com.shop.zkmall.admin.page.shop.ShopComment;
import com.shop.zkmall.admin.param.comment.CommentDeleteParam;
import com.shop.zkmall.admin.param.comment.CommentGetAllParam;
import com.shop.zkmall.admin.param.comment.CommentUpdateParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopCommentService {

    Page<ShopComment> getAll(CommentGetAllParam param);

    CommentDetail getById(Long commentId) throws Exception;

    void update(CommentUpdateParam param, CerePlatformUser user);

    void delete(CommentDeleteParam param, CerePlatformUser user);

    void allow(CommentUpdateParam param, CerePlatformUser user);

    BigDecimal selectShopStar(Long shopId);
}
