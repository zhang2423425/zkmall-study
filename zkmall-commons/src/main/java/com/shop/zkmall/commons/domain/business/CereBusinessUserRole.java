/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.business;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_business_user_role 商家用户关联角色实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereBusinessUserRole implements Serializable {
    /**
     * 商家用户id
     */
    private Long businessUserId;

    /**
     * 关联角色表id
     */
    private Long roleId;

}
