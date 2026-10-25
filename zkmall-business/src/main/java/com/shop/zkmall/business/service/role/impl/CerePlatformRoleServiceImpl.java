/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.role.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.dao.role.CerePlatformRoleDAO;
import com.shop.zkmall.business.param.permission.RoleDistributionParam;
import com.shop.zkmall.business.param.role.RoleDeleteParam;
import com.shop.zkmall.business.param.role.RoleGetAllParam;
import com.shop.zkmall.business.param.role.RoleSaveParam;
import com.shop.zkmall.business.param.role.RoleUpdateParam;
import com.shop.zkmall.business.service.business.CereBusinessUserRoleService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.role.CerePlatformRolePermissionService;
import com.shop.zkmall.business.service.role.CerePlatformRoleService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;
import com.shop.zkmall.commons.domain.role.CerePlatformRolePermission;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformRoleServiceImpl implements CerePlatformRoleService {

    private final CerePlatformRoleDAO cerePlatformRoleDAO;

    private final CerePlatformRolePermissionService cerePlatformRolePermissionService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereBusinessUserRoleService cereBusinessUserRoleService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(RoleSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformRole role = new CerePlatformRole();
        role.setRoleName(param.getRoleName());
        role.setRoleDescribe(param.getRoleDescribe());
        role.setProject(param.getShopId());
        role.setCreateTime(time);
        cerePlatformRoleDAO.insert(role);
        //新增日志
        cerePlatformLogService.addLog(user, "角色管理", "平台端操作", "添加角色", role.getRoleId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(RoleUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformRole role = new CerePlatformRole();
        role.setRoleName(param.getRoleName());
        role.setRoleDescribe(param.getRoleDescribe());
        role.setRoleId(param.getRoleId());
        role.setUpdateTime(time);
        cerePlatformRoleDAO.updateByPrimaryKeySelective(role);
        //新增日志
        cerePlatformLogService.addLog(user, "角色管理", "平台端操作", "修改角色", role.getRoleId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(RoleDeleteParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        List<Long> userIdList = cereBusinessUserRoleService.findByRoleId(param.getRoleId());
        if (CollectionUtils.isNotEmpty(userIdList)) {
            throw new CoBusinessException(CoReturnFormat.ROLE_BIND_USER);
        }
        cerePlatformRoleDAO.deleteByPrimaryKey(param.getRoleId());
        //清空关联权限数据
        cerePlatformRolePermissionService.deleteByRoleId(param.getRoleId());
        //新增日志
        cerePlatformLogService.addLog(user, "角色管理", "平台端操作", "删除角色", param.getRoleId(), time);
    }

    @Override
    public CerePlatformRole getById(Long roleId) {
        return cerePlatformRoleDAO.getById(roleId);
    }

    @Override
    public Page<CerePlatformRole> getAll(RoleGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformRole> list = cerePlatformRoleDAO.getAll(param);
        PageInfo<CerePlatformRole> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void distribution(RoleDistributionParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //根据角色id清空权限数据
        cerePlatformRolePermissionService.deleteByRoleId(param.getRoleId());
        //新增权限数据
        if (!EmptyUtils.isEmpty(param.getPermissionIds())) {
            for (Long id : param.getPermissionIds()) {
                CerePlatformRolePermission cerePlatformRolePermission = new CerePlatformRolePermission();
                cerePlatformRolePermission.setRoleId(param.getRoleId());
                cerePlatformRolePermission.setPermissionId(id);
                cerePlatformRolePermissionService.insert(cerePlatformRolePermission);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "角色管理", "平台端操作", "分配权限", param.getRoleId(), time);
    }
}
