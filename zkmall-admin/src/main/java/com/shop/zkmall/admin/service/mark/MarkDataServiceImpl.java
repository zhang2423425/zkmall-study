/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.mark;

import com.shop.zkmall.admin.dao.mark.MarkDataDao;
import com.shop.zkmall.admin.service.after.CereAfterProductService;
import com.shop.zkmall.admin.service.after.CereOrderAfterService;
import com.shop.zkmall.admin.service.log.CerePlatformLogService;
import com.shop.zkmall.admin.service.order.CereOrderParentService;
import com.shop.zkmall.admin.service.order.CereOrderProductService;
import com.shop.zkmall.admin.service.order.CereOrderReconciliationService;
import com.shop.zkmall.admin.service.order.CereShopOrderService;
import com.shop.zkmall.admin.service.pay.CerePayLogService;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.order.*;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarkDataServiceImpl implements MarkDataService {

    private final MarkDataDao markDataDao;

    private final CereShopOrderService cereShopOrderService;

    private final CereOrderParentService cereOrderParentService;

    private final CereOrderProductService cereOrderProductService;

    private final CereOrderReconciliationService cereOrderReconciliationService;

    private final CereOrderAfterService cereOrderAfterService;

    private final CereAfterProductService cereAfterProductService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CerePayLogService cerePayLogService;


    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void createOrder() {
        String time = TimeUtils.yyMMddHHmmss();
        //查询客户数据
        List<CereBuyerUser> users = markDataDao.findUsers();
        List<CereOrderProductAttribute> attributes = new ArrayList<>();
        for (CereBuyerUser user : users) {
            //查询2条最新的分销员数据
            for (int i = 0; i < 2; i++) {
                //查询收货地址数据
                CereBuyerReceive receive = markDataDao.findReceive(user.getBuyerUserId());
                if (receive != null) {
                    //新增订单数据
                    CereOrderParent parent = new CereOrderParent();
                    CereShopOrder order = new CereShopOrder();
                    //查询2条商品数据
                    List<CereProductSku> skus = markDataDao.findProducts();
                    if (!EmptyUtils.isEmpty(skus)) {
                        List<CereOrderProduct> orderProducts = new ArrayList<>();
                        BigDecimal decimal = BigDecimal.ZERO;
                        for (CereProductSku sku : skus) {
                            decimal = decimal.add(sku.getPrice());
                            CereOrderProduct cereOrderProduct = new CereOrderProduct();
                            cereOrderProduct.setProductId(sku.getProductId());
                            cereOrderProduct.setImage(sku.getSkuImage());
                            cereOrderProduct.setNumber(2);
                            cereOrderProduct.setProductName(sku.getCreateTime());
                            cereOrderProduct.setProductPrice(sku.getPrice());
                            cereOrderProduct.setSkuId(sku.getSkuId());
                            orderProducts.add(cereOrderProduct);
                        }
                        parent.setCreateTime(time);
                        parent.setOrderPrice(decimal);
                        parent.setPrice(decimal);
                        parent.setParentFormid(RandomStringUtil.getRandomCode(10, 0));
                        cereOrderParentService.insert(parent);
                        order.setParentId(parent.getParentId());
                        order.setOrderFormid(RandomStringUtil.getRandomCode(10, 0));
                        order.setOrderPrice(decimal);
                        order.setPrice(decimal);
                        order.setAfterState(0);
                        order.setState(2);
                        order.setBuyerUserId(user.getBuyerUserId());
                        order.setCreateTime(time);
                        order.setCustomerName(user.getWechatName());
                        order.setCustomerPhone(user.getPhone());
                        order.setPaymentMode(1);
                        order.setPaymentState(1);
                        order.setPaymentTime(time);
                        order.setShopId(1L);
                        order.setReceiveName(receive.getReceiveName());
                        order.setReceivePhone(receive.getReceivePhone());
                        order.setReceiveAdress(receive.getReceiveAdress());
                        order.setPostalCode("430000");
                        cereShopOrderService.insert(order);
                        //添加对账数据
                        CereOrderReconciliation reconciliation = new CereOrderReconciliation();
                        reconciliation.setOrderId(order.getOrderId());
                        reconciliation.setCreateTime(time);
                        reconciliation.setMoney(decimal);
                        reconciliation.setState(1);
                        reconciliation.setType(1);
                        cereOrderReconciliationService.insert(reconciliation);
                        //添加支付数据
                        CerePayLog payLog = new CerePayLog();
                        payLog.setCreateTime(time);
                        payLog.setOrderFormid(order.getOrderFormid());
                        payLog.setOutRefundNo(RandomStringUtil.getRandomCode(15, 0));
                        payLog.setOutTradeNo(RandomStringUtil.getRandomCode(15, 0));
                        payLog.setPaymentMode(1);
                        payLog.setTransactionId(RandomStringUtil.getRandomCode(15, 0));
                        payLog.setTotalFee(decimal);
                        payLog.setUserId(String.valueOf(user.getBuyerUserId()));
                        payLog.setShopId(1L);
                        payLog.setState("支付");
                        cerePayLogService.insert(payLog);
                        CerePlatformUser platformUser = new CerePlatformUser();
                        platformUser.setPlatformUserId(user.getBuyerUserId());
                        cerePlatformLogService.addLog(platformUser, "订单管理", "客户端操作", "d创建订单", String.valueOf(order.getOrderId()), time);
                        for (CereOrderProduct orderProduct : orderProducts) {
                            orderProduct.setOrderId(order.getOrderId());
                            cereOrderProductService.insert(orderProduct);
                            //根据规格id查询属性数据
                            CereOrderProductAttribute attribute = markDataDao.findBySkuId(orderProduct.getSkuId());
                            attribute.setOrderProductId(orderProduct.getOrderProductId());
                            attributes.add(attribute);
                        }
                    }
                }
            }
        }
        if (!EmptyUtils.isEmpty(attributes)) {
            cereOrderProductService.insertBatchAttibutes(attributes);
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void createAfter() {
        String time = TimeUtils.yyMMddHHmmss();
        //查询2条最新订单数据生成售后单
        List<CereShopOrder> list = markDataDao.findOrders();
        List<CereAfterProductAttribute> attributes = new ArrayList<>();
        int count = 0;
        for (CereShopOrder order : list) {
            CereOrderAfter cereOrderAfter = new CereOrderAfter();
            cereOrderAfter.setShopId(order.getShopId());
            cereOrderAfter.setOrderId(order.getOrderId());
            cereOrderAfter.setAfterState(1);
            cereOrderAfter.setAfterFormid(RandomStringUtil.getRandomCode(10, 0));
            cereOrderAfter.setCreateTime(time);
            cereOrderAfter.setExplain("不想要了");
            cereOrderAfter.setAfterType(1);
            if (count > 0) {
                cereOrderAfter.setExplain("不想买了");
            }
            count++;
            cereOrderAfter.setPrice(order.getPrice());
            cereOrderAfterService.insert(cereOrderAfter);
            CerePlatformUser platformUser = new CerePlatformUser();
            platformUser.setPlatformUserId(order.getBuyerUserId());
            cerePlatformLogService.addLog(platformUser, "售后管理", "客户端操作", "申请售后", String.valueOf(cereOrderAfter.getAfterId()), time);
            List<CereOrderProduct> products = markDataDao.findAfterProducts(order.getOrderId());
            if (!EmptyUtils.isEmpty(products)) {
                for (CereOrderProduct orderProduct : products) {
                    CereAfterProduct afterProduct = new CereAfterProduct();
                    BeanUtils.copyProperties(orderProduct, afterProduct);
                    cereAfterProductService.insert(afterProduct);
                    CereAfterProductAttribute attribute = markDataDao.findAfterAttribute(orderProduct.getOrderProductId());
                    attribute.setAfterProductId(afterProduct.getAfterProductId());
                    attributes.add(attribute);
                }
            }
        }
        cereAfterProductService.insertBatchAttibutes(attributes);
    }
}
