/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.city;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.city.CereCityManage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereCityManageDAO extends BaseMapper<CereCityManage> {

    int deleteByPrimaryKey(Long cityId);

    int insertSelective(CereCityManage record);

    CereCityManage selectByPrimaryKey(Long cityId);

    int updateByPrimaryKeySelective(CereCityManage record);

    int updateByPrimaryKey(CereCityManage record);
}
