/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.risk.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.risk.CereRiskBlackDAO;
import com.shop.zkmall.admin.page.risk.CereRiskUserBlack;
import com.shop.zkmall.admin.param.risk.CereRiskBlackPageParam;
import com.shop.zkmall.admin.service.risk.CereRiskBlackService;
import com.shop.zkmall.admin.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 黑名单表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereRiskBlackServiceImpl implements CereRiskBlackService {

    private final CereRiskBlackDAO cereRiskBlackDAO;

    private final EncodeUtil encodeUtil;

    @Override
    public Page<CereRiskBlack> getAllIpBlackList(CereRiskBlackPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        LambdaQueryWrapper<CereRiskBlack> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(param.getIp())) {
            wrapper.like(CereRiskBlack::getIp, "%" + param.getIp() + "%");
        }
        if (param.getState() != null) {
            wrapper.eq(CereRiskBlack::getState, param.getState());
        }
        wrapper.orderByDesc(CereRiskBlack::getUpdateTime);
        List<CereRiskBlack> list = cereRiskBlackDAO.selectList(wrapper);
        PageInfo<CereRiskBlack> pageInfo = new PageInfo<>(list);
        return (Page<CereRiskBlack>) new Page(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Page<CereRiskUserBlack> getUserBlackList(CereRiskBlackPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CereRiskUserBlack> list = cereRiskBlackDAO.getUserBlackList(param);
        for (CereRiskUserBlack black : list) {
            black.setName(encodeUtil.encodeInfo(black.getName()));
            black.setPhone(encodeUtil.encodePhone(black.getPhone()));
            black.setOpenId(encodeUtil.encodeInfo(black.getOpenId()));
        }
        PageInfo<CereRiskUserBlack> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public long save(CereRiskBlack param) {
        String time = TimeUtils.yyMMddHHmmss();
        param.setCreateTime(time);
        param.setUpdateTime(time);
        if (IntegerEnum.RISK_BLACK_TYPE_IP.getCode().equals(param.getType())) {
            String[] ipArr = StringUtils.trimToEmpty(param.getIp()).split(",");
            if (ipArr.length == 0) {
                return 0;
            }
            List<String> ipList = Arrays.asList(ipArr);
            LambdaQueryWrapper<CereRiskBlack> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(CereRiskBlack::getIp, ipList);
            long count = cereRiskBlackDAO.selectCount(wrapper);
            if (count > 0) {
                throw new CoBusinessException(CoReturnFormat.RISK_BLACK_IP_EXISTS);
            }
            count = 0;
            for (String ip : ipList) {
                CereRiskBlack cereRiskBlack = new CereRiskBlack();
                BeanUtils.copyProperties(param, cereRiskBlack);
                cereRiskBlack.setIp(ip);
                count += cereRiskBlackDAO.insert(cereRiskBlack);
            }
            return count;
        } else {
            return cereRiskBlackDAO.insert(param);
        }
    }

    @Override
    public int update(CereRiskBlack param) {
        String time = TimeUtils.yyMMddHHmmss();
        param.setUpdateTime(time);
        param.setIp(null);
        param.setBuyerUserId(null);
        param.setType(null);
        return cereRiskBlackDAO.updateById(param);
    }

    @Override
    public int delete(CereRiskBlack param) {
        return cereRiskBlackDAO.deleteById(param.getId());
    }

}
