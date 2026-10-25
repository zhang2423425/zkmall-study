/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.utils;

import cn.hutool.core.codec.Base64;
import com.shop.zkmall.business.dao.business.CereScrmVerifyDAO;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CereScrmVerify;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.third.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
@RequiredArgsConstructor
public class ScrmSyncVerifyUtil {

    private final CereScrmVerifyDAO cereScrmVerifyDAO;

    /**
     * 校验秘钥是否正确
     */
    public void verify(Long shopId, String secret) {
        String encodeSecret = Tools.md5(Base64.encode(secret.getBytes()));
        CereScrmVerify verify = cereScrmVerifyDAO.selectById(shopId);
        if (verify == null) {
            throw new CoBusinessException(CoReturnFormat.SECRET_ERROR);
        }
        if (!encodeSecret.equals(verify.getEncodeSecret().toLowerCase())) {
            throw new CoBusinessException(CoReturnFormat.SECRET_ERROR);
        }
    }

}
