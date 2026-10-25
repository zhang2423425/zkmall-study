/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.order;

import com.shop.zkmall.business.page.finance.Bank;
import com.shop.zkmall.business.page.finance.FinanceCount;
import com.shop.zkmall.business.page.finance.FlowingWater;
import com.shop.zkmall.business.page.finance.WithdrawalDetail;
import com.shop.zkmall.business.page.order.ShopOrder;
import com.shop.zkmall.business.param.finance.FinanceCountParam;
import com.shop.zkmall.business.param.finance.FinanceDetailParam;
import com.shop.zkmall.business.param.finance.FinanceWithdrawalParam;
import com.shop.zkmall.business.param.order.OrderGetAllParam;
import com.shop.zkmall.business.param.order.UpdateOrderPriceParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductSku;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopOrderService {

    Page<ShopOrder> getAll(OrderGetAllParam param);

    ShopOrder getById(Long shopId, Long orderId);

    void updateState(CereShopOrder cereShopOrder);

    FinanceCount getFinanceCount(FinanceCountParam param);

    Bank getBank(Long shopId);

    List<WithdrawalDetail> getWithdrawalDetails(FinanceWithdrawalParam param);

    Page<FlowingWater> getDetails(FinanceDetailParam param);

    void updateBatchStock(List<CereProductSku> skus);

    BigDecimal getWithdrawableStayMoney(Long shopId);

    BigDecimal getAllWithdrawableMoney(Long shopId);

    BigDecimal getWithdrawableMoney(Long shopId);

    CereShopOrder findById(Long orderId);

    void cancelBatch(List<Long> ids) throws Exception;

    void handleRefundWxLog(String orderFormid, String transactionId, String outTradeNo);

    List<CereShopOrder> findUnPayBySeckillId(Long shopSeckillId);

    void updateBatchCancelOrder(List<CereShopOrder> list);

    List<CereShopOrder> findUnPayByDiscountId(Long shopDiscountId);

    List<ShopOrder> syncOrderList(Long shopId, Long lastOrderId, Integer pageSize);

    void updateOrderPrice(UpdateOrderPriceParam param, CerePlatformBusiness user);

    Map<Long, Integer> selectSalesVolumeBySkuIdList(List<Long> skuIdList);

    List<CereShopOrder> findByIdList(List<Long> orderIdList);
}
