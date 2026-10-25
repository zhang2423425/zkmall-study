/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.dao.business.CerePlatformBusinessDAO;
import com.shop.zkmall.business.dao.user.CerePrivacyVerifySettingDAO;
import com.shop.zkmall.business.page.role.PlatformUserRole;
import com.shop.zkmall.business.page.shop.PlatformBusiness;
import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.param.business.BusinessForgetParam;
import com.shop.zkmall.business.param.user.*;
import com.shop.zkmall.business.service.business.CereBusinessShopService;
import com.shop.zkmall.business.service.business.CereBusinessUserRoleService;
import com.shop.zkmall.business.service.business.CerePlatformBusinessService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CerePlatformShopService;
import com.shop.zkmall.business.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import com.shop.zkmall.commons.domain.business.CereBusinessUserRole;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.user.CerePrivacyVerifySetting;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.EncryptUtil;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CerePlatformBusinessServiceImpl implements CerePlatformBusinessService {

    private final CerePlatformBusinessDAO cerePlatformBusinessDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereBusinessUserRoleService cereBusinessUserRoleService;

    private final CereBusinessShopService cereBusinessShopService;

    private final CerePlatformShopService cerePlatformShopService;

    private final EncodeUtil encodeUtil;

    private final CerePrivacyVerifySettingDAO cerePrivacyVerifySettingDAO;

    @Override
    public PlatformBusiness findByUserName(String username) {
        return cerePlatformBusinessDAO.findByUserName(username);
    }

    @Override
    public PlatformBusiness findByPhone(String phone) {
        return cerePlatformBusinessDAO.findByPhone(phone);
    }

    @Override
    public PlatformBusiness findById(Long userId) {
        return cerePlatformBusinessDAO.findById(userId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void forgetPassword(BusinessForgetParam user, CerePlatformBusiness cerePlatformBusiness) {
        String time = TimeUtils.yyMMddHHmmss();
        cerePlatformBusiness.setUpdateTime(time);
        cerePlatformBusiness.setPassword(EncryptUtil.encrypt(user.getNewPassword()));
        cerePlatformBusinessDAO.updateByPrimaryKeySelective(cerePlatformBusiness);
        //新增日志
        cerePlatformLogService.addLog(cerePlatformBusiness, "商家用户管理", "商户端操作", "忘记密码", cerePlatformBusiness.getBusinessUserId(), time);
    }

    @Override
    public CerePlatformBusiness findByToken(String token) {
        return cerePlatformBusinessDAO.findByToken(token);
    }

    @Override
    public void insert(CerePlatformBusiness cerePlatformBusiness) {
        cerePlatformBusinessDAO.insert(cerePlatformBusiness);
    }

    @Override
    public void updateToken(CerePlatformBusiness cerePlatformBusiness) {
        cerePlatformBusinessDAO.updateToken(cerePlatformBusiness);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(BusinessSaveUser business, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验账号是否已存在
        CerePlatformBusiness platformBusiness = cerePlatformBusinessDAO.checkUserName(business.getUsername(), null);
        if (platformBusiness != null) {
            throw new CoBusinessException(CoReturnFormat.USER_AREADY_REGISTER);
        }
        //校验手机号是否已存在
        CerePlatformBusiness platformBusiness2 = cerePlatformBusinessDAO.checkPhone(business.getPhone(), null);
        if (platformBusiness2 != null) {
            throw new CoBusinessException(CoReturnFormat.USER_AREADY_REGISTER);
        }
        //添加用户
        CerePlatformBusiness cerePlatformBusiness = new CerePlatformBusiness();
        cerePlatformBusiness.setCreateTime(time);
        cerePlatformBusiness.setPassword(EncryptUtil.encrypt(business.getPassword()));
        cerePlatformBusiness.setUsername(business.getUsername());
        cerePlatformBusiness.setName(business.getName());
        cerePlatformBusiness.setState(business.getState());
        cerePlatformBusiness.setEmail(business.getEmail());
        cerePlatformBusiness.setPhone(business.getPhone());
        cerePlatformBusiness.setSex(business.getSex());
        cerePlatformBusinessDAO.insert(cerePlatformBusiness);
        //新增用户关联角色数据
        for (Long roleId : business.getRoleIds()) {
            CereBusinessUserRole businessUserRole = new CereBusinessUserRole();
            businessUserRole.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
            businessUserRole.setRoleId(roleId);
            cereBusinessUserRoleService.insert(businessUserRole);
        }
        //同步员工关联店铺数据
        CereBusinessShop cereBusinessShop = new CereBusinessShop();
        cereBusinessShop.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
        cereBusinessShop.setIfBinding(IntegerEnum.YES.getCode());
        cereBusinessShop.setShopId(business.getShopId());
        cereBusinessShopService.insert(cereBusinessShop);
        //新增日志
        cerePlatformLogService.addLog(user, "商家用户管理", "商户端操作", "添加用户", cerePlatformBusiness.getBusinessUserId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(BusinessUpdateUser business, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验账号是否已存在
        CerePlatformBusiness platformBusiness = cerePlatformBusinessDAO.checkUserName(business.getUsername(), business.getBusinessUserId());
        if (platformBusiness != null) {
            throw new CoBusinessException(CoReturnFormat.USER_AREADY_REGISTER);
        }
        //校验手机号是否已存在
        CerePlatformBusiness platformBusiness2 = cerePlatformBusinessDAO.checkPhone(business.getPhone(), business.getBusinessUserId());
        if (platformBusiness2 != null) {
            throw new CoBusinessException(CoReturnFormat.USER_AREADY_REGISTER);
        }
        //修改用户
        CerePlatformBusiness cerePlatformBusiness = new CerePlatformBusiness();
        cerePlatformBusiness.setBusinessUserId(business.getBusinessUserId());
        cerePlatformBusiness.setUpdateTime(time);
        cerePlatformBusiness.setPassword(EncryptUtil.encrypt(business.getPassword()));
        cerePlatformBusiness.setUsername(business.getUsername());
        cerePlatformBusiness.setName(business.getName());
        cerePlatformBusiness.setState(business.getState());
        cerePlatformBusiness.setEmail(business.getEmail());
        cerePlatformBusiness.setPhone(business.getPhone());
        cerePlatformBusiness.setSex(business.getSex());
        cerePlatformBusinessDAO.updateByPrimaryKeySelective(cerePlatformBusiness);
        //查询当前店铺绑定负责人用户id
        Long userid = cereBusinessShopService.findByShopId(user.getShopId());
        if (business.getBusinessUserId().equals(userid)) {
            //如果修改的是店铺负责人信息,需要同步更新店铺账号
            CerePlatformShop cerePlatformShop = cerePlatformShopService.selectByPrimaryKey(user.getShopId());
            if (cerePlatformShop != null) {
                cerePlatformShop.setShopPhone(business.getUsername());
                cerePlatformShop.setShopPassword(EncryptUtil.encrypt(business.getPassword()));
                cerePlatformShop.setUpdateTime(time);
                cerePlatformShopService.update(cerePlatformShop);
            }
        }
        //清空用户关联角色数据
        cereBusinessUserRoleService.deleteByUserId(cerePlatformBusiness.getBusinessUserId());
        //新增用户关联角色数据
        for (Long roleId : business.getRoleIds()) {
            CereBusinessUserRole businessUserRole = new CereBusinessUserRole();
            businessUserRole.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
            businessUserRole.setRoleId(roleId);
            cereBusinessUserRoleService.insert(businessUserRole);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "商家用户管理", "商户端操作", "修改用户", cerePlatformBusiness.getBusinessUserId(), time);
    }

    @Override
    public Business getById(Long businessUserId) {
        Business business = cerePlatformBusinessDAO.getById(businessUserId);
        if (business != null) {
            //解密密码
            business.setPassword(EncryptUtil.decrypt(business.getPassword()));
            //查询关联角色数据
            List<PlatformUserRole> list = cereBusinessUserRoleService.findRolesByUserId(businessUserId);
            if (!EmptyUtils.isEmpty(list)) {
                List<Long> ids = list.stream()
                        .map(PlatformUserRole::getRoleId).collect(Collectors.toList());
                business.setIds(ids);
            }
            business.setRoles(list);
        }
        return business;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(BusinessDeleteUser param, CerePlatformBusiness user) {
        //删除用户
        cerePlatformBusinessDAO.deleteByPrimaryKey(param.getBusinessUserId());
        //删除绑定店铺数据
        cereBusinessShopService.deleteByUserId(param.getBusinessUserId());
        //新增日志
        cerePlatformLogService.addLog(user, "商家用户管理", "商户端操作", "修改用户",
                param.getBusinessUserId(), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public Page<CerePlatformBusiness> getAll(BusinessGetAllUser param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformBusiness> list = cerePlatformBusinessDAO.getAll(param);
        for (CerePlatformBusiness business : list) {
            business.setEmail(encodeUtil.encodeInfo(business.getEmail()));
            //手机号做脱敏处理
            if (business.getPhone() != null) {
                String phone = StringUtils.showStartAndEnd(business.getPhone(), 3, 4);
                business.setPhone(phone);
            }
        }
        PageInfo<CerePlatformBusiness> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public CerePlatformBusiness checkUserName(String shopPhone, Long id) {
        return cerePlatformBusinessDAO.checkUserName(shopPhone, id);
    }

    @Override
    public void updatePassword(BusinessForgetParam passwordParam, CerePlatformBusiness user) {
        if (user != null) {
            Business business = cerePlatformBusinessDAO.getById(user.getBusinessUserId());
            if (business != null) {
                String oldPassword = EncryptUtil.encrypt(passwordParam.getPassword());
                if (!Objects.equals(oldPassword, business.getPassword())) {
                    throw new CoBusinessException(CoReturnFormat.OLD_PASSWORD_ERROR);
                }
                String time = TimeUtils.yyMMddHHmmss();
                user.setUpdateTime(time);
                user.setPassword(EncryptUtil.encrypt(passwordParam.getNewPassword()));
                cerePlatformBusinessDAO.updateByPrimaryKeySelective(user);
                //新增日志
                cerePlatformLogService.addLog(user, "商家管理", "商户端操作", "修改密码",
                        user.getBusinessUserId(), time);
            }
        }
    }

    @Override
    public void updateAvatar(CerePlatformBusiness user) {
        if (user != null) {
            CerePlatformBusiness business = new CerePlatformBusiness();
            business.setBusinessUserId(user.getBusinessUserId());
            business.setAvatar(user.getAvatar());
            String time = TimeUtils.yyMMddHHmmss();
            business.setUpdateTime(time);
            cerePlatformBusinessDAO.updateByPrimaryKeySelective(business);
            //新增日志
            cerePlatformLogService.addLog(user, "商家管理", "商户端操作", "修改头像",
                    user.getBusinessUserId(), time);
        }
    }

    @Override
    public void updatePhone(UserUpdatePhoneParam param, CerePlatformBusiness user) {
        if (user != null) {
            CerePrivacyVerifySetting setting = cerePrivacyVerifySettingDAO.selectByShopId(user.getShopId());
            if (setting != null && !setting.getPhone().equals(param.getNewPhone())) {
                setting.setPhone(param.getNewPhone());
                cerePrivacyVerifySettingDAO.updateById(setting);
            }
            String time = TimeUtils.yyMMddHHmmss();
            //新增日志
            cerePlatformLogService.addLog(user, "商家管理", "商户端操作", "修改二次认证手机号",
                    user.getBusinessUserId(), time);
        }
    }

    @Override
    public String findAdminPhone(Long shopId) {
        String phone = cerePrivacyVerifySettingDAO.findVerifyPhone(shopId);
        if (phone == null) {
            CerePlatformShop shop = cerePlatformShopService.selectByPrimaryKey(shopId);
            if (shop != null) {
                phone = shop.getChargePersonPhone();
                CerePrivacyVerifySetting setting = new CerePrivacyVerifySetting();
                setting.setPhone(phone);
                setting.setProject(shopId);
                cerePrivacyVerifySettingDAO.insert(setting);
            }
        }
        return phone;
    }
}
