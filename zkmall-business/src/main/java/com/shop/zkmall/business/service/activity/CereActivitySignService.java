/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.activity;

import com.shop.zkmall.business.page.finance.BondCount;
import com.shop.zkmall.business.param.finance.BondParam;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereActivitySignService {

    BondCount getAllBond(BondParam param);

    void deleteById(Long signId);

    void deleteDetailsBySignId(Long signId);

    void deleteLogBySignId(Long signId);

}
