/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after.impl;

import com.shop.zkmall.app.dao.after.CereAfterProductAttributeDAO;
import com.shop.zkmall.app.service.after.CereAfterProductAttributeService;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereAfterProductAttributeServiceImpl implements CereAfterProductAttributeService {

    private final CereAfterProductAttributeDAO cereAfterProductAttributeDAO;

    @Override
    public void insertBatch(List<CereAfterProductAttribute> attributes) {
        cereAfterProductAttributeDAO.insertBatch(attributes);
    }

}
