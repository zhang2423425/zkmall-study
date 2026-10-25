/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.admin;

import com.shop.zkmall.admin.param.admin.StatsAmountByDay;
import com.shop.zkmall.admin.param.admin.StatsByDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface AdminDAO {

    List<StatsByDay> selectUserCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsByDay> selectVisitCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsByDay> selectVisitUserCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsByDay> selectShopCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsAmountByDay> selectOrderAmountList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsByDay> selectOrderCountList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer selectOrderCountSum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<StatsByDay> selectPayUserCountList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer selectPayUserCountSum(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
