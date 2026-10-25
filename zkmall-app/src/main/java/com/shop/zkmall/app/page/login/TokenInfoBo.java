/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.login;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户token信息
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@AllArgsConstructor
public class TokenInfoBo {

    private Long buyerUserId;

    private String token;

    private String refreshToken;

}
