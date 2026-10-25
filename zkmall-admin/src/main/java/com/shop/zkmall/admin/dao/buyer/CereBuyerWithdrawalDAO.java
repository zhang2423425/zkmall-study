/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.buyer.BuyerWithdrawal;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerWithdrawal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerWithdrawalDAO extends BaseMapper<CereBuyerWithdrawal> {

    int deleteByPrimaryKey(Long withdrawalId);

    int insertSelective(CereBuyerWithdrawal record);

    CereBuyerWithdrawal selectByPrimaryKey(Long withdrawalId);

    int updateByPrimaryKeySelective(CereBuyerWithdrawal record);

    int updateByPrimaryKey(CereBuyerWithdrawal record);

    List<BuyerWithdrawal> getAll(BuyerWithdrawalGetAllParam param);

    BuyerWithdrawal getById(@Param("withdrawalId") Long withdrawalId);
}
