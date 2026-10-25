/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.buyer.BuyerUserDetail;
import com.shop.zkmall.commons.domain.business.CereShopIdBuyerUserIdTimeDTO;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerUserDAO extends BaseMapper<CereBuyerUser> {

    int deleteByPrimaryKey(Long buyerUserId);

    int insertSelective(CereBuyerUser record);

    CereBuyerUser selectByPrimaryKey(Long buyerUserId);

    int updateByPrimaryKeySelective(CereBuyerUser record);

    int updateByPrimaryKey(CereBuyerUser record);

    CereBuyerUser checkPhone(@Param("phone") String phone);

    BuyerUserDetail getById(@Param("shopId") Long shopId, @Param("buyerUserId") Long buyerUserId);

    /** 查询所有访问了商家或下过单的用户id */
    List<CereShopIdBuyerUserIdTimeDTO> findVisitRecord();

    /** 查询所有商家记录的店铺id */
    List<CereShopIdBuyerUserIdTimeDTO> findConsumeRecord();

    void updateGrowth(@Param("buyerUserId") Long buyerUserId, @Param("growth") int growth);
}
