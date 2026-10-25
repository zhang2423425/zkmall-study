/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.admin.page.finance.Finance;
import com.shop.zkmall.admin.page.finance.FinanceCount;
import com.shop.zkmall.admin.page.shop.Shop;
import com.shop.zkmall.admin.page.shop.ShopGetAll;
import com.shop.zkmall.admin.param.finance.FinanceParam;
import com.shop.zkmall.admin.param.shop.*;
import com.shop.zkmall.admin.service.business.CereBusinessShopService;
import com.shop.zkmall.admin.service.business.CerePlatformBusinessService;
import com.shop.zkmall.admin.service.common.CommonService;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.product.CereShopProductService;
import com.shop.zkmall.admin.service.shop.CerePlatformShopService;
import com.shop.zkmall.admin.service.shop.CereShopCommentService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformShopServiceImpl implements CerePlatformShopService {

    private final CerePlatformShopDAO cerePlatformShopDAO;

    private final CerePlatformBusinessService cerePlatformBusinessService;

    private final CereBusinessShopService cereBusinessShopService;

    private final CommonService commonService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereShopProductService cereShopProductService;

    private final EncodeUtil encodeUtil;

    private final CereShopCommentService cereShopCommentService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopSaveParam param, CerePlatformUser user) {
        //新建商家数据
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        BeanUtils.copyProperties(param, cerePlatformShop);
        if (EmptyUtils.isEmpty(param.getShopPassword())) {
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt("123456"));
        } else {
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt(param.getShopPassword()));
        }
        CerePlatformBusiness business = cerePlatformBusinessService.findByPhone(param.getChargePersonPhone());
        if (business != null) {
            throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
        }
        //生成店铺编号
        String shopCode = commonService.getShopCode();
        cerePlatformShop.setShopCode(shopCode);
        cerePlatformShop.setAuthenticationState(IntegerEnum.APPROVED.getCode());
        cerePlatformShop.setCheckState(IntegerEnum.UNTREATED.getCode());
        cerePlatformShop.setState(IntegerEnum.YES.getCode());
        cerePlatformShop.setCreateTime(time);
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShop.setHotFlag(param.isHotFlag());
        cerePlatformShopDAO.insert(cerePlatformShop);
        //同步新增商家用户数据
        CerePlatformBusiness cerePlatformBusiness = new CerePlatformBusiness();
        cerePlatformBusiness.setCreateTime(time);
        cerePlatformBusiness.setUsername(cerePlatformShop.getShopPhone());
        cerePlatformBusiness.setPassword(cerePlatformShop.getShopPassword());
        cerePlatformBusiness.setName(cerePlatformShop.getChargePersonName());
        cerePlatformBusiness.setPhone(cerePlatformShop.getChargePersonPhone());
        cerePlatformBusiness.setState(IntegerEnum.YES.getCode());
        cerePlatformBusiness.setIsDefault(IntegerEnum.YES.getCode());
        cerePlatformBusinessService.insert(cerePlatformBusiness);
        //同步新增商家用户绑定店铺数据
        CereBusinessShop cereBusinessShop = new CereBusinessShop();
        cereBusinessShop.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
        cereBusinessShop.setShopId(cerePlatformShop.getShopId());
        cereBusinessShop.setIfBinding(IntegerEnum.YES.getCode());
        cereBusinessShopService.insert(cereBusinessShop);
        //新增日志
        cerePlatformLogService.addLog(user, "商家管理", "平台端操作", "添加商家", String.valueOf(cereBusinessShop.getShopId()), time);
    }

    @Override
    public CerePlatformShop findByShopName(String shopName) {
        return cerePlatformShopDAO.findByShopName(shopName);
    }

    @Override
    public CerePlatformShop findByPhone(String shopPhone) {
        return cerePlatformShopDAO.findByPhone(shopPhone);
    }

    @Override
    public CerePlatformShop checkShopIdByName(String shopName, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByName(shopName, shopId);
    }

    @Override
    public CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByPhone(shopPhone, shopId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopUpdateParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        BeanUtils.copyProperties(param, cerePlatformShop);

        CerePlatformBusiness businessByPhone = cerePlatformBusinessService.findByPhone(cerePlatformShop.getChargePersonPhone());
        if (businessByPhone != null && !businessByPhone.getBusinessUserId().equals(param.getBusinessUserId())) {
            throw new CoBusinessException(CoReturnFormat.SHOP_PHONE_ALREADY);
        }

        if (EmptyUtils.isEmpty(param.getShopPassword())) {
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt("123456"));
        } else {
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt(param.getShopPassword()));
        }

        cerePlatformShop.setUpdateTime(time);
        cerePlatformShop.setHotFlag(param.isHotFlag());
        //修改商家数据
        cerePlatformShopDAO.updateById(cerePlatformShop);
        //根据商家用户id查询用户数据
        CerePlatformBusiness cerePlatformBusiness = cerePlatformShopDAO.findBusiness(param.getBusinessUserId());

        if (cerePlatformBusiness != null) {
            cerePlatformBusiness.setUpdateTime(time);
            cerePlatformBusiness.setUsername(cerePlatformShop.getShopPhone());
            cerePlatformBusiness.setPassword(cerePlatformShop.getShopPassword());
            cerePlatformBusiness.setName(cerePlatformShop.getChargePersonName());
            cerePlatformBusiness.setPhone(cerePlatformShop.getChargePersonPhone());
            cerePlatformBusinessService.update(cerePlatformBusiness);
        }
        //新增日志
        cerePlatformLogService.addLog(user, "商家管理", "平台端操作", "修改商家", String.valueOf(cerePlatformShop.getShopId()), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void startOrStop(ShopStartParam param, CerePlatformUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setState(param.getState());
        String describe = "";
        if (IntegerEnum.YES.getCode().equals(cerePlatformShop.getState())) {
            describe = "启用商家";
        } else {
            describe = "停用商家";
        }
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateById(cerePlatformShop);
        //同时下架所有商品
        cereShopProductService.unShelveByShopId(param.getShopId(), user);
        //新增日志
        cerePlatformLogService.addLog(user, "商家管理", "平台端操作", describe, String.valueOf(cerePlatformShop.getShopId()), time);
    }

    @Override
    public ShopGetAll getById(Long shopId) {
        ShopGetAll shop = cerePlatformShopDAO.getById(shopId);
        //解密密码
        shop.setShopPassword(EncryptUtil.decrypt(shop.getShopPassword()));
        return shop;
    }

    @Override
    public Page<ShopGetAll> getAll(ShopGetAllParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<ShopGetAll> list = cerePlatformShopDAO.getAll(param);
        for (ShopGetAll shop : list) {
            shop.setChargePersonName(encodeUtil.encodeInfo(shop.getChargePersonName()));
            shop.setChargePersonPhone(encodeUtil.encodePhone(shop.getChargePersonPhone()));
        }
        PageInfo<ShopGetAll> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Shop findShop(Long shopId) {
        return cerePlatformShopDAO.findShop(shopId);
    }

    @Override
    public void updateDate(CerePlatformShop cerePlatformShop) {
        cerePlatformShopDAO.updateDate(cerePlatformShop);
    }

    @Override
    public FinanceCount getAllFinance(FinanceParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Finance> list = cerePlatformShopDAO.getAllFinance(param);
        PageInfo<Finance> pageInfo = new PageInfo<>(list);
        if (!EmptyUtils.isEmpty(list)) {
            list = list.stream()
                    //查询冻结金额
                    .peek(a -> a.setFrozen(cerePlatformShopDAO.finFrozen(a.getShopId())))
                    //查询已提金额
                    .peek(a -> a.setHaveWithdrawable(cerePlatformShopDAO.findHaveWithdrawal(a.getShopId())))
                    //查询已退款金额
                    .peek(a -> a.setRefund(cerePlatformShopDAO.findRefund(a.getShopId())))
                    //可提现金额 = 总营收 - 已提现 - 冻结 - 提现中
                    .peek(a -> a.setWithdrawable(a.getRevenue().subtract(a.getHaveWithdrawable()).subtract(a.getFrozen()).subtract(cerePlatformShopDAO.findWithdrawing(a.getShopId())))).collect(Collectors.toList());
            pageInfo.setList(list);
        }
        Page<Finance> page = new Page<>(pageInfo.getList(), pageInfo.getTotal());
        FinanceCount count = new FinanceCount();
        BigDecimal decimal = BigDecimal.ZERO;
        //查询营收总额
        count.setRevenue(cerePlatformShopDAO.findRevenue());
        //查询冻结中金额
        count.setFrozen(cerePlatformShopDAO.finFrozen(null));
        //查询总的提现金额
        BigDecimal withdrawableMoney = cerePlatformShopDAO.getAllWithdrawableMoney();
        //查询提现中的金额
        BigDecimal stayMoney = cerePlatformShopDAO.getWithdrawableStayMoney();
        //查询已提现金额
        BigDecimal have = cerePlatformShopDAO.findHaveWithdrawal(null);
        count.setHaveWithdrawable(have);
        //计算可提现金额=总的提现金额-提现中金额-已提现金额
        count.setWithdrawable(decimal.add(withdrawableMoney).subtract(stayMoney).subtract(have));
        count.setWithdrawableStayMoney(count.getRevenue().subtract(count.getFrozen()).subtract(count.getWithdrawable()).subtract(count.getHaveWithdrawable()));
        //查询已退款金额
        count.setRefund(cerePlatformShopDAO.findRefund(null));
        count.setPage(page);
        return count;
    }

    @Override
    public Long findBusinessId(Long shopId) {
        return cerePlatformShopDAO.findBusinessId(shopId);
    }

    @Override
    public List<CerePlatformShop> findAll() {
        return cerePlatformShopDAO.findAll();
    }

    @Override
    public void updateShopStop(CerePlatformShop shop) {
        cerePlatformShopDAO.updateShopStop(shop);
    }

    @Override
    public Boolean cleanShop(Long shopId) {
        return cerePlatformShopDAO.cleanShop(shopId);
    }

    @Override
    public void cloneShop(ShopCloneParam param, CerePlatformUser user) {
        //新建商家数据
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformShop platformShop = cerePlatformShopDAO.selectById(param.getShopId());
        if (platformShop == null) {
            throw new CoBusinessException(CoReturnFormat.MERCHANT_NOT_EXIST);
        }

        for (ShopUpdateParam subParam : param.getCloneList()) {
            CerePlatformBusiness business = cerePlatformBusinessService.findByPhone(subParam.getShopPhone());
            if (business != null) {
                throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
            }
        }

        for (ShopUpdateParam subParam : param.getCloneList()) {
            CerePlatformShop shop = new CerePlatformShop();
            BeanUtils.copyProperties(platformShop, shop);
            shop.setShopId(null);
            shop.setShopName(subParam.getShopName());
            shop.setShopPhone(subParam.getShopPhone());
            shop.setChargePersonPhone(subParam.getShopPhone());
            shop.setShopPassword(EncryptUtil.encrypt("123456"));
            shop.setAuthenticationState(IntegerEnum.APPROVED.getCode());
            shop.setCheckState(IntegerEnum.UNTREATED.getCode());
            shop.setState(IntegerEnum.YES.getCode());
            //生成店铺编号
            String shopCode = commonService.getShopCode();
            shop.setShopCode(shopCode);
            shop.setCreateTime(time);
            shop.setUpdateTime(time);
            cerePlatformShopDAO.insert(shop);

            //同步新增商家用户数据
            CerePlatformBusiness cerePlatformBusiness = new CerePlatformBusiness();
            cerePlatformBusiness.setCreateTime(time);
            cerePlatformBusiness.setUsername(subParam.getShopPhone());
            cerePlatformBusiness.setPassword(shop.getShopPassword());
            cerePlatformBusiness.setName(shop.getChargePersonName());
            cerePlatformBusiness.setPhone(shop.getChargePersonPhone());
            cerePlatformBusiness.setState(IntegerEnum.YES.getCode());
            cerePlatformBusiness.setIsDefault(1);
            cerePlatformBusinessService.insert(cerePlatformBusiness);

            //同步新增商家用户绑定店铺数据
            CereBusinessShop cereBusinessShop = new CereBusinessShop();
            cereBusinessShop.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
            cereBusinessShop.setShopId(shop.getShopId());
            cereBusinessShop.setIfBinding(IntegerEnum.YES.getCode());
            cereBusinessShopService.insert(cereBusinessShop);
        }

        //新增日志
        cerePlatformLogService.addLog(user, "商家管理", "平台端操作", "克隆商家", String.valueOf(param.getShopId()), time);
    }

    @Override
    public List<Shop> selectHotShopList() {
        List<Shop> shopList = cerePlatformShopDAO.selectHotShopList();
        for (Shop shop : shopList) {
            BigDecimal star = cereShopCommentService.selectShopStar(shop.getShopId());
            shop.setShopStar(star);
        }
        return shopList;
    }
}
