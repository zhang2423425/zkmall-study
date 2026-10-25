/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.role.RoleGetAllParam;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformRoleDAO extends BaseMapper<CerePlatformRole> {

    int deleteByPrimaryKey(Long roleId);

    int insertSelective(CerePlatformRole record);

    CerePlatformRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(CerePlatformRole record);

    int updateByPrimaryKey(CerePlatformRole record);

    CerePlatformRole getById(@Param("roleId") Long roleId);

    List<CerePlatformRole> getAll(RoleGetAllParam param);

    List<CerePlatformRole> selectByProject(Long project);

    List<CerePlatformRole> selectByProjectAndBusinessUserId(@Param("project") Long project,
                                                            @Param("businessUserId") Long businessUserId);
}
