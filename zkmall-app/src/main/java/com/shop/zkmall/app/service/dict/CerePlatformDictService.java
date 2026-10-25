/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.dict;

import com.shop.zkmall.commons.domain.dict.CerePlatformDict;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformDictService {

    List<CerePlatformDict> getExpressSelect();

    List<String> getReasonSelect();

    String findByCompany(String express, Long dictPid);

    CerePlatformDict getByName(String name);

    Map<String, CerePlatformDict> getDictMap(List<String> nameList);

    List<CerePlatformDict> getSelect(String dictName);

    Map<Long, String> selectDictNameMap(List<Long> dictIdList);
}
