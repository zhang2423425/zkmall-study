/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.customer_service.impl;

import com.shop.zkmall.business.service.customer_service.CereCustomerServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 客服表
 * </p>
 *
 * @author JustArgo
 * @date 2021-12-08
 */
@Slf4j
@Service
public class CereCustomerServiceServiceImpl implements CereCustomerServiceService {
/*
    @Autowired
    private CereCustomerServiceDAO cereCustomerServiceDAO;

    @Autowired
    private CereCustomerServiceReceptionistDAO cereCustomerServiceReceptionistDAO;

    @Override
    public int save(CereCustomerService com.shop.zkmall.admin.param) {
        String time = TimeUtils.yyMMddHHmmss();
        com.shop.zkmall.admin.param.setCreateTime(time);
        com.shop.zkmall.admin.param.setUpdateTime(time);
        com.shop.zkmall.admin.param.setState(IntegerEnum.YES.getCode());
        return cereCustomerServiceDAO.insert(com.shop.zkmall.admin.param);
    }

    @Override
    public int update(CereCustomerService com.shop.zkmall.admin.param) {
        String time = TimeUtils.yyMMddHHmmss();
        com.shop.zkmall.admin.param.setUpdateTime(time);
        com.shop.zkmall.admin.param.setState(IntegerEnum.YES.getCode());
        return cereCustomerServiceDAO.updateById(com.shop.zkmall.admin.param);
    }

    @Override
    public int delete(Long shopId, Long id) {
        LambdaQueryWrapper<CereCustomerServiceReceptionist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereCustomerServiceReceptionist::getServiceId, id);
        cereCustomerServiceReceptionistDAO.delete(wrapper);

        LambdaQueryWrapper<CereCustomerService> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(CereCustomerService::getShopId, shopId);
        wrapper2.eq(CereCustomerService::getId, id);
        cereCustomerServiceReceptionistDAO.delete(wrapper);
        return cereCustomerServiceDAO.delete(wrapper2);
    }

    @Override
    public CereCustomerService getById(Long id) {
        return cereCustomerServiceDAO.selectById(id);
    }

    @Override
    public int saveReceptionist(CereCustomerServiceReceptionist com.shop.zkmall.admin.param) {
        String time = TimeUtils.yyMMddHHmmss();
        com.shop.zkmall.admin.param.setCreateTime(time);
        com.shop.zkmall.admin.param.setUpdateTime(time);
        com.shop.zkmall.admin.param.setState(IntegerEnum.NO.getCode());
        return cereCustomerServiceReceptionistDAO.insert(com.shop.zkmall.admin.param);
    }

    @Override
    public int deleteReceptionist(Long id) {
        return cereCustomerServiceReceptionistDAO.deleteById(id);
    }

    @Override
    public List<CereCustomerService> getAll() {
        return cereCustomerServiceDAO.selectList(new QueryWrapper<>());
    }

    @Override
    public List<CereCustomerServiceReceptionist> getAllReceptionist(Long serviceId) {
        LambdaQueryWrapper<CereCustomerServiceReceptionist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereCustomerServiceReceptionist::getServiceId, serviceId);
        return cereCustomerServiceReceptionistDAO.selectList(wrapper);
    }*/

}
