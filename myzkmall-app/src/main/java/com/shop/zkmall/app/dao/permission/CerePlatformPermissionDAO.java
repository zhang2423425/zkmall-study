/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformPermissionDAO extends BaseMapper<CerePlatformPermission> {

    int deleteByPrimaryKey(Long permissionId);

    int insertSelective(CerePlatformPermission record);

    CerePlatformPermission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(CerePlatformPermission record);

    int updateByPrimaryKey(CerePlatformPermission record);
}
