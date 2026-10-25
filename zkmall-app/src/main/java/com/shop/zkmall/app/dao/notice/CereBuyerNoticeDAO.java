/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.notice.CereBuyerNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereBuyerNoticeDAO extends BaseMapper<CereBuyerNotice> {

    int insertSelective(CereBuyerNotice record);

    CereBuyerNotice selectBuyerNotice(@Param("noticeId") Long noticeId, @Param("buyerUserId") Long buyerUserId);

    int updateStatus(CereBuyerNotice buyerNotice);
}
