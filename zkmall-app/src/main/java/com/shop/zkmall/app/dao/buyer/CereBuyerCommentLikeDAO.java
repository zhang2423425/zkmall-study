/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.buyer.CereBuyerCommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerCommentLikeDAO extends BaseMapper<CereBuyerCommentLike> {

    int deleteByPrimaryKey(Long commentId);

    int insertSelective(CereBuyerCommentLike record);

    CereBuyerCommentLike selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(CereBuyerCommentLike record);

    int updateByPrimaryKey(CereBuyerCommentLike record);

    CereBuyerCommentLike findByUserIdAndCommentId(@Param("buyerUserId") Long buyerUserId, @Param("commentId") Long commentId);

    void updateBuyerData(@Param("buyerUserId") Long buyerUserId, @Param("id") Long id);

    int insertOrUpdate(@Param("buyerUserId") Long buyerUserId,
                       @Param("commentId") Long commentId,
                       @Param("ifLike") Integer ifLike);
}
