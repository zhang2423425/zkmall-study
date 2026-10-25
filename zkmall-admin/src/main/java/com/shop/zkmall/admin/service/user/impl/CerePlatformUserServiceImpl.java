/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.user.CerePlatformUserDAO;
import com.shop.zkmall.admin.dao.user.CerePrivacyVerifySettingDAO;
import com.shop.zkmall.admin.page.role.PlatformUserRole;
import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.param.user.*;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.user.CerePlatformUserRoleService;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.domain.user.CerePlatformUserRole;
import com.shop.zkmall.commons.domain.user.CerePrivacyVerifySetting;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.EncryptUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformUserServiceImpl implements CerePlatformUserService {

    private final CerePlatformUserDAO cerePlatformUserDAO;

    private final CerePlatformUserRoleService cerePlatformUserRoleService;

    private final CerePlatformLogService cerePlatformLogService;

    private final EncodeUtil encodeUtil;

    private final CerePrivacyVerifySettingDAO cerePrivacyVerifySettingDAO;

    @Override
    public CerePlatformUser findByUserName(String userName) {
        return cerePlatformUserDAO.findByUserName(userName);
    }

    @Override
    public CerePlatformUser findByPhone(String phone) {
        return cerePlatformUserDAO.findByPhone(phone);
    }

    @Override
    public CerePlatformUser findById(Long userId) {
        CerePlatformUser cerePlatformUser = new CerePlatformUser();
        BeanUtils.copyProperties(cerePlatformUserDAO.getById(userId), cerePlatformUser);
        return cerePlatformUser;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(UserSaveParam userParam, CerePlatformUser user) {
//        user=new CerePlatformUser();//我加的，防止user为null
        String time = TimeUtils.yyMMddHHmmss();
        //校验用户名或者手机号是否已注册
        CerePlatformUser platformUser = cerePlatformUserDAO.checkUserNameOrPhone(userParam.getUsername(), userParam.getPhone(), null);
        if (platformUser != null) {
            throw new CoBusinessException(CoReturnFormat.USERNAME_OR_PHONE_HAVE);
        }
        CerePlatformUser cerePlatformUser = new CerePlatformUser();
        cerePlatformUser.setCreateTime(time);
        cerePlatformUser.setUsername(userParam.getUsername());
        cerePlatformUser.setEmail(userParam.getEmail());
        cerePlatformUser.setName(userParam.getName());
        cerePlatformUser.setPhone(userParam.getPhone());
        String password = "";
        if (EmptyUtils.isEmpty(userParam.getPassword())) {
            //设置默认密码123456
            password = EncryptUtil.encrypt("123456");
        } else {
            password = EncryptUtil.encrypt(userParam.getPassword());
        }
        cerePlatformUser.setPassword(password);
        cerePlatformUser.setSex(userParam.getSex());
        cerePlatformUser.setState(userParam.getState());
        cerePlatformUser.setToken("");
        cerePlatformUserDAO.insert(cerePlatformUser);
        if (!EmptyUtils.isEmpty(userParam.getRoleIds())) {
            //新增用户角色数据
            for (Long id : userParam.getRoleIds()) {
                CerePlatformUserRole cerePlatformUserRole = new CerePlatformUserRole();
                cerePlatformUserRole.setPlatformUserId(cerePlatformUser.getPlatformUserId());
                cerePlatformUserRole.setRoleId(id);
                cerePlatformUserRoleService.insert(cerePlatformUserRole);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "用户管理", "平台端操作", "添加用户", String.valueOf(cerePlatformUser.getPlatformUserId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(UserUpdateParam userParam, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验用户名或者手机号是否已注册
        CerePlatformUser platformUser = cerePlatformUserDAO.checkUserNameOrPhone(userParam.getUsername(), userParam.getPhone(), userParam.getPlatformUserId());
        if (platformUser != null) {
            throw new CoBusinessException(CoReturnFormat.USERNAME_OR_PHONE_HAVE);
        }
        CerePlatformUser cerePlatformUser = new CerePlatformUser();
        cerePlatformUser.setPlatformUserId(userParam.getPlatformUserId());
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUser.setUsername(userParam.getUsername());
        cerePlatformUser.setEmail(userParam.getEmail());
        cerePlatformUser.setName(userParam.getName());
        cerePlatformUser.setPhone(userParam.getPhone());
        cerePlatformUser.setSex(userParam.getSex());
        cerePlatformUser.setState(userParam.getState());
        if (!EmptyUtils.isEmpty(userParam.getPassword())) {
            //更新密码
            cerePlatformUser.setPassword(EncryptUtil.encrypt(userParam.getPassword()));
        }
        cerePlatformUserDAO.updateByPrimaryKeySelective(cerePlatformUser);
        //清空关联角色数据
        cerePlatformUserRoleService.deleteByUserId(cerePlatformUser.getPlatformUserId());
        if (!EmptyUtils.isEmpty(userParam.getRoleIds())) {
            //新增用户角色数据
            for (Long id : userParam.getRoleIds()) {
                CerePlatformUserRole cerePlatformUserRole = new CerePlatformUserRole();
                cerePlatformUserRole.setPlatformUserId(cerePlatformUser.getPlatformUserId());
                cerePlatformUserRole.setRoleId(id);
                cerePlatformUserRoleService.insert(cerePlatformUserRole);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "用户管理", "平台端操作", "修改用户", String.valueOf(cerePlatformUser.getPlatformUserId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(UserDeleteParam userParam, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        cerePlatformUserDAO.deleteByPrimaryKey(userParam.getPlatformUserId());
        //清空关联角色数据
        cerePlatformUserRoleService.deleteByUserId(userParam.getPlatformUserId());
        //新增日志
        cerePlatformLogService.addLog(user, "用户管理", "平台端操作", "删除用户", String.valueOf(userParam.getPlatformUserId()), time);
    }

    @Override
    public PlatformUser getById(Long platformUserId) {
        PlatformUser page = cerePlatformUserDAO.getById(platformUserId);
        if (page != null) {
            //解密密码
            page.setPassword(EncryptUtil.decrypt(page.getPassword()));
            //查询关联角色数据
            List<PlatformUserRole> list = cerePlatformUserRoleService.findRolesByUserId(platformUserId);
            if (!EmptyUtils.isEmpty(list)) {
                List<Long> ids = list.stream()
                        .map(PlatformUserRole::getRoleId).collect(Collectors.toList());
                page.setIds(ids);
            }
            page.setRoles(list);
        }
        return page;
    }

    @Override
    public Page<PlatformUser> getAll(UserGetAllParam userParam) {
        PageHelper.startPage(userParam.getPage(), userParam.getPageSize());
        List<PlatformUser> list = cerePlatformUserDAO.getAll(userParam);
        for (PlatformUser user : list) {
            user.setName(encodeUtil.encodeInfo(user.getName()));
            user.setEmail(encodeUtil.encodeInfo(user.getEmail()));
            user.setPhone(encodeUtil.encodePhone(user.getPhone()));
        }
        PageInfo<PlatformUser> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public CerePlatformUser findByToken(String token) {
        return cerePlatformUserDAO.findByToken(token);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void forgetPassword(UserForgetPasswordParam user, CerePlatformUser cerePlatformUser) {
        String time = TimeUtils.yyMMddHHmmss();
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUser.setPassword(EncryptUtil.encrypt(user.getNewPassword()));
        cerePlatformUserDAO.updateByPrimaryKeySelective(cerePlatformUser);
        //新增日志
        cerePlatformLogService.addLog(cerePlatformUser, "系统用户管理", "平台端操作", "忘记密码",
                String.valueOf(cerePlatformUser.getPlatformUserId()), time);
    }

    @Override
    public void updateToken(CerePlatformUser cerePlatformUser) {
        cerePlatformUserDAO.updateToken(cerePlatformUser);
    }

    @Override
    public void updatePassword(UserForgetPasswordParam passwordParam, CerePlatformUser user) {
        if (user != null) {
            PlatformUser platformUser = cerePlatformUserDAO.getById(user.getPlatformUserId());
            if (platformUser != null) {
                String oldPassword = EncryptUtil.encrypt(passwordParam.getPassword());
                if (!Objects.equals(oldPassword, platformUser.getPassword())) {
                    throw new CoBusinessException(CoReturnFormat.OLD_PASSWORD_ERROR);
                }
                String time = TimeUtils.yyMMddHHmmss();
                user.setUpdateTime(time);
                user.setPassword(EncryptUtil.encrypt(passwordParam.getNewPassword()));
                cerePlatformUserDAO.updateByPrimaryKeySelective(user);
                //新增日志
                cerePlatformLogService.addLog(user, "系统用户管理", "平台端操作", "修改密码",
                        String.valueOf(user.getPlatformUserId()), time);
            }
        }
    }

    @Override
    public void updatePhone(UserUpdatePhoneParam param, CerePlatformUser user) {
        if (user != null) {
            String time = TimeUtils.yyMMddHHmmss();
            CerePrivacyVerifySetting setting = cerePrivacyVerifySettingDAO.selectAdminSetting();
            if (setting != null && !setting.getPhone().equals(param.getNewPhone())) {
                setting.setPhone(param.getNewPhone());
                cerePrivacyVerifySettingDAO.updateById(setting);
            }
            //新增日志
            cerePlatformLogService.addLog(user, "系统用户管理", "平台端操作", "修改二次认证密码",
                    String.valueOf(user.getPlatformUserId()), time);
        }
    }

    @Override
    public String getAdminPhone() {
        String phone = cerePrivacyVerifySettingDAO.findVerifyPhone();
        if (phone == null) {
            CerePlatformUser user = cerePlatformUserDAO.selectByPrimaryKey(1L);
            if (user != null) {
                phone = user.getPhone();
                CerePrivacyVerifySetting setting = new CerePrivacyVerifySetting();
                setting.setPhone(phone);
                setting.setProject(1L);
                cerePrivacyVerifySettingDAO.insert(setting);
            }
        }
        return phone;
    }

    @Override
    public void updateAvatar(CerePlatformUser user) {
        CerePlatformUser cerePlatformUser = new CerePlatformUser();
        cerePlatformUser.setPlatformUserId(user.getPlatformUserId());
        cerePlatformUser.setAvatar(user.getAvatar());
        String time = TimeUtils.yyMMddHHmmss();
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUserDAO.updateById(cerePlatformUser);
        //新增日志
        cerePlatformLogService.addLog(user, "系统用户管理", "平台端操作", "修改管理",
                String.valueOf(user.getPlatformUserId()), time);
    }
}
