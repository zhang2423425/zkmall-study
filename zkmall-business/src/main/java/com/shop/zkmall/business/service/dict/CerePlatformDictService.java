/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.dict;

import com.shop.zkmall.commons.domain.dict.CerePlatformDict;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformDictService {

    List<CerePlatformDict> getExpressSelect();

    List<CerePlatformDict> getSelect(String dictName);
}
