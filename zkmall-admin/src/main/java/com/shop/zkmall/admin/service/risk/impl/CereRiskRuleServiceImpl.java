/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.risk.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.admin.dao.risk.CereRiskRuleDAO;
import com.shop.zkmall.admin.service.risk.CereRiskRuleService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 风控规则表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereRiskRuleServiceImpl implements CereRiskRuleService {

    private final CereRiskRuleDAO cereRiskRuleDAO;

    @Override
    public Page<CereRiskRule> getAllRiskRule(PageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        LambdaQueryWrapper<CereRiskRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CereRiskRule::getUpdateTime);
        List<CereRiskRule> list = cereRiskRuleDAO.selectList(wrapper);
        PageInfo<CereRiskRule> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public int save(CereRiskRule param) {
        String time = TimeUtils.yyMMddHHmmss();
        param.setCreateTime(time);
        param.setUpdateTime(time);
        return cereRiskRuleDAO.insert(param);
    }

    @Override
    public int update(CereRiskRule param) {
        String time = TimeUtils.yyMMddHHmmss();
        param.setUpdateTime(time);
        if (IntegerEnum.YES.getCode().equals(param.getStatus())) {
            cereRiskRuleDAO.switchOffAll();
        }
        return cereRiskRuleDAO.updateById(param);
    }

    @Override
    public int delete(CereRiskRule param) {
        return cereRiskRuleDAO.deleteById(param.getId());
    }
}
