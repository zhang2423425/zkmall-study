/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.timing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 商品库存为0的，自动下线
 * 库存为0，虽然不可卖，但是还可以展示，后续新增让用户求补货的功能
 */
@Slf4j
@Component
public class ProductDownRunnable {

//    @Autowired
//    private CereShopProductService cereShopProductService;
//
//    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
//    public void run() {
//        try {
//            //查询所有规格库存都为0的商品数据
//            List<Long> ids=cereShopProductService.findAllZeroStockNumber();
//            if(!EmptyUtils.isEmpty(ids)){
//                //修改状态为已下架
//                cereShopProductService.updateBatchShelveState(ids, TimeUtils.yyMMddHHmmss());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
