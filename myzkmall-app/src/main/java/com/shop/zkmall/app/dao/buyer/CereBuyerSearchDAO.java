/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerSearchDAO extends BaseMapper<CereBuyerSearch> {

    int deleteByPrimaryKey(Long searchId);

    int insertSelective(CereBuyerSearch record);

    CereBuyerSearch selectByPrimaryKey(Long searchId);

    int updateByPrimaryKeySelective(CereBuyerSearch record);

    int updateByPrimaryKey(CereBuyerSearch record);

    List<CereBuyerSearch> getHistory(@Param("buyerUserId") Long buyerUserId);

    // void delete(@Param("searchId") Long searchId);

    CereBuyerSearch findBySearch(@Param("search") String search, @Param("buyerUserId") Long buyerUserId);

    void updateBuyerData(@Param("buyerUserId") Long buyerUserId, @Param("id") Long id);

    List<String> selectHotSearch();
}
