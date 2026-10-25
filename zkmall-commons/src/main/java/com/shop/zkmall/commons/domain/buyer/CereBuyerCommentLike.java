/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.buyer;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_comment_like 客户评论点赞信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereBuyerCommentLike implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 点赞客户id
     */
    private Long buyerUserId;

    /**
     * 是否点赞 1-是 0-否
     */
    private Integer ifLike;

}
