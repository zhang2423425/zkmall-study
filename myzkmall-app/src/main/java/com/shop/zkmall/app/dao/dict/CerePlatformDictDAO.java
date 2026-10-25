/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformDictDAO extends BaseMapper<CerePlatformDict> {

    int deleteByPrimaryKey(Long dictId);

    int insertSelective(CerePlatformDict record);

    CerePlatformDict selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(CerePlatformDict record);

    int updateByPrimaryKey(CerePlatformDict record);

    Integer findExpress();

    List<CerePlatformDict> findExpressSelect(@Param("dictId") Long dictId);

    List<String> getReasonSelect();

    String findByCompany(@Param("express") String express, @Param("dictPid") Long dictPid);

    CerePlatformDict getByName(@Param("name") String name);

    List<CerePlatformDict> getSelect(@Param("dictName") String dictName);

    List<CerePlatformDict> getByNameList(List<String> nameList);
}
