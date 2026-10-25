/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.buyer.BuyerComment;
import com.shop.zkmall.admin.page.buyer.BuyerOrder;
import com.shop.zkmall.admin.page.buyer.BuyerUser;
import com.shop.zkmall.admin.page.buyer.BuyerUserDetail;
import com.shop.zkmall.admin.param.buyer.BuyerGetAllParam;
import com.shop.zkmall.admin.param.buyer.BuyerGetByIdParam;
import com.shop.zkmall.admin.param.buyer.BuyerGetLabelsParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.label.CerePlatformLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    List<BuyerUser> getAll(BuyerGetAllParam param);

    BuyerUserDetail getById(@Param("buyerUserId") Long buyerUserId);

    List<String> findLabels(@Param("buyerUserId") Long buyerUserId);

    Integer findOrders(@Param("buyerUserId") Long buyerUserId);

    Integer findPays(@Param("buyerUserId") Long buyerUserId);

    Integer findProducts(@Param("buyerUserId") Long buyerUserId);

    BigDecimal findPrice(@Param("buyerUserId") Long buyerUserId);

    Integer findAfters(@Param("buyerUserId") Long buyerUserId);

    Integer findAfterOrders(@Param("buyerUserId") Long buyerUserId);

    Integer findSuccessAfters(@Param("buyerUserId") Long buyerUserId);

    List<BuyerOrder> findOrderList(BuyerGetByIdParam param);

    List<BuyerComment> findComments(@Param("buyerUserId") Long buyerUserId);

    List<CereBuyerReceive> findReceives(@Param("buyerUserId") Long buyerUserId);

    List<CerePlatformLabel> getLabels(BuyerGetLabelsParam param);

    List<CerePlatformLabel> getUserLabels(BuyerGetLabelsParam param);

    int increaseCredit(@Param("buyerUserId") long buyerUserId, @Param("credit") Integer credit);
}
