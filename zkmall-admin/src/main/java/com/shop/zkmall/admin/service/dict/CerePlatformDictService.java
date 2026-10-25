/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.dict;

import com.shop.zkmall.admin.param.dict.*;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformDictService {

    void save(DictSaveParam param, CerePlatformUser user);

    void update(DictUpdateParam param, CerePlatformUser user);

    void delete(DictDeleteParam param, CerePlatformUser user);

    CerePlatformDict getById(Long dictId);

    Page<CerePlatformDict> getAll(DictGetAllParam param);

    Page<CerePlatformDict> getChilds(DictGetChildsParam param);

    List<CerePlatformDict> getSelect(String dictName);

    CerePlatformDict checkName(String dictName);

    CerePlatformDict checkNameAndId(DictUpdateParam param);

    void importExpressCodes(Workbook wb) throws Exception;

    String findByCompany(String express, Long dictPid);

    String findNameById(Long cardType);
}
