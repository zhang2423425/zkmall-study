/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.message.MessageGetAllParam;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatfromMessageDAO extends BaseMapper<CerePlatfromMessage> {

    int deleteByPrimaryKey(Long messageId);

    int insertSelective(CerePlatfromMessage record);

    CerePlatfromMessage selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(CerePlatfromMessage record);

    int updateByPrimaryKey(CerePlatfromMessage record);

    CerePlatfromMessage getById(@Param("messageId") Long messageId);

    List<CerePlatfromMessage> getAll(MessageGetAllParam param);
}
