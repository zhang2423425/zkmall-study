/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.buyer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.business.dao.buyer.CereBuyerUserDAO;
import com.shop.zkmall.business.page.buyer.BuyerUser;
import com.shop.zkmall.business.page.buyer.BuyerUserDetail;
import com.shop.zkmall.business.param.buyer.BuyerUserGetAllParam;
import com.shop.zkmall.business.param.buyer.UserSaveParam;
import com.shop.zkmall.business.param.buyer.UserUpdateParam;
import com.shop.zkmall.business.service.buyer.CereBuyerUserService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.business.CereShopIdBuyerUserIdTimeDTO;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EncryptUtil;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerUserServiceImpl implements CereBuyerUserService {

    private final CereBuyerUserDAO cereBuyerUserDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final EncodeUtil encodeUtil;

    @Value("${defaultHeadImg}")
    private String defaultHeadImg;

    @Override
    public Page<BuyerUser> getAll(BuyerUserGetAllParam param) {
        if (param.getPage() != null && param.getPageSize() != null) {
            PageHelper.startPage(param.getPage(), param.getPageSize());
        }
        PageInfo<BuyerUser> pageInfo = new PageInfo<>(Collections.emptyList());
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(UserSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验手机号是否存在
        CereBuyerUser cereBuyerUser = cereBuyerUserDAO.checkPhone(param.getPhone());
        if (cereBuyerUser != null) {
            throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY);
        }
        cereBuyerUser = new CereBuyerUser();
        cereBuyerUser.setCreateTime(time);
        cereBuyerUser.setBirthday(param.getBirthday());
        cereBuyerUser.setName("MJ" + RandomStringUtil.getRandomCode(4, 0));
        cereBuyerUser.setWechatName(param.getName());
        cereBuyerUser.setSex(param.getSex());
        cereBuyerUser.setPhone(param.getPhone());
        cereBuyerUser.setState(IntegerEnum.YES.getCode());
        cereBuyerUser.setIfBlack(IntegerEnum.NO.getCode());
        cereBuyerUser.setRemark(param.getRemark());
        cereBuyerUser.setPassword(EncryptUtil.encrypt("123456"));
        cereBuyerUser.setToken(RandomStringUtil.getRandomCode(32, 0));
        cereBuyerUser.setHeadImage(defaultHeadImg);
        cereBuyerUser.setTerminal(IntegerEnum.TERMINAL_6.getCode());
        cereBuyerUserDAO.insert(cereBuyerUser);
        Long buyerUserId = cereBuyerUser.getBuyerUserId();

        //新增日志
        cerePlatformLogService.addLog(user, "客户管理", "商户端操作", "添加客户", buyerUserId, time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(UserUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereBuyerUser cereBuyerUser = new CereBuyerUser();
        cereBuyerUser.setBuyerUserId(param.getBuyerUserId());
        cereBuyerUser.setCreateTime(time);
        cereBuyerUser.setBirthday(param.getBirthday());
        cereBuyerUser.setName("MJ" + RandomStringUtil.getRandomCode(4, 0));
        cereBuyerUser.setWechatName(param.getName());
        cereBuyerUser.setSex(param.getSex());
        cereBuyerUser.setState(IntegerEnum.YES.getCode());
        cereBuyerUser.setIfBlack(IntegerEnum.NO.getCode());
        cereBuyerUser.setRemark(param.getRemark());
        cereBuyerUser.setPassword(EncryptUtil.encrypt("123456"));
        cereBuyerUser.setToken(RandomStringUtil.getRandomCode(32, 0));
        //更新客户
        cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
        Long buyerUserId = cereBuyerUser.getBuyerUserId();
        //新增日志
        cerePlatformLogService.addLog(user, "客户管理", "商户端操作", "修改客户", buyerUserId, time);
    }

    @Override
    public BuyerUserDetail getById(Long shopId, Long buyerUserId) {
        BuyerUserDetail detail = cereBuyerUserDAO.getById(shopId, buyerUserId);
        //查询客户关联标签
        if (detail != null) {
            detail.setName(encodeUtil.encodeInfo(detail.getName()));
            detail.setPhone(encodeUtil.encodePhone(detail.getPhone()));
        }
        return detail;
    }

    @Override
    public void initBusinessBuyerUser() {
        List<CereShopIdBuyerUserIdTimeDTO> visitRecordList = cereBuyerUserDAO.findVisitRecord();
        List<CereShopIdBuyerUserIdTimeDTO> consumeRecordList = cereBuyerUserDAO.findConsumeRecord();
        Map<String, String> map = consumeRecordList.stream().collect(Collectors.groupingBy(dto -> dto.getShopId() + "-" + dto.getBuyerUserId(), Collectors.mapping(CereShopIdBuyerUserIdTimeDTO::getTime, Collectors.joining())));
        int lastLogIndex = 0;
        int size = visitRecordList.size();
        int index = 0;
        for (CereShopIdBuyerUserIdTimeDTO record : visitRecordList) {
            index++;
            int percent = (int) (index / 1.0 / size * 100);
            if (percent != lastLogIndex) {
                log.info("init progress {}%", percent);
                lastLogIndex = percent;
            }
        }
    }

    @Override
    public void updateGrowth(Long buyerUserId, int growth) {
        cereBuyerUserDAO.updateGrowth(buyerUserId, growth);
    }
}
