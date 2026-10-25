/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.risk.impl;

import com.shop.zkmall.app.dao.risk.CereRiskBlackDAO;
import com.shop.zkmall.app.service.risk.CereRiskBlackService;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public List<CereRiskBlack> getEnabled() {
        return cereRiskBlackDAO.getEnabled();
    }
}
