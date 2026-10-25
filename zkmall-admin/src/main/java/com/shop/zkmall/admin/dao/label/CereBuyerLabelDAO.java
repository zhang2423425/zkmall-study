/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.label;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.buyer.BuyerSaveUserLabelParam;
import com.shop.zkmall.commons.domain.label.CereBuyerLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerLabelDAO extends BaseMapper<CereBuyerLabel> {

    int insertSelective(CereBuyerLabel record);

    void deleteLabelUser(@Param("ids") List<Long> ids);

    void insertBatch(@Param("list") List<CereBuyerLabel> list);

    List<Long> findAlreadyByUser(BuyerSaveUserLabelParam param);

    List<Long> findByBuyerUserId(Long buyerUserId);
}
