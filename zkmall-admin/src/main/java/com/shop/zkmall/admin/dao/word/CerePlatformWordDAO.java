/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.word;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.word.WordParam;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformWordDAO extends BaseMapper<CerePlatformWord> {

    int deleteByPrimaryKey(Long wordId);

    int insertSelective(CerePlatformWord record);

    CerePlatformWord selectByPrimaryKey(Long wordId);

    int updateByPrimaryKeySelective(CerePlatformWord record);

    int updateByPrimaryKey(CerePlatformWord record);

    CerePlatformWord checkWord(@Param("keyWord") String keyWord, @Param("wordId") Long wordId);

    List<CerePlatformWord> getAll(WordParam param);

    void updateState(@Param("state") Integer state);
}
