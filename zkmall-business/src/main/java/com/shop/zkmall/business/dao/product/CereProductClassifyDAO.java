/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import com.shop.zkmall.commons.domain.product.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereProductClassifyDAO extends BaseMapper<CereProductClassify> {

    int deleteByPrimaryKey(Long classifyId);

    int insertSelective(CereProductClassify record);

    CereProductClassify selectByPrimaryKey(Long classifyId);

    int updateByPrimaryKeySelective(CereProductClassify record);

    int updateByPrimaryKey(CereProductClassify record);

    List<CereProductClassify> getClassifySelect(@Param("classifyPid") Long classifyPid);

    CereProductClassify findByHierarchy(@Param("hierarchy") String hierarchy);

    List<Classify> findAll();

    List<Classify> findChilds();

    List<CereProductClassify> selectAll();
}
