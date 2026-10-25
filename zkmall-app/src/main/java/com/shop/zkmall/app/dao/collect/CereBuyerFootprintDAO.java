/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.collect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.collect.BuyerFootprint;
import com.shop.zkmall.app.page.collect.FootprintProduct;
import com.shop.zkmall.commons.domain.collect.CereBuyerFootprint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerFootprintDAO extends BaseMapper<CereBuyerFootprint> {

    int deleteByPrimaryKey(Long footprintId);

    int insertSelective(CereBuyerFootprint record);

    CereBuyerFootprint selectByPrimaryKey(Long footprintId);

    int updateByPrimaryKeySelective(CereBuyerFootprint record);

    int updateByPrimaryKey(CereBuyerFootprint record);

    void deleteByIds(@Param("ids") List<Long> ids, @Param("buyerUserId") Long buyerUserId, @Param("times") List<String> times);

    List<BuyerFootprint> getAll(@Param("buyerUserId") Long buyerUserId);

    List<FootprintProduct> findProducts(@Param("createTime") String createTime, @Param("buyerUserId") Long buyerUserId);

    void updateSelected(@Param("ids") List<Long> ids);

    void updateBuyerData(@Param("buyerUserId") Long buyerUserId, @Param("id") Long id);
}
