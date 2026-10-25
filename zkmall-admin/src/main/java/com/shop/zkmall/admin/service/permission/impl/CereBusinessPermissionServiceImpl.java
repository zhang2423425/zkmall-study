/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.business.CereBusinessShopDAO;
import com.shop.zkmall.admin.dao.business.CerePlatformBusinessDAO;
import com.shop.zkmall.admin.dao.permission.CereBusinessPermissionDAO;
import com.shop.zkmall.admin.dao.permission.CerePlatformPermissionDAO;
import com.shop.zkmall.admin.dao.role.CerePlatformRoleDAO;
import com.shop.zkmall.admin.dao.role.CerePlatformRolePermissionDAO;
import com.shop.zkmall.admin.page.permission.MenuButton;
import com.shop.zkmall.admin.page.permission.Meta;
import com.shop.zkmall.admin.page.permission.Permission;
import com.shop.zkmall.admin.page.permission.RolePermission;
import com.shop.zkmall.admin.param.permission.*;
import com.shop.zkmall.admin.param.role.RoleDistributionParam;
import com.shop.zkmall.admin.param.user.UserBuildParam;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.permission.CereBusinessPermissionService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.LongEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;
import com.shop.zkmall.commons.domain.role.CerePlatformRolePermission;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBusinessPermissionServiceImpl implements CereBusinessPermissionService {

    private final CereBusinessPermissionDAO cereBusinessPermissionDAO;

    private final CerePlatformBusinessDAO cerePlatformBusinessDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final CerePlatformRolePermissionDAO cerePlatformRolePermissionDAO;

    private final CerePlatformPermissionDAO cerePlatformPermissionDAO;

    private final CereBusinessShopDAO cereBusinessShopDAO;

    private final CerePlatformRoleDAO cerePlatformRoleDAO;

    private static final String ADMIN = "管理员";

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(PermissionSaveParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformPermission permission = new CerePlatformPermission();
        permission.setPermissionPid(param.getPermissionPid());
        permission.setPermission(param.getPermission());
        permission.setPermissionName(param.getPermissionName());
        permission.setPermissionUri(param.getPermissionUri());
        permission.setDescribe(param.getDescribe());
        permission.setIcon(param.getIcon());
        permission.setResourceType(param.getResourceType());
        permission.setSort(param.getSort());
        if (EmptyUtils.isEmpty(permission.getPermissionPid())) {
            //如果没有父节点id,新增根节点
            permission.setPermissionPid(LongEnum.getByName("根节点"));
        }
        permission.setCreateTime(time);
        permission.setProject(LongEnum.ZERO.getCode());

        cereBusinessPermissionDAO.insert(permission);
        //新增日志
        cerePlatformLogService.addLog(user, "权限管理", "平台端操作", "添加权限", String.valueOf(permission.getPermissionId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(PermissionUpdateParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformPermission permission = new CerePlatformPermission();
        permission.setPermissionPid(param.getPermissionPid());
        permission.setPermission(param.getPermission());
        permission.setPermissionName(param.getPermissionName());
        permission.setPermissionUri(param.getPermissionUri());
        permission.setDescribe(param.getDescribe());
        permission.setIcon(param.getIcon());
        permission.setResourceType(param.getResourceType());
        permission.setSort(param.getSort());
        permission.setPermissionId(param.getPermissionId());
        permission.setUpdateTime(time);
        cereBusinessPermissionDAO.updateByPrimaryKeySelective(permission);
        //新增日志
        cerePlatformLogService.addLog(user, "权限管理", "平台端操作", "修改权限", String.valueOf(permission.getPermissionId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(PermissionDeleteParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        cereBusinessPermissionDAO.deleteByPrimaryKey(param.getPermissionId());
        //新增日志
        cerePlatformLogService.addLog(user, "权限管理", "平台端操作", "删除权限", String.valueOf(param.getPermissionId()), time);
    }

    @Override
    public CerePlatformPermission getById(Long permissionId) {
        return cereBusinessPermissionDAO.getById(permissionId);
    }

    @Override
    public Page<Permission> getAll(PermissionGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        //查询所有根节点菜单权限
        List<Permission> list = cereBusinessPermissionDAO.findAllByResourceType(StringEnum.CATALOG.getCode(), param.getPermissionName());
        //查询所有子节点菜单和按钮权限
        List<Permission> childs = cereBusinessPermissionDAO.findChilds(param.getPermissionName());
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((permission -> {
                setMenuChildsIndex(permission, childs, map, null);
            }));
        }
        PageInfo<Permission> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public RolePermission getRolePermission(RoleDistributionParam param) {
        RolePermission rolePermission = new RolePermission();
        //查询所有根节点菜单权限
        List<Permission> list = cereBusinessPermissionDAO.findAllByResourceType(StringEnum.CATALOG.getCode(), null);
        //查询所有子节点菜单权限
        List<Permission> childs = cereBusinessPermissionDAO.findChilds(null);
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((permission -> {
                setMenuChildsIndex(permission, childs, map, null);
            }));
        }
        rolePermission.setPermissions(list);
        //查询该角色已添加的菜单权限id
        rolePermission.setIds(cereBusinessPermissionDAO.findRolePermissionIds(param.getRoleId()));
        return rolePermission;
    }

    @Override
    public List<MenuButton> getAllByUserId(UserBuildParam user) {
        //根据用户id查询所有根节点菜单权限
        List<MenuButton> list = cereBusinessPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(), StringEnum.CATALOG.getCode());
        //根据用户id查询所有子节点菜单权限
        List<MenuButton> childs = cereBusinessPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(), StringEnum.MENU.getCode());
        //查询所有按钮权限
        List<MenuButton> buttons = cereBusinessPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(), StringEnum.BUTTON.getCode());
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((permission -> {
                Meta meta = new Meta();
                meta.setTitle(permission.getPermissionName());
                meta.setIcon(permission.getIcon());
                permission.setMeta(meta);
                setUserMenuChildsIndex(permission, childs, map, buttons);
            }));
        }
        return list;
    }

    @Override
    public List<MenuButton> findAllPermissionShop() {
        //根据用户id查询所有根节点菜单权限
        List<MenuButton> list = cereBusinessPermissionDAO.findAllCatalogByResourceType(StringEnum.CATALOG.getCode());
        //根据用户id查询所有子节点菜单权限
        List<MenuButton> childs = cereBusinessPermissionDAO.findAllCatalogByResourceType(StringEnum.MENU.getCode());
        //查询所有按钮权限
        List<MenuButton> buttons = cereBusinessPermissionDAO.findAllCatalogByResourceType(StringEnum.BUTTON.getCode());
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((permission -> {
                Meta meta = new Meta();
                meta.setTitle(permission.getPermissionName());
                meta.setIcon(permission.getIcon());
                permission.setMeta(meta);
                setUserMenuChildsIndex(permission, childs, map, buttons);
            }));
        }
        return list;
    }

    @Override
    public void insert(CerePlatformPermission permission) {
        cereBusinessPermissionDAO.insert(permission);
    }

    @Override
    public boolean syncMenu(SyncMenuParam param) {
        if (param.isDelMenu()) {
            delMenu(param);
        } else {
            addOrUpdateMenu(param);
        }
        return false;
    }

    private void delMenu(SyncMenuParam param) {
        List<CerePlatformPermission> permissionList = param.getSyncMenu();
        Map<String, List<String>> map = permissionList.stream().collect(Collectors.groupingBy(CerePlatformPermission::getResourceType,
                Collectors.mapping(CerePlatformPermission::getPermission, Collectors.toList())));
        if (param.isSyncAll()) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                cerePlatformRolePermissionDAO.deleteRolePermission(null, null, entry.getKey(), entry.getValue());
                cereBusinessPermissionDAO.deleteByResourceTypeAndPermission(null, entry.getKey(), entry.getValue());
            }
        } else {
            List<CerePlatformBusiness> businessList = cerePlatformBusinessDAO.findListByUsername(param.getSyncBusinessUsername());
            if (businessList.isEmpty()) {
                throw new CoBusinessException(CoReturnFormat.MERCHANT_NOT_EXIST);
            }
            /*Long project = cerePlatformBusinessDAO.findProjectByUsername(com.shop.zkmall.admin.param.getSyncBusinessUsername());
            if (project == null) {
                throw new CoBusinessException(CoReturnFormat.MERCHANT_NOT_EXIST);
            }*/
            for (CerePlatformBusiness bu : businessList) {
                Long project = bu.getShopId();
                Long businessUserId = bu.getBusinessUserId();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    cerePlatformRolePermissionDAO.deleteRolePermission(project, businessUserId, entry.getKey(), entry.getValue());
                    //cereBusinessPermissionDAO.deleteByResourceTypeAndPermission(project, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void addOrUpdateMenu(SyncMenuParam param) {
        List<CerePlatformPermission> permissionList = param.getSyncMenu();
        List<Permission> treeList = treefyPermission(permissionList);
        if (param.isSyncAll()) {
            List<Long> projectList = cereBusinessShopDAO.selectAllProject();
            for (Long project : projectList) {
                for (Permission per : treeList) {
                    insertOrUpdateInner(per, project, null, null, per.getPermissionPid());
                }
                log.info("sync progress %{}", ((projectList.indexOf(project) + 1) / 1.0 / projectList.size()) * 100);
            }
        } else {
            List<CerePlatformBusiness> businessList = cerePlatformBusinessDAO.findListByUsername(param.getSyncBusinessUsername());
            if (businessList.isEmpty()) {
                throw new CoBusinessException(CoReturnFormat.MERCHANT_NOT_EXIST);
            }
            /*Long project = cerePlatformBusinessDAO.findProjectByUsername(com.shop.zkmall.admin.param.getSyncBusinessUsername());
            if (project == null) {
                throw new CoBusinessException(CoReturnFormat.MERCHANT_NOT_EXIST);
            }*/
            for (CerePlatformBusiness bu : businessList) {
                Long project = bu.getShopId();
                for (Permission per : treeList) {
                    insertOrUpdateInner(per, project, bu.getBusinessUserId(), null, per.getPermissionPid());
                }
            }
        }
    }

    private void insertOrUpdateInner(Permission per, Long project, Long businessUserId, Long permissionPid, Long templatePermissionPid) {
        if (per == null) {
            return;
        }
        List<CerePlatformPermission> list = cerePlatformPermissionDAO.findByPermissionAndProject(project,
                per.getPermission(), per.getResourceType());
        String time = TimeUtils.yyMMddHHmmss();
        if (!list.isEmpty()) {
            for (CerePlatformPermission oldPer : list) {
                oldPer.setUpdateTime(time);
                oldPer.setDescribe(per.getDescribe());
                oldPer.setIcon(per.getIcon());
                oldPer.setPermissionName(per.getPermissionName());
                oldPer.setPermissionUri(per.getPermissionUri());
                cerePlatformPermissionDAO.updateByPrimaryKey(oldPer);
            }
            per.setPermissionId(list.get(0).getPermissionId());
            bindRoleAndPermission(project, businessUserId, per);
        } else {
            if (permissionPid != null && permissionPid >= 0) {
                insertPermissionInner(per, project, businessUserId, time, permissionPid);
            } else if (templatePermissionPid != null) {
                if (templatePermissionPid == 0) {
                    insertPermissionInner(per, project, businessUserId, time, templatePermissionPid);
                } else {
                    Long newPermissionPid = checkAndInsertParentPermission(templatePermissionPid, project, businessUserId, time);
                    insertPermissionInner(per, project, businessUserId, time, newPermissionPid);
                }
            }
        }

        if (per.getChilds() != null) {
            for (Permission child : per.getChilds()) {
                insertOrUpdateInner(child, project, businessUserId, per.getPermissionId(), null);
            }
        }
    }

    /**
     * 绑定角色和权限的关系
     */
    private void bindRoleAndPermission(Long project, Long businessUserId, Permission per) {
        //处理角色问题
        List<CerePlatformRole> roleList = cerePlatformRoleDAO.selectByProjectAndBusinessUserId(project, businessUserId);
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().filter(role -> ADMIN.equals(role.getRoleName())).map(CerePlatformRole::getRoleId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(roleIdList)) {
                roleIdList = roleList.stream().map(CerePlatformRole::getRoleId).toList();
            }
            for (Long roleId : roleIdList) {
                CerePlatformRolePermission rolePermission = new CerePlatformRolePermission();
                rolePermission.setPermissionId(per.getPermissionId());
                rolePermission.setRoleId(roleId);

                LambdaQueryWrapper<CerePlatformRolePermission> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CerePlatformRolePermission::getRoleId, roleId);
                wrapper.eq(CerePlatformRolePermission::getPermissionId, per.getPermissionId());

                Long count = cerePlatformRolePermissionDAO.selectCount(wrapper);
                if (count == 0) {
                    cerePlatformRolePermissionDAO.insert(rolePermission);
                }
            }
        }
    }

    /**
     * 检查对应的权限是否存在，如果不存在就新增权限，并检查其上级权限
     */
    private Long checkAndInsertParentPermission(Long templatePermissionPid, Long project, Long businessUserId, String time) {
        if (templatePermissionPid != null && templatePermissionPid > 0) {
            // 这个是模板的权限
            CerePlatformPermission templateParentPermission = cerePlatformPermissionDAO.selectByPrimaryKey(templatePermissionPid);
            if (templateParentPermission != null) {
                // 查出project对应的商家内，是否有对应的权限，如果没有需要新增，否则没有父级权限，无法新增子级权限
                List<CerePlatformPermission> projectParentPermissionList = cerePlatformPermissionDAO.findByPermissionAndProject(project, templateParentPermission.getPermission(), templateParentPermission.getResourceType());
                Long pid = 0L;
                if (!CollectionUtils.isEmpty(projectParentPermissionList)) {
                    pid = projectParentPermissionList.get(0).getPermissionId();
                } else {
                    Long newPid = checkAndInsertParentPermission(templateParentPermission.getPermissionPid(), project, businessUserId, time);
                    Permission projectParentPermission = new Permission();
                    BeanUtils.copyProperties(templateParentPermission, projectParentPermission);
                    projectParentPermission.setPermissionId(null);
                    insertPermissionInner(projectParentPermission, project, businessUserId, time, newPid);
                    pid = projectParentPermission.getPermissionId();
                }
                return pid;
            }
        }
        return 0L;
    }

    /**
     * 新增一个权限
     */
    private void insertPermissionInner(Permission per, Long project, Long businessUserId, String time, Long permissionPid) {
        CerePlatformPermission newPer = new CerePlatformPermission();
        BeanUtils.copyProperties(per, newPer);
        newPer.setProject(project);
        newPer.setCreateTime(time);
        newPer.setUpdateTime(time);
        newPer.setPermissionPid(permissionPid);
        cerePlatformPermissionDAO.insertSelective(newPer);
        per.setPermissionId(newPer.getPermissionId());

        bindRoleAndPermission(project, businessUserId, per);
    }

    @Override
    public CerePlatformPermission findBySort(Integer sort, Long id) {
        return cereBusinessPermissionDAO.findBySort(sort, id);
    }

    @Override
    public Integer getMaxSort() {
        return cereBusinessPermissionDAO.getMaxSort();
    }

    private List<Permission> treefyPermission(List<CerePlatformPermission> permissionList) {
        List<Permission> result = new ArrayList<>();
        Map<Long, List<CerePlatformPermission>> pidMap = permissionList.stream().collect(Collectors.groupingBy(CerePlatformPermission::getPermissionPid));
//        List<CerePlatformPermission> topList = pidMap.get(0L);
//        if (topList == null) {
//            return result;
//        }
        for (Map.Entry<Long, List<CerePlatformPermission>> entry : pidMap.entrySet()) {
            for (CerePlatformPermission per : entry.getValue()) {
                Permission copy = new Permission();
                BeanUtils.copyProperties(per, copy);
                result.add(copy);

                assembleChild(copy, pidMap);
            }
        }
        Set<Long> permissionIdSet = result.stream().map(Permission::getPermissionId).collect(Collectors.toSet());
        result.removeIf(permission -> permissionIdSet.contains(permission.getPermissionPid()));
        return result;
    }

    private void assembleChild(Permission permission, Map<Long, List<CerePlatformPermission>> pidMap) {
        List<CerePlatformPermission> childs = pidMap.get(permission.getPermissionId());
        if (childs != null) {
            permission.setChilds(new ArrayList<>());
            for (CerePlatformPermission per : childs) {
                Permission child = new Permission();
                BeanUtils.copyProperties(per, child);
                permission.getChilds().add(child);
                assembleChild(child, pidMap);
            }
        }
    }

    private MenuButton setUserMenuChildsIndex(MenuButton parent, List<MenuButton> all,
                                              Map<String, Integer> map, List<MenuButton> buttons) throws ArrayIndexOutOfBoundsException {
        if (!parent.getResourceType().equals(StringEnum.CATALOG.getCode())) {
            //回调进来的,设置回调执行次数+1
            map.put("callback", map.get("callback") + 1);
        } else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback", 0);
        }
        List<MenuButton> childs = new ArrayList<>();
        if (!EmptyUtils.isEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                if (i < 0) {
                    i = 0;
                }
                MenuButton sysPermission = all.get(i);
                if (!EmptyUtils.isEmpty(buttons)) {
                    //封装按钮数据
                    List<MenuButton> collect = buttons.stream()
                            //过滤不是当前菜单的子级按钮
                            .filter(button -> button.getPermissionPid().equals(sysPermission.getPermissionId()))
                            .collect(Collectors.toList());
                    sysPermission.setButtons(collect);
                }
                if (parent.getPermissionId().equals(sysPermission.getPermissionPid())) {
                    all.remove(i);
                    i--;
                    //执行回调
                    MenuButton itemPermission = setUserMenuChildsIndex(sysPermission, all, map, buttons);
                    Meta meta = new Meta();
                    meta.setTitle(itemPermission.getPermissionName());
                    meta.setIcon(itemPermission.getIcon());
                    itemPermission.setMeta(meta);
                    childs.add(itemPermission);
                    //判断当前是否回到最高级菜单节点
                    if (parent.getResourceType().equals(StringEnum.CATALOG.getCode())) {
                        //如果是,计算索引值=当前索引值-（回调执行次数-1）
                        i = i - (map.get("callback") - 1);
                        //初始化回调执行次数
                        map.put("callback", 0);
                    }
                }
            }
            parent.setChildren(childs);
        }
        return parent;
    }

    private Permission setMenuChildsIndex(Permission parent, List<Permission> all, Map<String, Integer> map, List<Permission> buttons) throws ArrayIndexOutOfBoundsException {
        if (!parent.getResourceType().equals(StringEnum.CATALOG.getCode())) {
            //回调进来的,设置回调执行次数+1
            map.put("callback", map.get("callback") + 1);
        } else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback", 0);
        }
        List<Permission> childs = new ArrayList<>();
        if (!EmptyUtils.isEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                if (i < 0) {
                    i = 0;
                }
                Permission sysPermission = all.get(i);
                if (parent.getPermissionId().equals(sysPermission.getPermissionPid())) {
                    all.remove(i);
                    i--;
                    //执行回调
                    Permission itemPermission = setMenuChildsIndex(sysPermission, all, map, buttons);
                    childs.add(itemPermission);
                    //判断当前是否回到最高级菜单节点
                    if (parent.getResourceType().equals(StringEnum.CATALOG.getCode())) {
                        //如果是,计算索引值=当前索引值-（回调执行次数-1）
                        i = i - (map.get("callback") - 1);
                        //初始化回调执行次数
                        map.put("callback", 0);
                    }
                }
            }
            parent.setChilds(childs);
        }
        return parent;
    }
}
