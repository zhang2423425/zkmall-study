/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shop.zkmall.app.dao.order.CereOrderDileverDAO;
import com.shop.zkmall.app.dao.order.CereOrderParentDAO;
import com.shop.zkmall.app.dao.order.CereShopOrderDAO;
import com.shop.zkmall.app.dao.product.CereProductStatsByDayDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.*;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.settlement.Distribution;
import com.shop.zkmall.app.page.settlement.Settlement;
import com.shop.zkmall.app.page.settlement.SettlementShop;
import com.shop.zkmall.app.param.comment.CommentSaveParam;
import com.shop.zkmall.app.param.order.*;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.app.param.settlement.SettlementParam;
import com.shop.zkmall.app.param.settlement.ShopProductParam;
import com.shop.zkmall.app.pay.PayFactory;
import com.shop.zkmall.app.pay.PayService;
import com.shop.zkmall.app.pay.alipay.service.AliPayService;
import com.shop.zkmall.app.pay.weixin.service.WxPayService;
import com.shop.zkmall.app.service.after.CereAfterDileverService;
import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.app.service.buyer.CereBuyerReceiveService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.dict.CerePlatformDictService;
import com.shop.zkmall.app.service.log.CerePlatformLogService;
import com.shop.zkmall.app.service.logistics.CereOrderLogisticsService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.app.service.order.CereOrderParentService;
import com.shop.zkmall.app.service.order.CereOrderProductService;
import com.shop.zkmall.app.service.order.CereOrderReconciliationService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.app.service.order.placeOrderTemplate.PlaceOrderFactory;
import com.shop.zkmall.app.service.order.placeOrderTemplate.PlaceOrderTemplate;
import com.shop.zkmall.app.service.pay.CerePayLogService;
import com.shop.zkmall.app.service.product.CereCommentWordService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.sensitive.CerePlatformSensitiveService;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.app.utils.DistributionUtil;
import com.shop.zkmall.app.utils.RedisUtil;
import com.shop.zkmall.commons.config.AlipayConfig;
import com.shop.zkmall.commons.config.ZkMallProperties;
import com.shop.zkmall.commons.constant.*;
import com.shop.zkmall.commons.domain.after.CereAfterDilever;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.after.CereOrderDilever;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.domain.express.ShippingTrace;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.order.CereOrderParent;
import com.shop.zkmall.commons.domain.order.CereOrderReconciliation;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import com.shop.zkmall.commons.domain.product.CereCommentWord;
import com.shop.zkmall.commons.domain.product.CereProductStatsByDay;
import com.shop.zkmall.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;
import com.shop.zkmall.commons.enums.LogisticsTypeEnum;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.*;
import com.shop.zkmall.commons.utils.third.WXPayConstants;
import com.shop.zkmall.express.IExpressService;
import com.shop.zkmall.oss.FileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopOrderServiceImpl implements CereShopOrderService {

    private final CereShopOrderDAO cereShopOrderDAO;

    private final CereOrderParentDAO cereOrderParentDAO;

    private final CereOrderLogisticsService cereOrderLogisticsService;

    private final CereProductSkuService cereProductSkuService;

    private final CereOrderParentService cereOrderParentService;

    private final CereOrderProductService cereOrderProductService;

    private final WxPayService wxPayService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereOrderReconciliationService cereOrderReconciliationService;

    private final CerePayLogService cerePayLogService;

    private final CereShopCommentService cereShopCommentService;

    private final CerePlatformSensitiveService cerePlatformSensitiveService;

    private final CereCommentWordService cereCommentWordService;

    private final CereAfterDileverService cereAfterDileverService;

    private final CereBuyerReceiveService cereBuyerReceiveService;

    private final CerePlatformDictService cerePlatformDictService;

    private final IExpressService expressService;

    private final ZkMallProperties zkMallProperties;

    private final FileStrategy fileStrategy;

    private final CereOrderAfterService cereOrderAfterService;

    private final CereNoticeService cereNoticeService;

    private final CereBuyerUserService cereBuyerUserService;

    private final CereStockService cereStockService;

    private final PlaceOrderFactory placeOrderFactory;

    private final CereProductStatsByDayDAO cereProductStatsByDayDAO;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    private final CereOrderDileverDAO cereOrderDileverDAO;

    private final CerePlatformShopDAO cerePlatformShopDAO;

    private final RedisUtil redisUtil;

    private final AlipayConfig alipayConfig;

    /**
     * app支付版本号
     */
    @Value("${weixin.app_pay_version}")
    private String app_pay_version;

    @Value("${querySkuRealInfo}")
    private boolean querySkuRealInfo;

    @Override
    public Settlement getSettlement(SettlementParam param, CereBuyerUser user) {
        Settlement settlement = new Settlement();
        if (EmptyUtils.isEmpty(param.getReceiveId())) {
            //查询默认地址
            settlement.setReceive(cereBuyerReceiveService.findlatelyReceiveByUserId(user.getBuyerUserId()));
        } else {
            settlement.setReceive(cereBuyerReceiveService.findById(param.getReceiveId()));
        }
        //结算处理
        if (querySkuRealInfo) {
            getSettlementInnerForRealInfo(settlement, param, user);
        } else {
            getSettlementInner(settlement, param, user);
        }

        settlement.setHuabeiChargeType(alipayConfig.getHuabeiChargeType());
        settlement.setHuabeiFeerateList(alipayConfig.getHuabeiFeerateList());
        return settlement;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {CoBusinessException.class, Exception.class})
    public PayUrl submit(OrderParam param, CereBuyerUser user, String ip) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }

        PlaceOrderTemplate placeOrderTemplate = placeOrderFactory.getPlaceOrderTemplate(param.getType());
        return placeOrderTemplate.placeOrder(param, user, ip, time);
    }

    @Override
    public Integer findPayUsers(Long productId, Long skuId) {
        return cereShopOrderDAO.findPayUsers(productId, skuId);
    }

    @Override
    public List<BroadCastDTO> findRecentOrder(String oneHourAgo, Long productId) {
        return cereShopOrderDAO.findRecentOrder(oneHourAgo, productId);
    }

    @Override
    public String gotoAliPcPay(PayParam param, CereBuyerUser user, String ip) {
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }

        try {
            String orderFormId;
            BigDecimal price;
            if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
                //父订单提交,查询父订单编号
                CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
                orderFormId = parent.getParentFormid();
                price = parent.getPrice();
            } else {
                //子订单提交,查询子订单编号
                CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
                orderFormId = order.getOrderFormid();
                price = order.getPrice();
            }
            System.out.println("支付订单号为：" + orderFormId);
            //调用支付接口
            PayService payService = PayFactory.getPayService(param.getPaymentMode());
            String payForm = ((AliPayService) payService).getPayForm(orderFormId, price, ip);
            System.out.println("支付返回结果集" + payForm);
            return payForm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String gotoAliCodePay(PayParam param, CereBuyerUser user, String ip) {
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }

        try {
            String orderFormId;
            BigDecimal price;
            if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
                //父订单提交,查询父订单编号
                CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
                orderFormId = parent.getParentFormid();
                price = parent.getPrice();
            } else {
                //子订单提交,查询子订单编号
                CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
                orderFormId = order.getOrderFormid();
                price = order.getPrice();
            }
            System.out.println("支付订单号为：" + orderFormId);
            //调用支付接口
            PayService payService = PayFactory.getPayService(param.getPaymentMode());
            String collectionCode = payService.getOrderCollectionCode(orderFormId, price, ip, null);
            System.out.println("支付返回结果集" + collectionCode);
            return collectionCode;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    @Override
    public OrderPay gotoPay(PayParam param, CereBuyerUser user, String ip) throws Exception {
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }

        String orderFormId;
        BigDecimal price;
        if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId = parent.getParentFormid();
            price = parent.getPrice();
        } else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId = order.getOrderFormid();
            price = order.getPrice();
        }
        System.out.println("支付订单号为：" + orderFormId);
        //调用支付接口
        PayService payService = PayFactory.getPayService(param.getPaymentMode());
        String openId = user.getWechatOpenId();
        if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(param.getPaymentMode())) {
            openId = user.getAliUserId();
        }
        Map<String, String> map = payService.gotoPay(orderFormId, price, openId, ip, ParamEnum.PAY_XCX.getCode(), param.getHuabeiPeriod());
        System.out.println("支付返回结果集" + map.toString());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
    }


    @Override
    public OrderPay gotoAppPay(PayParam param, CereBuyerUser user, String ip) throws Exception {
        String orderFormId;
        BigDecimal price;
        if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId = parent.getParentFormid();
            price = parent.getPrice();
        } else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId = order.getOrderFormid();
            price = order.getPrice();
        }
        //调用微信支付接口
        Integer payType = ParamEnum.PAY_APP.getCode();
        if (WXPayConstants.APP_PAY_VERSION_V3.equals(app_pay_version)) {
            payType = ParamEnum.PAY_APP_V3.getCode();
        }
        Map<String, String> map = wxPayService.gotoPay(orderFormId, price, user.getWechatOpenId(), ip, payType, param.getHuabeiPeriod());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
    }

    @Override
    public OrderPay gotoH5Pay(PayParam param, CereBuyerUser user, String ip) throws Exception {
        String orderFormId;
        BigDecimal price;
        if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId = parent.getParentFormid();
            price = parent.getPrice();
        } else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId = order.getOrderFormid();
            price = order.getPrice();
        }
        //调用微信支付接口
        Map<String, String> map = wxPayService.gotoPay(orderFormId, price, user.getWechatOpenId(), ip, ParamEnum.PAY_H5.getCode(), param.getHuabeiPeriod());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleWxLog(String orderFormId, String transactionId, String orderNo) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<CereShopOrder> list;
        //查询是否为父订单编号
        CereOrderParent parent = cereShopOrderDAO.findByParentFormid(orderFormId);
        if (parent != null) {
            //查询所有子订单数据
            list = cereShopOrderDAO.findByParentId(parent.getParentId());
        } else {
            //子订单支付回调
            list = cereShopOrderDAO.findByFormid(orderFormId);
        }
        //支付业务处理
        if (!EmptyUtils.isEmpty(list)) {
            BigDecimal total = BigDecimal.ZERO;
            List<Long> orderIdList = new ArrayList<>();
            for (CereShopOrder order : list) {
                total = total.add(order.getPrice());
                //正常下单业务处理
                handleOrder(order, transactionId, orderNo, time);
                orderIdList.add(order.getOrderId());
            }
            //更新商品销量
            List<CartSku> statsList = cereOrderProductService.findProductStatsByOrderIds(orderIdList);
            if (CollectionUtils.isNotEmpty(statsList)) {
                Map<Long, List<CartSku>> productOpMap = statsList.stream().collect(Collectors.groupingBy(CartSku::getProductId));
                //更新日度统计
                for (Long productId : productOpMap.keySet()) {
                    List<CartSku> skuList = productOpMap.get(productId);
                    Long shopId = skuList.get(0).getShopId();
                    int number = skuList.stream().map(CartSku::getNumber).reduce(Integer::sum).orElse(0);
                    CereProductStatsByDay stats = new CereProductStatsByDay();
                    stats.setShopId(shopId);
                    stats.setCreateDate(TimeUtils.today());
                    stats.setProductId(productId);
                    stats.setAddCartCount(0);
                    stats.setVisitCount(0);
                    stats.setSalesVolume(number);
                    cereProductStatsByDayDAO.insertOrUpdate(stats);
                }
                //更新sku实时信息(销量)
                for (CartSku stat : statsList) {
                    cereSkuMemberRealInfoDAO.increSalesVolumeBy(stat.getSkuId(), stat.getNumber());
                }
            }

            //支付金额四舍五入
            total = total.setScale(0, RoundingMode.UP);
            //增加成长值
            cereBuyerUserService.updateGrowth(list.get(0).getBuyerUserId(), total.intValue());
        }
    }

    private void handleOrder(CereShopOrder order, String transactionId, String orderNo, String time) throws Exception {
        //修改订单支付状态和订单状态
        order.setUpdateTime(time);
        order.setPaymentState(IntegerEnum.ORDER_PAY_SUCCESS.getCode());
        order.setPaymentTime(time);
        order.setState(IntegerEnum.ORDER_STAY_DILEVERY.getCode());
        cereShopOrderDAO.updateByPrimaryKeySelective(order);
        //新增订单对账数据
        addReconciliation(order, time);
        //新增支付流水数据
        addPayLog(order, time, orderNo, transactionId);
        redisUtil.removeFromQueue(String.valueOf(order.getOrderId()), StringEnum.ORDER_AUTOMATIC_CANCEL.getCode());
        //新增订单支付成功消息
        CereNotice cereNotice = new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
        cereNotice.setNoticeContent("您购买的" + order.getOrderFormid() + "已支付成功，请耐心等待商家发货");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }


    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleRefundWxLog(String orderFormId, String transaction_id, String orderNo) {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单数据
        CereShopOrder order = cereShopOrderDAO.findByOrderFormid(orderFormId);
        if (order != null) {
            //新增对账单数据
            CereOrderReconciliation reconciliation = new CereOrderReconciliation();
            reconciliation.setOrderId(order.getOrderId());
            reconciliation.setMoney(order.getPrice());
            reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
            reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
            reconciliation.setCreateTime(time);
            cereOrderReconciliationService.insert(reconciliation);
            //查询支付流水
            CerePayLog cerePayLog = cereShopOrderDAO.findPayLog(order.getOrderFormid(), StringEnum.PAY_LOG_PAY.getCode());
            //插入退款流水
            CerePayLog payLog = new CerePayLog();
            payLog.setCreateTime(time);
            payLog.setOrderFormid(order.getOrderFormid());
            payLog.setOutTradeNo(orderNo);
            payLog.setTransactionId(transaction_id);
            payLog.setPaymentMode(order.getPaymentMode());
            payLog.setShopId(order.getShopId());
            payLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
            payLog.setTotalFee(order.getPrice());
            payLog.setUserId(String.valueOf(order.getBuyerUserId()));
            payLog.setOutRefundNo(cerePayLog.getOutRefundNo());
            payLog.setRemark(order.getCustomerName() + "取消订单退款" + order.getPrice() + "元,退款单号为：" + cerePayLog.getOutRefundNo()
                    + ",退款时间为" + time);
            cerePayLogService.insert(payLog);

            //更新订单状态为已取消
            order.setUpdateTime(time);
            order.setState(IntegerEnum.ORDER_STOP.getCode());
            order.setPrice(BigDecimal.ZERO);
            order.setPaymentState(IntegerEnum.NO.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);

            //扣减成长值
            int growth = payLog.getTotalFee().setScale(0, RoundingMode.UP).negate().intValue();
            log.info(" buyerUserId {} growth {}", order.getBuyerUserId(), growth);
            cereBuyerUserService.updateGrowth(order.getBuyerUserId(), growth);
        }
    }

    @Override
    public Page<Orders> getAll(OrderGetAllParam param, CereBuyerUser user) throws Exception {
        param.setBuyerUserId(user.getBuyerUserId());
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Orders> list = cereShopOrderDAO.getAll(param);
        if (!EmptyUtils.isEmpty(list)) {
            List<Long> orderIdList = list.stream().map(Orders::getOrderId).collect(Collectors.toList());
            Map<Long, List<CereOrderDilever>> deliverMap = cereOrderDileverDAO
                    .selectList(Wrappers.<CereOrderDilever>lambdaQuery().in(CereOrderDilever::getOrderId, orderIdList))
                    .stream().collect(Collectors.groupingBy(CereOrderDilever::getOrderId));
            List<Long> dictIdList = deliverMap.values().stream().flatMap(Collection::stream).map(CereOrderDilever::getExpress).collect(Collectors.toList());
            Map<Long, String> dictNameMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(dictIdList)) {
                dictNameMap = cerePlatformDictService.selectDictNameMap(dictIdList);
            }
            //查售后数据
            List<CereOrderAfter> afterList = cereOrderAfterService.selectByOrderIdList(orderIdList);
            afterList.sort((o1, o2) -> {
                if (o1.getUpdateTime() == null) {
                    return -1;
                }
                if (o2.getUpdateTime() == null) {
                    return 1;
                }
                return o2.getUpdateTime().compareTo(o1.getUpdateTime());
            });
            Map<Long, Integer> afterStateMap = afterList.stream().collect(Collectors.groupingBy(CereOrderAfter::getOrderId,
                    Collectors.collectingAndThen(Collectors.toList(), lst -> lst.stream().map(CereOrderAfter::getAfterState).findFirst().orElse(null))));
            for (Orders orders : list) {
                Long orderId = orders.getOrderId();
                //设置物流公司名称
                List<CereOrderDilever> deliverList = deliverMap.get(orderId);
                if (CollectionUtils.isNotEmpty(deliverList)) {
                    String expressName = dictNameMap.get(deliverList.get(0).getExpress());
                    orders.setExpress(expressName);
                    orders.setDeliverFormid(deliverList.get(0).getDeliverFormid());
                }
                //设置售后状态 一个订单可能有多个商品，分多次售后，这里查最近一次更新售后的状态
                Integer afterState = afterStateMap.get(orderId);
                orders.setAfterState(afterState != null ? afterState.toString() : null);
                //计算自动关闭订单倒计时
                orders.setTime(30 * 60 * 1000 - TimeUtils.getCountDownByTime(orders.getCreateTime(), TimeUtils.yyMMddHHmmss()));
                //查询订单商品明细数据
                List<CartSku> skus = cereOrderProductService.findOrderProductSku(orders.getOrderId());
                if (!EmptyUtils.isEmpty(skus)) {
                    //设置规格值数组
                    skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
                }
                //查询该订单已评价商品数量
                int comment = cereShopCommentService.findProductNumber(orders.getOrderId());
                if (skus.size() == comment) {
                    //该订单商品全部已评价
                    orders.setIfNotComment(IntegerEnum.NO.getCode());
                } else {
                    orders.setIfNotComment(IntegerEnum.YES.getCode());
                }
                orders.setSkus(skus);
            }
        }
        PageInfo<Orders> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public OrderDetail getById(OrderGetByIdParam param) throws Exception {
        Long orderId = param.getOrderId();
        OrderDetail detail = cereShopOrderDAO.getById(orderId);
        if (detail != null) {
            //查询订单商品明细
            List<CartSku> skus = cereOrderProductService.findOrderProductSku(detail.getOrderId());
            if (!EmptyUtils.isEmpty(skus)) {
                //设置规格值数组
                skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
            }
            detail.setSkus(skus);
            //计算自动关闭订单倒计时
            detail.setTime(30 * 60 * 1000 - TimeUtils.getCountDownByTime(detail.getCreateTime(), TimeUtils.yyMMddHHmmss()));
        }
        return detail;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void confirm(OrderGetByIdParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验当前订单状态是否为待收货
        CereShopOrder cereShopOrder = cereShopOrderDAO.checkState(param.getOrderId(), IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
        if (cereShopOrder == null) {
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //修改订单状态为已完成
        CereShopOrder order = cereShopOrderDAO.selectByPrimaryKey(param.getOrderId());
        order.setOrderId(param.getOrderId());
        order.setUpdateTime(time);
        order.setState(IntegerEnum.ORDER_FINISH.getCode());
        order.setReceiveTime(time);
        cereShopOrderDAO.updateByPrimaryKeySelective(order);
        redisUtil.removeFromQueue(String.valueOf(param.getOrderId()), StringEnum.ORDER_CONFIRM_DILEVERY.getCode());
        //修改订单对账单状态为解冻
        CereOrderReconciliation reconciliation = new CereOrderReconciliation();
        reconciliation.setOrderId(param.getOrderId());
        reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
        reconciliation.setUpdateTime(time);
        cereOrderReconciliationService.updateByOrderId(reconciliation);
        //新增订单已完成消息
        CereNotice cereNotice = new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_FINISH.getCode());
        cereNotice.setNoticeContent("订单" + order.getOrderFormid() + "已确认收货，期待您分享商品评价和购物心得");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

    @Override
    public void update(CereShopOrder cereShopOrder) {
        cereShopOrderDAO.updateByPrimaryKeySelective(cereShopOrder);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void cancel(OrderGetByIdParam param, CereBuyerUser user) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //校验当前订单状态是否为待付款或者待发货或者待成团
        CereShopOrder cereShopOrder = cereShopOrderDAO.checkCancelState(param.getOrderId());
        if (cereShopOrder == null) {
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //修改订单状态为已取消
        CereShopOrder order = new CereShopOrder();
        order.setOrderId(param.getOrderId());
        order.setUpdateTime(time);
        order.setState(IntegerEnum.ORDER_STOP.getCode());
        cereShopOrderDAO.updateByPrimaryKeySelective(order);

        redisUtil.removeFromQueue(String.valueOf(param.getOrderId()), StringEnum.ORDER_AUTOMATIC_CANCEL.getCode());

        //订单库存回流
        cereStockService.rollbackStockByOrderId(order.getOrderId());

        redisUtil.removeFromQueue(String.valueOf(param.getOrderId()), StringEnum.ORDER_CONFIRM_DILEVERY.getCode());

        if (IntegerEnum.ORDER_STAY_DILEVERY.getCode().equals(cereShopOrder.getState())
                || IntegerEnum.YES.getCode().equals(cereShopOrder.getPaymentState())) {
            //如果是待发货或者待成团（且付款）取消,需要退款,查询支付流水数据
            CerePayLog refund = cereShopOrderDAO.findPayLog(cereShopOrder.getOrderFormid(), StringEnum.PAY_LOG_PAY.getCode());
            if (refund != null) {
                PayService payService = PayFactory.getPayService(refund.getPaymentMode());
                Map<String, String> resultMap = payService.orderRefund(refund.getTransactionId(), refund.getOutRefundNo(), refund.getTotalFee(), cereShopOrder.getPrice());
                if (!EmptyUtils.isEmpty(resultMap)) {
                    if (resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode()) &&
                            resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())) {
                        //退款成功 支付宝的退款是立即退款的，需要同步处理
                        if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(cereShopOrder.getPaymentMode())) {
                            //内部调用不会执行事务，所以通过springUtil获取service对象
                            CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
                            cereShopOrderService.handleRefundWxLog(refund.getOrderFormid(), refund.getTransactionId(), refund.getOutTradeNo());
                        }
                    } else if (resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())) {
                        //退款失败,商户余额不足
                        throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                    }
                }
            }
        }
    }

    @Override
    public CereShopOrder findById(Long orderId) {
        return cereShopOrderDAO.selectByPrimaryKey(orderId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) {
        cereShopOrderDAO.updateBuyerData(buyerUserId, id);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void addComment(CommentSaveParam commentParam, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(commentParam.getParams())) {
            //查询敏感词设置
            CerePlatformSensitive sensitive = cerePlatformSensitiveService.findSensitive();
            for (OrderCommentParam param : commentParam.getParams()) {
                CereShopComment oldComment = cereShopCommentService.findComment(user.getBuyerUserId(), param.getOrderId(), param.getSkuId());
                if (oldComment != null) {
                    throw new CoBusinessException(CoReturnFormat.ALREADY_COMMENTED);
                }
                //查询店铺数据
                CereShopComment cereShopComment = cereShopCommentService.findShop(param.getProductId());
                if (cereShopComment != null) {
                    cereShopComment.setBuyerUserId(user.getBuyerUserId());
                    cereShopComment.setCreateTime(time);
                    cereShopComment.setUpdateTime(time);
                    cereShopComment.setComment(param.getComment());
                    cereShopComment.setImage(param.getImage());
                    cereShopComment.setState(IntegerEnum.NO.getCode());
                    cereShopComment.setIfSensitive(IntegerEnum.NO.getCode());
                    cereShopComment.setOrderId(param.getOrderId());
                    cereShopComment.setSkuId(param.getSkuId());
                    cereShopComment.setStar(param.getStar());
                    cereShopComment.setDes(param.getDes());
                    cereShopComment.setDelivery(param.getDelivery());
                    cereShopComment.setAttitude(param.getAttitude());
                    cereShopComment.setImpression(param.getImpression());
                    if (sensitive != null && IntegerEnum.YES.getCode().equals(sensitive.getState())) {
                        //校验是否存在敏感词
                        String[] split = sensitive.getSensitiveWord().split(",");
                        for (String word : split) {
                            if (param.getComment().contains(word)) {
                                //如果存在敏感词
                                if (IntegerEnum.SENSITIVE_STOP.getCode().equals(sensitive.getHandleMeasures())) {
                                    throw new CoBusinessException(CoReturnFormat.SENSITIVE_ERROR);
                                } else {
                                    //设置评论敏感词状态为需审核
                                    cereShopComment.setIfSensitive(IntegerEnum.YES.getCode());
                                    cereShopComment.setState(IntegerEnum.YES.getCode());
                                }
                            }
                        }
                    }
                    cereShopCommentService.insert(cereShopComment);
                    //查询是否匹配关键词
                    List<CerePlatformWord> words = cereShopCommentService.findWords();
                    if (!EmptyUtils.isEmpty(words)) {
                        List<CereCommentWord> collect = words.stream()
                                .map(word -> {
                                    if (cereShopComment.getComment().contains(word.getKeyWord())) {
                                        CereCommentWord cereCommentWord = new CereCommentWord();
                                        cereCommentWord.setCommentId(cereShopComment.getCommentId());
                                        cereCommentWord.setProductId(cereShopComment.getProductId());
                                        cereCommentWord.setKeyWord(word.getKeyWord());
                                        cereCommentWord.setWordId(word.getWordId());
                                        return cereCommentWord;
                                    }
                                    return null;
                                }).filter(Objects::nonNull).collect(Collectors.toList());
                        if (!EmptyUtils.isEmpty(collect)) {
                            //批量插入关键词关联评论数据
                            cereCommentWordService.insertBatch(collect);
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void addToComment(CommentSaveParam saveParam, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        if (!EmptyUtils.isEmpty(saveParam.getParams())) {
            //查询敏感词设置
            CerePlatformSensitive sensitive = cerePlatformSensitiveService.findSensitive();
            for (OrderCommentParam param : saveParam.getParams()) {
                //追加评论
                CereShopComment cereShopComment = new CereShopComment();
                cereShopComment.setCommentId(param.getCommentId());
                cereShopComment.setAddComment(param.getComment());
                cereShopComment.setAddImage(param.getImage());
                cereShopComment.setAddTime(time);
                cereShopComment.setUpdateTime(time);
                //查询敏感词设置
                if (sensitive != null && IntegerEnum.YES.getCode().equals(sensitive.getState())) {
                    //校验是否存在敏感词
                    String[] split = sensitive.getSensitiveWord().split(",");
                    for (String word : split) {
                        if (param.getComment().contains(word)) {
                            //如果存在敏感词
                            if (IntegerEnum.SENSITIVE_STOP.getCode().equals(sensitive.getHandleMeasures())) {
                                throw new CoBusinessException(CoReturnFormat.SENSITIVE_ERROR);
                            } else {
                                //设置评论敏感词状态为需审核
                                cereShopComment.setIfSensitive(IntegerEnum.YES.getCode());
                                cereShopComment.setState(IntegerEnum.YES.getCode());
                            }
                        }
                    }
                }
                cereShopCommentService.update(cereShopComment);
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(OrderGetByIdParam param, CereBuyerUser user) {
        //校验当前订单状态是否为已完成或者已取消
        CereShopOrder cereShopOrder = cereShopOrderDAO.checkStateFinishAndCancel(param.getOrderId());
        if (cereShopOrder == null) {
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //查询是否有售后数据
        List<CereOrderAfter> afters = cereShopOrderDAO.findAfterByOrderId(param.getOrderId());
        if (!EmptyUtils.isEmpty(afters)) {
            //删除售后商品属性数据
            cereShopOrderDAO.deleteAfterProductSkus(afters);
            //删除售后商品数据
            cereShopOrderDAO.deleteAfterProducts(afters);
        }
        //查询订单关联的所有消息id
        List<Long> noticeIds = cereNoticeService.findIdsByOrderId(param.getOrderId());
        if (!EmptyUtils.isEmpty(noticeIds)) {
            //删除消息数据
            cereNoticeService.deleteByIds(noticeIds);
            //删除客户关联消息数据
            cereNoticeService.deleteBuyerNotice(noticeIds, user.getBuyerUserId());
        }
        //删除订单商品属性数据
        cereShopOrderDAO.deleteOrderProductSkus(param.getOrderId());
        //删除订单关联所有数据
        cereShopOrderDAO.deleteAll(param.getOrderId());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void returnExpress(CereAfterDilever dilever, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        dilever.setCreateTime(time);
        cereAfterDileverService.insert(dilever);
        //修改售后单状态为退货中
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(dilever.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_RETURN_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterService.update(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "客户端操作", "填写退货物流信息", dilever.getAfterId(), time);
    }

    @Override
    public List<Dilevery> getDilevery(DileveryParam param) throws Exception {
        List<Dilevery> list = new ArrayList<>();
        //查询物流查询策略
        LogisticsTypeEnum logisticsTypeEnum = zkMallProperties.getLogisticsType();
        String code;
        if (LogisticsTypeEnum.KUAIDI100 == logisticsTypeEnum) {
            //查询快递公司编码
            code = cerePlatformDictService.findByCompany(param.getExpress(), LongEnum.EXPRESS_100.getCode());
            //通过快递100查询物流轨迹
            List<ShippingTrace> traces = expressService.findTraces(code, param.getDeliverFormid());
            if (!EmptyUtils.isEmpty(traces)) {
                list = traces.stream().map(a -> {
                    Dilevery dilevery = new Dilevery();
                    dilevery.setReason(a.getAcceptStation());
                    dilevery.setTime(a.getAcceptTime());
                    return dilevery;
                }).collect(Collectors.toList());
            }
        } else if (LogisticsTypeEnum.KUAIDINIAO == logisticsTypeEnum) {
            //查询快递公司编码
            code = cerePlatformDictService.findByCompany(param.getExpress(), LongEnum.EXPRESS_NIAO.getCode());
            //通过快递鸟查询物流轨迹
            List<ShippingTrace> traces = expressService.findTraces(code, param.getDeliverFormid());
            if (!EmptyUtils.isEmpty(traces)) {
                list = traces.stream().map(a -> {
                    Dilevery dilevery = new Dilevery();
                    dilevery.setReason(a.getAcceptStation());
                    dilevery.setTime(a.getAcceptTime());
                    return dilevery;
                }).collect(Collectors.toList());
            }
        }
        return list;
    }

    private void addPayLog(CereShopOrder order, String time, String orderNo, String transactionId) {
        CerePayLog payLog = new CerePayLog();
        payLog.setCreateTime(time);
        payLog.setOrderFormid(order.getOrderFormid());
        payLog.setOutTradeNo(orderNo);
        //设置退款单号
        if (orderNo.contains("XCX")) {
            //小程序支付
            payLog.setOutRefundNo("XCXTK" + TimeUtils.todayTime() + order.getOrderFormid() + order.getBuyerUserId());
        } else if (orderNo.contains("H5")) {
            //H5支付
            payLog.setOutRefundNo("H5TK" + TimeUtils.todayTime() + order.getOrderFormid() + order.getBuyerUserId());
        } else if (orderNo.contains("APPV3")) {
            //APPV3支付
            payLog.setOutRefundNo("APPV3TK" + TimeUtils.todayTime() + order.getOrderFormid() + order.getBuyerUserId());
        } else if (orderNo.contains("APP")) {
            //APP支付
            payLog.setOutRefundNo("APPTK" + TimeUtils.todayTime() + order.getOrderFormid() + order.getBuyerUserId());
        } else if (orderNo.contains("PC")) {
            //PC二维码支付
            payLog.setOutRefundNo("PCTK" + TimeUtils.todayTime() + order.getOrderFormid() + order.getBuyerUserId());
        }
        payLog.setTransactionId(transactionId);
        payLog.setPaymentMode(order.getPaymentMode());
        payLog.setShopId(order.getShopId());
        payLog.setState(StringEnum.PAY_LOG_PAY.getCode());
        payLog.setTotalFee(order.getPrice());
        payLog.setUserId(String.valueOf(order.getBuyerUserId()));
        String payTypeStr = PayUtil.getPayTypeStr(order.getPaymentMode());
        payLog.setRemark(order.getCustomerName() + "使用" + payTypeStr + "支付了" + order.getPrice() + "元,支付订单号为：" + order.getOrderFormid()
                + ",支付时间为" + time);
        cerePayLogService.insert(payLog);
    }

    private void addReconciliation(CereShopOrder order, String time) throws Exception {
        CereOrderReconciliation reconciliation = new CereOrderReconciliation();
        reconciliation.setCreateTime(time);
        reconciliation.setMoney(order.getPrice());
        reconciliation.setOrderId(order.getOrderId());
        reconciliation.setState(IntegerEnum.RELATIONSHIP_FROZEN.getCode());
        reconciliation.setType(IntegerEnum.RELATIONSHIP_INCOME.getCode());
        cereOrderReconciliationService.insert(reconciliation);
    }

    /**
     * 结算逻辑查询实时sku信息表
     */
    private void getSettlementInnerForRealInfo(Settlement settlement, SettlementParam param, CereBuyerUser user) {
        //定义是否提示
        AtomicBoolean flag = new AtomicBoolean(false);
        //封装商品明细数据
        if (!EmptyUtils.isEmpty(param.getShops())) {
            List<SettlementShop> invalidShops = new ArrayList<>();
            List<SettlementShop> shopSettlementList = new ArrayList<>();

            //参数中所有shopId
            List<Long> shopIdList = new ArrayList<>();
            //参数中所有skuid
            List<Long> skuIdList = new ArrayList<>();
            for (ShopProductParam shop : param.getShops()) {
                shopIdList.add(shop.getShopId());
                skuIdList.addAll(shop.getSkus().stream().map(ProductSku::getSkuId).toList());
            }
            Long memberLevelId = 0L;
            //存放会员价
            List<CartSku> realInfoList = cereSkuMemberRealInfoDAO.findSkuListBySkuIdList(skuIdList, memberLevelId);
            Map<Long, CartSku> cartSkuMap = realInfoList.stream().collect(Collectors.toMap(CartSku::getSkuId, Function.identity()));

            List<CerePlatformShop> shopList = cerePlatformShopDAO.selectList(Wrappers.<CerePlatformShop>lambdaQuery().in(CerePlatformShop::getShopId, shopIdList));
            Map<Long, CerePlatformShop> shopMap = shopList.stream().collect(Collectors.toMap(CerePlatformShop::getShopId, Function.identity()));

            for (ShopProductParam shop : param.getShops()) {

                CerePlatformShop cps = shopMap.get(shop.getShopId());

                //查询店铺信息
                SettlementShop settlementShop = new SettlementShop();
                settlementShop.setShopId(shop.getShopId());
                settlementShop.setShopName(cps.getShopName());
                settlementShop.setShopLogo(cps.getShopLogo());
                settlementShop.setShopAdress(cps.getShopAdress());

                List<CartSku> invalidSkus = new ArrayList<>();
                int invalidNumber = 0;
                BigDecimal total = BigDecimal.ZERO;
                //sku后面接的 优惠信息
                Map<Long, List<String>> skuDiscountInfoMap = new HashMap<>();
                settlementShop.setSkuDiscountInfoMap(skuDiscountInfoMap);

                Map<Long, CartSku> map = new HashMap<>();
                if (!EmptyUtils.isEmpty(shop.getSkus())) {
                    //将商品数量放到map中
                    Map<Long, Integer> numberMap = shop.getSkus().stream()
                            .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));

                    Map<Long, Integer> productIdBuyNumberMap = new HashMap<>();

                    if (!EmptyUtils.isEmpty(numberMap)) {
                        //查询规格信息数据
                        List<CartSku> skus = cereProductSkuService.findStockNumberBySkusForRealInfo(shop.getSkus());
                        if (!EmptyUtils.isEmpty(skus)) {
                            //设置商品数量
                            for (CartSku sku : skus) {
                                map.put(sku.getSkuId(), sku);
                                //设置购买数量
                                sku.setNumber(numberMap.get(sku.getSkuId()));
                                sku.setShopId(shop.getShopId());
                                Integer buyNum = productIdBuyNumberMap.getOrDefault(sku.getProductId(), 0);
                                productIdBuyNumberMap.put(sku.getProductId(), buyNum + sku.getNumber());
                            }

                            for (CartSku sku : skus) {

                                CartSku realInfo = cartSkuMap.get(sku.getSkuId());
                                if (realInfo == null) {
                                    throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT);
                                }
                                sku.setActivityType(realInfo.getActivityType());

                            }

                            for (CartSku sku : skus) {
                                //设置规格属性值数组
                                sku.setValues(EmptyUtils.getImages(sku.getValue()));

                                sku.setTotal(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                sku.setSelected(IntegerEnum.YES.getCode());
                                //校验库存
                                int stockNumber = map.get(sku.getSkuId()).getStockNumber();
                                if (sku.getNumber() > stockNumber) {
                                    //叠加商品件数
                                    invalidNumber += sku.getNumber();
                                    flag.set(true);
                                    invalidSkus.add(sku);
                                }
                            }
                            settlementShop.setNumber(skus.stream().mapToInt(CartSku::getNumber).sum());

                            List<CartSku> sortSkuList = new ArrayList<>();

                            //剩下的就是普通的商品
                            skus.removeAll(sortSkuList);
                            for (CartSku sku : skus) {
                                if (sku.isUseMember()) {
                                    CartSku cartSku = cartSkuMap.get(sku.getSkuId());
                                    total = total.add(cartSku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                } else {
                                    total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                }

                            }
                            sortSkuList.addAll(skus);
                            settlementShop.setSkus(sortSkuList);

                            if (flag.get()) {
                                SettlementShop settlementShop1 = new SettlementShop();
                                settlementShop1.setShopId(shop.getShopId());
                                settlementShop1.setNumber(invalidNumber);
                                settlementShop1.setSkus(invalidSkus);
                                invalidShops.add(settlementShop1);
                            } else {
                                //查询店铺物流方案
                                List<CereOrderLogistics> logistics = cereOrderLogisticsService.findLogistics(shop.getShopId());
                                Distribution distribution = setLogisticPrice(logistics, sortSkuList, settlement.getReceive(), numberMap);
                                if (distribution == null) {
                                    settlementShop.setReceiveNotMatch(true);
                                    distribution = new Distribution();
                                    distribution.setDistributionName("全国包邮");
                                    distribution.setDistributionPrice(BigDecimal.ZERO);
                                    distribution.setLogisticsId(-1L);
                                    settlementShop.setDistribution(distribution);
                                } else {
                                    settlementShop.setDistribution(distribution);
                                }
                            }
                        }
                    }
                }
                settlementShop.setTotal(total.setScale(2, RoundingMode.HALF_UP));

                shopSettlementList.add(settlementShop);
            }

            settlement.setShops(shopSettlementList);
            settlement.setInvalidShops(invalidShops);
            if (flag.get()) {
                //超出库存,提示‘存在无效商品，请重新选择’
                throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT, settlement);
            }
        }
    }

    /**
     * 结算具体逻辑
     */
    private void getSettlementInner(Settlement settlement, SettlementParam param, CereBuyerUser user) {
        //定义是否提示
        AtomicBoolean flag = new AtomicBoolean(false);
        //封装商品明细数据
        if (!EmptyUtils.isEmpty(param.getShops())) {
            List<SettlementShop> invalidShops = new ArrayList<>();
            List<SettlementShop> shopSettlementList = new ArrayList<>();

            //查找所有的活动相关信息
            //cerePlatformSeckillService.
            List<Long> shopIdList = new ArrayList<>();
            for (ShopProductParam shop : param.getShops()) {
                shopIdList.add(shop.getShopId());
            }

            for (ShopProductParam shop : param.getShops()) {
                //查询店铺信息
                SettlementShop settlementShop = cereShopOrderDAO.findSettlementShop(shop.getShopId());
                settlementShop.setShopId(shop.getShopId());
                List<CartSku> invalidSkus = new ArrayList<>();
                int invalidNumber = 0;
                BigDecimal total = BigDecimal.ZERO;
                //sku后面接的 优惠信息
                Map<Long, List<String>> skuDiscountInfoMap = new HashMap<>();
                settlementShop.setSkuDiscountInfoMap(skuDiscountInfoMap);

                Map<Long, CartSku> map = new HashMap<>();
                if (!EmptyUtils.isEmpty(shop.getSkus())) {
                    //查询当前店铺所有购买商品的库存数据
                    List<CartSku> productSkus = cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                    if (!EmptyUtils.isEmpty(productSkus)) {
                        for (CartSku sku : productSkus) {
                            map.put(sku.getSkuId(), sku);
                        }
                    }
                    //将商品数量放到map中
                    Map<Long, Integer> numberMap = shop.getSkus().stream()
                            .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));

                    Map<Long, Integer> productIdBuyNumberMap = new HashMap<>();

                    if (!EmptyUtils.isEmpty(numberMap)) {
                        //查询规格信息数据
                        List<CartSku> skus = cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                        if (!EmptyUtils.isEmpty(skus)) {
                            //设置商品数量
                            skus.forEach(sku -> {
                                sku.setNumber(numberMap.get(sku.getSkuId()));
                                sku.setShopId(shop.getShopId());
                                Integer buyNum = productIdBuyNumberMap.getOrDefault(sku.getProductId(), 0);
                                productIdBuyNumberMap.put(sku.getProductId(), buyNum + sku.getNumber());
                            });

                            for (CartSku sku : skus) {
                                //设置规格属性值数组
                                sku.setValues(EmptyUtils.getImages(sku.getValue()));

                                sku.setTotal(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                sku.setSelected(IntegerEnum.YES.getCode());
                                //校验库存
                                int stockNumber = map.get(sku.getSkuId()).getStockNumber();
                                if (sku.getNumber() > stockNumber) {
                                    //叠加商品件数
                                    invalidNumber += sku.getNumber();
                                    flag.set(true);
                                    invalidSkus.add(sku);
                                }
                            }
                            settlementShop.setNumber(skus.stream().mapToInt(CartSku::getNumber).sum());

                            //剩下的就是普通的商品
                            //skus.removeAll(sortSkuList);
                            for (CartSku sku : skus) {
                                total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                            }
                            List<CartSku> sortSkuList = new ArrayList<>(skus);
                            settlementShop.setSkus(sortSkuList);

                            if (flag.get()) {
                                SettlementShop settlementShop1 = new SettlementShop();
                                settlementShop1.setShopId(shop.getShopId());
                                settlementShop1.setNumber(invalidNumber);
                                settlementShop1.setSkus(invalidSkus);
                                invalidShops.add(settlementShop1);
                            } else {
                                //查询店铺物流方案
                                List<CereOrderLogistics> logistics = cereOrderLogisticsService.findLogistics(shop.getShopId());
                                Distribution distribution = setLogisticPrice(logistics, sortSkuList, settlement.getReceive(), numberMap);
                                if (distribution == null) {
                                    settlementShop.setReceiveNotMatch(true);
                                    distribution = new Distribution();
                                    distribution.setDistributionName("全国包邮");
                                    distribution.setDistributionPrice(BigDecimal.ZERO);
                                    distribution.setLogisticsId(-1L);
                                    settlementShop.setDistribution(distribution);
                                } else {
                                    settlementShop.setDistribution(distribution);
                                }
                            }
                        }
                    }
                }
                settlementShop.setTotal(total.setScale(2, RoundingMode.HALF_UP));
                shopSettlementList.add(settlementShop);
            }

            settlement.setShops(shopSettlementList);
            settlement.setInvalidShops(invalidShops);
            if (flag.get()) {
                //超出库存,提示‘存在无效商品，请重新选择’
                throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT, settlement);
            }
        }
    }

    @Override
    public Distribution setLogisticPrice(List<CereOrderLogistics> logistics, List<CartSku> skus, CereBuyerReceive receive,
                                         Map<Long, Integer> buyNumberMap) {
        if (receive == null) {
            return null;
        }
        Distribution tribution = new Distribution();
        tribution.setDistributionPrice(BigDecimal.ZERO);
        //过滤不需要物流的商品
        List<CartSku> cartSkus = skus.stream().filter(sku -> IntegerEnum.YES.getCode().equals(sku.getIfLogistics()))
                .collect(Collectors.toList());
        if (EmptyUtils.isEmpty(cartSkus)) {
            tribution.setDistributionName("全国包邮");
            return tribution;
        }
        return DistributionUtil.getDistribution(logistics, receive, buyNumberMap, cartSkus);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void pay(PayParam param) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<CereShopOrder> orderList;
        if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
            //父订单支付,查询所有子订单数据
            orderList = cereShopOrderDAO.findByParentId(param.getOrderId());
        } else {
            //子订单支付
            orderList = cereShopOrderDAO.findByOrderId(param.getOrderId());
        }
        //支付业务处理
        BigDecimal total = BigDecimal.ZERO;
        if (!EmptyUtils.isEmpty(orderList)) {
            for (CereShopOrder order : orderList) {
                total = total.add(order.getPrice());
                //正常下单业务处理
                handleOrder(order, RandomStringUtil.getRandomCode(15, 0),
                        order.getOrderFormid() + "-" + RandomStringUtil.getRandomCode(4, 0) + "XCX", time);
            }
        }
        CerePlatformDict cereplatformDict = cerePlatformDictService.getByName("auto_pay");
        if (cereplatformDict != null && Objects.equals(cereplatformDict.getDictDescribe(), "true")) {
            //增加成长值
            cereBuyerUserService.updateGrowth(orderList.get(0).getBuyerUserId(), total.intValue());
        }
    }

    @Override
    public PayUrl getUrl(OrderGetByIdParam param, CereBuyerUser user, String ip) throws Exception {
        if (param.getType() == null) {
            param.setType(2);
        }
        PayUrl payUrl = new PayUrl();
        //根据订单id查询支付金额数据
        String orderFormId = null;
        BigDecimal price = null;
        CereOrderParent orderParent = param.getType() == 1 ? cereOrderParentDAO.findById(param.getOrderId()) : null;
        if (orderParent != null) {
            orderFormId = orderParent.getParentFormid();
            price = orderParent.getPrice();
        } else {
            CereShopOrder shopOrder = cereShopOrderDAO.findById(param.getOrderId());
            if (shopOrder != null) {
                orderFormId = shopOrder.getOrderFormid();
                price = shopOrder.getPrice();
            }
        }
        if (orderFormId != null && price != null) {
            payUrl.setOrderId(param.getOrderId());
            payUrl.setMoney(price);
            //生成收款码
            String formid = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "XCX";

            PayService payService = PayFactory.getPayService(param.getPaymentMode());

            String code_url;
            if (IntegerEnum.ORDER_PAY_WX.getCode().equals(param.getPaymentMode())) {
                code_url = wxPayService.getOrderCollectionCode(formid, price,
                        ip, WxPayEnum.PAY_TYPE_NATIVE.getCode());
            } else {
                code_url = payService.getOrderCollectionCode(orderFormId, price, ip, null);
            }

            if (!EmptyUtils.isEmpty(code_url)) {
                //生成收款二维码图片
                Map<EncodeHintType, Object> hints = new HashMap<>();
                // 设置纠错等级
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                // 编码类型
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, 400, 400, hints);
                MatrixToImageConfig config = new MatrixToImageConfig();
                BufferedImage image = toBufferedImage(bitMatrix, config);
                //上传图片到OSS
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image, "png", out);
                byte[] bytes = out.toByteArray();
                String url = fileStrategy.upload(formid + ".png", bytes);
                payUrl.setUrl(url);
            }
        }
        return payUrl;
    }

    @Override
    public PayUrl checkPay(PayParam param) {
        PayUrl payUrl = new PayUrl();
        List<CereShopOrder> list;
        //查询订单是否支付成功
        if (ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())) {
            //父订单校验
            list = cereShopOrderDAO.checkPayParent(param.getOrderId());
        } else {
            //子订单校验
            list = cereShopOrderDAO.checkPayOrder(param.getOrderId());
        }
        if (!EmptyUtils.isEmpty(list)) {
            payUrl.setCode(StringEnum.WX_PAY_SUCCESS.getCode());
        }
        return payUrl;
    }

    @Override
    public List<CartSku> refund(OrderGetByIdParam param, CereBuyerUser user) {
        List<CartSku> skus;
        //查询当前订单已经提交过的售后商品规格id数组
        List<Long> ids = cereShopOrderDAO.findAfterSkuIdsByOrderId(param.getOrderId());
        if (EmptyUtils.isEmpty(ids)) {
            //未申请过售后或者售后失败等,查询订单所有商品列表返回
            skus = cereProductSkuService.findSkuByOrderId(param.getOrderId());
        } else {
            //有售后商品,查询当前订单除了这些商品以外的其他商品
            skus = cereProductSkuService.findSkuByIds(ids, param.getOrderId());
        }
        return skus;
    }

    @Override
    public Long getWaitPayNumByHours(Integer hours, Long buyerUserId) {
        Date time = DateUtil.offsetHour(new Date(), -hours);
        LambdaQueryWrapper<CereShopOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereShopOrder::getBuyerUserId, buyerUserId)
                .eq(CereShopOrder::getPaymentState, IntegerEnum.NO.getCode())
                .ge(CereShopOrder::getCreateTime, DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
        return cereShopOrderDAO.selectCount(wrapper);
    }

    @Override
    public Long getOrderNumByHours(Integer hours, Long buyerUserId) {
        Date time = DateUtil.offsetHour(new Date(), -hours);
        LambdaQueryWrapper<CereShopOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereShopOrder::getBuyerUserId, buyerUserId)
                .ge(CereShopOrder::getCreateTime, DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
        return cereShopOrderDAO.selectCount(wrapper);
    }

}
