/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.after;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.after.AfterDetail;
import com.shop.zkmall.app.page.after.AfterHistory;
import com.shop.zkmall.app.page.after.Afters;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderAfterDAO extends BaseMapper<CereOrderAfter> {

    int deleteByPrimaryKey(Long afterId);

    int insertSelective(CereOrderAfter record);

    CereOrderAfter selectByPrimaryKey(Long afterId);

    int updateByPrimaryKeySelective(CereOrderAfter record);

    int updateByPrimaryKey(CereOrderAfter record);

    List<Afters> getAll(@Param("state") Integer state, @Param("buyerUserId") Long buyerUserId);

    AfterDetail getById(@Param("afterId") Long afterId);

    List<AfterHistory> findHistories(@Param("afterId") Long afterId);

    CereOrderAfter checkState(@Param("afterId") Long afterId, @Param("state") Integer state, @Param("afterType") Integer afterType);

    CereOrderAfter checkStateStayAndNo(@Param("afterId") Long afterId);

    CereOrderAfter checkStateStayAndNoAndType(@Param("afterId") Long afterId);

    Long getPostSaleNumByMonth(@Param("time") Date time, @Param("buyerUserId") Long buyerUserId);

    int findAfterCount(Long buyerUserId);

    List<Afters> getAllWithKeyword(@Param("state") Integer state,
                                   @Param("buyerUserId") Long buyerUserId,
                                   @Param("keyword") String keyword);
}
