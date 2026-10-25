/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereNoticeDAO extends BaseMapper<CereNotice> {

    int deleteByPrimaryKey(Long noticeId);

    int insertSelective(CereNotice record);

    CereNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(CereNotice record);

    int updateByPrimaryKey(CereNotice record);

    void insertBatch(@Param("list") List<CereNotice> list);
}
