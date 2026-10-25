/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.word;

import com.shop.zkmall.admin.param.word.WordParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformWordService {

    CerePlatformWord checkWord(String keyWord, Long wordId);

    void save(CerePlatformWord word, CerePlatformUser user);

    void update(CerePlatformWord word, CerePlatformUser user);

    void delete(CerePlatformWord word, CerePlatformUser user);

    CerePlatformWord getById(Long wordId);

    Page<CerePlatformWord> getAll(WordParam param);

    void start(Integer state);
}
