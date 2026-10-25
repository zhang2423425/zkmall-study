/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_notice 客户已读公告、站内信消息关系实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereBuyerNotice implements Serializable {
    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 状态 0-未删除 1-已删除
     */
    private Integer status;

}
