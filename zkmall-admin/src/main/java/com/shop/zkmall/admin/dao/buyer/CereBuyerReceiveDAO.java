/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerReceiveDAO extends BaseMapper<CereBuyerReceive> {

    int deleteByPrimaryKey(Long receiveId);

    int insertSelective(CereBuyerReceive record);

    CereBuyerReceive selectByPrimaryKey(Long receiveId);

    int updateByPrimaryKeySelective(CereBuyerReceive record);

    int updateByPrimaryKey(CereBuyerReceive record);
}
