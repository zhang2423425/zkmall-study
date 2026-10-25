/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.label;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_label 客户关联标签信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereBuyerLabel implements Serializable {
    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 关联平台标签id
     */
    private Long buyerLabelId;

}
