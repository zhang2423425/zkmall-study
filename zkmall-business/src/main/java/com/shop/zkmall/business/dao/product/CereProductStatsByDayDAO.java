/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.product;

import com.shop.zkmall.commons.domain.product.CereProductStatsByDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereProductStatsByDayDAO {

    List<CereProductStatsByDay> selectStatsByTime(@Param("shopId") Long shopId,
                                                  @Param("startTime") String startTime,
                                                  @Param("endTime") String endTime);

}
