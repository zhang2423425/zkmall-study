/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.after.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.shop.zkmall.business.dao.after.CereAfterProductDAO;
import com.shop.zkmall.business.dao.after.CereOrderAfterDAO;
import com.shop.zkmall.business.page.after.After;
import com.shop.zkmall.business.page.after.AfterHistory;
import com.shop.zkmall.business.page.after.Refund;
import com.shop.zkmall.business.page.order.Product;
import com.shop.zkmall.business.page.order.ShopOrder;
import com.shop.zkmall.business.page.pay.PayLog;
import com.shop.zkmall.business.param.after.AfterGetAllParam;
import com.shop.zkmall.business.param.after.AfterIdParam;
import com.shop.zkmall.business.pay.PayFactory;
import com.shop.zkmall.business.pay.PayService;
import com.shop.zkmall.business.service.after.CereOrderAfterService;
import com.shop.zkmall.business.service.buyer.CereBuyerUserService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.order.CereOrderReconciliationService;
import com.shop.zkmall.business.service.order.CereShopOrderService;
import com.shop.zkmall.business.service.pay.CerePayLogService;
import com.shop.zkmall.business.service.product.CereProductSkuService;
import com.shop.zkmall.business.service.redis.CereRedisKeyService;
import com.shop.zkmall.business.utils.EncodeUtil;
import com.shop.zkmall.business.utils.RedisUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.domain.order.CereOrderReconciliation;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.SpringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderAfterServiceImpl implements CereOrderAfterService {

    private final CereOrderAfterDAO cereOrderAfterDAO;

    private final CereShopOrderService cereShopOrderService;

    private final CerePlatformLogService cerePlatformLogService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final CereRedisKeyService cereRedisKeyService;

    private final CereProductSkuService cereProductSkuService;

    private final CerePayLogService cerePayLogService;

    private final CereOrderReconciliationService cereOrderReconciliationService;

    private final CereBuyerUserService cereBuyerUserService;

    private final CereAfterProductDAO cereAfterProductDAO;

    private final EncodeUtil encodeUtil;

    private final RedisUtil redisUtil;

    @Override
    public Page<After> getAll(AfterGetAllParam param) {
        //设置售后状态转换查询
        if (param.getAfterState() != null) {
            List<Integer> afterStateList = Lists.newArrayList();
            if (param.getAfterState() == 1) {
                afterStateList = Lists.newArrayList(1, 2, 3, 5, 6, 7, 8, 10);

            } else if (param.getAfterState() == 2) {
                afterStateList = Lists.newArrayList(4);

            } else if (param.getAfterState() == 3) {
                afterStateList = Lists.newArrayList(9);
            }
            param.setAfterStateList(afterStateList);
            param.setAfterState(null);
        }
        //分页查询
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<After> list = cereOrderAfterDAO.getAll(param);
        List<Long> afterIdList = list.stream().map(After::getAfterId).collect(Collectors.toList());
        List<Long> orderIdList = list.stream().map(After::getOrderId).distinct().collect(Collectors.toList());
        Map<Long, List<CereAfterProduct>> apMap = new HashMap<>();
        if (!afterIdList.isEmpty()) {
            LambdaQueryWrapper<CereAfterProduct> wrapper = Wrappers.<CereAfterProduct>lambdaQuery()
                    .in(CereAfterProduct::getAfterId, afterIdList);
            List<CereAfterProduct> apList = cereAfterProductDAO.selectList(wrapper);
            apMap = apList.stream().collect(Collectors.groupingBy(CereAfterProduct::getAfterId));
        }
        for (After after : list) {
            after.setCustomerName(encodeUtil.encodeInfo(after.getCustomerName()));
            List<CereAfterProduct> apList = apMap.get(after.getAfterId());
            if (apList != null) {
                after.setNumber(apList.size());
                after.setAfterProductNames(apList.stream().map(CereAfterProduct::getProductName).collect(Collectors.joining(";")));
            }
        }

        //关联运单查询相关信息[没有条件关联查询的时候，需要额外查询运单信息]
        if (!param.getNeedJoinShopOrder()) {
            Map<Long, CereShopOrder> orderMap = new HashMap<>();
            if (!orderIdList.isEmpty()) {
                orderMap = cereShopOrderService.findByIdList(orderIdList).stream().collect(Collectors.toMap(CereShopOrder::getOrderId, Function.identity()));
            }
            if (!orderMap.isEmpty()) {
                for (After after : list) {
                    CereShopOrder shopOrder = orderMap.get(after.getOrderId());
                    if (shopOrder != null) {
                        after.setOrderFormid(shopOrder.getOrderFormid());
                        //after.setPaymentTime();
                        after.setOrderState(shopOrder.getState());
                        after.setCustomerName(shopOrder.getCustomerName());
                    }
                }
            }
        }
        PageInfo<After> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public After getById(Long afterId) {
        After after = cereOrderAfterDAO.getById(afterId);
        if (after != null) {
            if (!EmptyUtils.isEmpty(after.getImage())) {
                after.setImages(EmptyUtils.getImages(after.getImage()));
            }
            //查询商品信息
            List<Product> products = cereOrderAfterDAO.findProducts(afterId);
            if (!EmptyUtils.isEmpty(products)) {
                //封装规格属性数据
                products.forEach(product -> product.setSkuDetails(cereOrderAfterDAO.findSkuAttribute(product.getAfterProductId())));
            }
            //查询协商历史
            List<AfterHistory> histories = cereOrderAfterDAO.findHistories(afterId);
            if (!EmptyUtils.isEmpty(histories)) {
                histories.forEach(afterHistory -> {
                    //封装图片数组
                    afterHistory.setImages(EmptyUtils.getImages(afterHistory.getImage()));
                    if (afterHistory.getTitle().contains("买家发起了")) {
                        afterHistory.setReason(after.getExplain());
                    } else if (!afterHistory.getTitle().contains("申请平台介入")) {
                        afterHistory.setReason(after.getReason());
                    }
                    afterHistory.setName(encodeUtil.encodeInfo(afterHistory.getName()));
                });
            }
            after.setAfterHistory(histories);
            after.setProducts(products);
            after.setCustomerName(encodeUtil.encodeInfo(after.getCustomerName()));
        }
        return after;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundSuccess(AfterIdParam param, CerePlatformBusiness user) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //同意退款申请,修改售后单状态为退款中
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //根据售后单id查询订单支付信息和订单总金额
        Refund refund = cereOrderAfterDAO.findOrderPay(param.getAfterId());
        //调用退款接口
        if (refund != null) {
            if (refund.getPrice().compareTo(refund.getOrderPrice()) > 0) {
                throw new CoBusinessException(CoReturnFormat.PARAM_INVALID, "退款金额不能超过支付金额总额!");
            }
            //更新支付流水after字段
            CerePayLog cerePayLog = new CerePayLog();
            cerePayLog.setId(refund.getId());
            if (!EmptyUtils.isEmpty(refund.getAfter())) {
                //不是第一次退款,重新设置退款单号
                if (refund.getOutTradeNo().contains("XCX")) {
                    //小程序支付
                    refund.setOutRefundNo("XCXTK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("H5")) {
                    //H5支付
                    refund.setOutRefundNo("H5TK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("APPV3")) {
                    //APPV3支付
                    refund.setOutRefundNo("APPV3TK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("APP")) {
                    //APP支付
                    refund.setOutRefundNo("APPTK" + TimeUtils.todayTime() + refund.getOrderFormid());
                }
                cerePayLog.setAfter(refund.getAfter() + "," + param.getAfterId());
            } else {
                cerePayLog.setAfter(String.valueOf(param.getAfterId()));
            }
            cerePayLogService.update(cerePayLog);
            CereShopOrder order = cereShopOrderService.findById(param.getOrderId());
//            handleWxLogTest(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo(),com.shop.zkmall.admin.param.getAfterId());
            PayService payService = PayFactory.getPayService(order.getPaymentMode());
            Map<String, String> resultMap = payService.refund(refund.getOutTradeNo(), refund.getTransactionId(), refund.getOutRefundNo(), refund.getOrderPrice(), refund.getPrice(), param.getAfterId());
            if (!EmptyUtils.isEmpty(resultMap)) {
                if (resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode()) &&
                        resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())) {
                    //退款成功 支付宝的退款是立即退款的，需要同步处理
                    if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                            IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                        //内部调用不会执行事务，所以通过springUtil获取service对象
                        CereOrderAfterService afterService = SpringUtil.getBean(CereOrderAfterService.class);
                        afterService.handleRefundSuc(refund.getOutRefundNo(), refund.getTransactionId(), refund.getOutTradeNo(), order.getPaymentMode());
                    }
                } else if (resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())) {
                    //退款失败,商户余额不足
                    throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                }
            }
        }
        //删除自动确认收货延时任务
//        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId());
//        cereRedisKeyService.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId());
//        //清空自动关闭售后延时任务和任务记录
//        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());
//        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());

        /*redisUtil.removeFromQueue(com.shop.zkmall.admin.param.getOrderId(), StringEnum.ORDER_CONFIRM_DILEVERY.getCode());
        redisUtil.removeFromQueue(com.shop.zkmall.admin.param.getAfterId(), StringEnum.AFTER_CANCEL.getCode());*/

        redisUtil.removeAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId());
        redisUtil.removeAppDelayQueue(StringEnum.AFTER_CANCEL.getCode(), param.getAfterId());

        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "仅退款同意退款申请", param.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundRefuse(AfterIdParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //拒绝退款申请,修改售后单状态为审核不通过
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_NO.getCode());
        cereOrderAfter.setReason(param.getReason());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "仅退款拒绝退款申请", param.getAfterId(), time);
        //重新开启自动确认收货延时任务

        String end = cereRedisKeyService.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + param.getOrderId());
        if (!EmptyUtils.isEmpty(end)) {
            //stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(), 1, TimeUtils.getCountDownByTime(time,end));
            redisUtil.addAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), end);
        }

        //清空自动关闭售后延时任务和任务记录
//        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());
//        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());

        redisUtil.removeAppDelayQueue(StringEnum.AFTER_CANCEL.getCode(), param.getAfterId());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void success(AfterIdParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //同意退货退款申请,修改售后单状态为审核通过
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_YES.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "退货退款同意申请", param.getAfterId(), time);
        //删除自动确认收货延时任务
//        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId());
//        cereRedisKeyService.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId());
//        //清空自动关闭售后延时任务和任务记录
//        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());
//        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());

        redisUtil.removeAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId());
        redisUtil.removeAppDelayQueue(StringEnum.AFTER_CANCEL.getCode(), param.getAfterId());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void refuse(AfterIdParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //拒绝退货退款申请,修改售后单状态为审核不通过
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_NO.getCode());
        cereOrderAfter.setReason(param.getReason());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "退货退款拒绝申请,拒绝原因：" +
                param.getReason(), param.getAfterId(), time);
        //重新开启自动确认收货延时任务
        String end = cereRedisKeyService.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + param.getOrderId());
        if (!EmptyUtils.isEmpty(end)) {
            //stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(), 1, TimeUtils.getCountDownByTime(time,end));
            //redisUtil.addAndUpdateDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), com.shop.zkmall.admin.param.getOrderId(), end);
            redisUtil.addAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), end);
        }
        //清空自动关闭售后延时任务和任务记录
//        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());
//        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+com.shop.zkmall.admin.param.getAfterId());
        //redisUtil.removeFromQueue(com.shop.zkmall.admin.param.getAfterId(), StringEnum.AFTER_CANCEL.getCode());
        redisUtil.removeAppDelayQueue(StringEnum.AFTER_CANCEL.getCode(), param.getAfterId());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void confirmAndRefund(AfterIdParam param, CerePlatformBusiness user) throws Exception {
        //资金将退回买家的支付账户，售后单状态变为售后成功，买家端变为已退款
        String time = TimeUtils.yyMMddHHmmss();
        //确认收货且退款,修改售后单状态为退款中
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //根据售后单id查询订单支付信息和订单总金额
        Refund refund = cereOrderAfterDAO.findOrderPay(param.getAfterId());
        //调用退款接口
        if (refund != null) {
            //测试使用=退款
//            handleWxLogTest(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo(),com.shop.zkmall.admin.param.getAfterId());
            //更新支付流水售后单id字段
            CerePayLog payLog = new CerePayLog();
            payLog.setId(refund.getId());
            if (!EmptyUtils.isEmpty(refund.getAfter())) {
                //不是第一次退款,重新设置退款单号
                if (refund.getOutTradeNo().contains("XCX")) {
                    //小程序支付
                    refund.setOutRefundNo("XCXTK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("H5")) {
                    //H5支付
                    refund.setOutRefundNo("H5TK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("APPV3")) {
                    //APPV3支付
                    refund.setOutRefundNo("APPV3TK" + TimeUtils.todayTime() + refund.getOrderFormid());
                } else if (refund.getOutTradeNo().contains("APP")) {
                    //APP支付
                    refund.setOutRefundNo("APPTK" + TimeUtils.todayTime() + refund.getOrderFormid());
                }
                //拼接售后单id
                payLog.setAfter(refund.getAfter() + "," + param.getAfterId());
            } else {
                //第一次售后退款
                payLog.setAfter(String.valueOf(param.getAfterId()));
            }
            cerePayLogService.update(payLog);
            CereShopOrder order = cereShopOrderService.findById(param.getOrderId());
            PayService payService = PayFactory.getPayService(order.getPaymentMode());
            Map<String, String> resultMap = payService.refund(refund.getOutTradeNo(), refund.getTransactionId(), refund.getOutRefundNo(), refund.getOrderPrice(), refund.getPrice(), param.getAfterId());
            if (!EmptyUtils.isEmpty(resultMap)) {
                if (resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode()) &&
                        resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())) {
                    //退款成功 支付宝的退款是立即退款的，需要同步处理
                    if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                            IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                        //内部调用不会执行事务，所以通过springUtil获取service对象
                        CereOrderAfterService afterService = SpringUtil.getBean(CereOrderAfterService.class);
                        afterService.handleRefundSuc(refund.getOutRefundNo(), refund.getTransactionId(), refund.getOutTradeNo(), order.getPaymentMode());
                    }
                } else if (resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())) {
                    //退款失败,商户余额不足
                    throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                }
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "退货退款确认收货且退款", param.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void damaging(AfterIdParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //货物有损拒绝退款,修改售后单状态改为退货完成,拒绝退款
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_RETURN_SUCCESS_NOT_REFUND.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        if (EmptyUtils.isEmpty(param.getReason())) {
            param.setReason("无");
        }
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "退货退款货物有损拒绝退款,拒绝原因：" +
                param.getReason(), param.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleRefundSuc(String refundNo, String transactionId, String orderNo, Integer paymentMode) {
        String time = TimeUtils.yyMMddHHmmss();
        //根据退款单号查询支付流水
        PayLog payLog = cerePayLogService.findByOutRefundNo(refundNo);
        if (payLog != null) {
            Long afterId = null;
            //截取最新售后单id
            if (!EmptyUtils.isEmpty(payLog.getAfter())) {
                if (payLog.getAfter().contains(",")) {
                    String[] split = payLog.getAfter().split(",");
                    afterId = Long.parseLong(split[split.length - 1]);
                } else {
                    afterId = Long.parseLong(payLog.getAfter());
                }
            }
            //查询售后单数据
            CereOrderAfter cereOrderAfter = cereOrderAfterDAO.findById(afterId);
            if (cereOrderAfter != null) {
                //修改售后单状态为退款成功
                cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_SUCCESS.getCode());
                cereOrderAfter.setAfterId(afterId);
                cereOrderAfter.setUpdateTime(time);
                cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
                //查询订单是否有其他未退款商品(退款成功的商品才算退款商品)
                List<CereOrderProduct> list = cereOrderAfterDAO.findOtherProductsByAfterId(payLog.getOrderId(), afterId);
                CereShopOrder cereShopOrder = new CereShopOrder();
                cereShopOrder.setOrderId(payLog.getOrderId());
                cereShopOrder.setUpdateTime(time);
                if (EmptyUtils.isEmpty(list)) {
                    //如果没有其他未退款商品,订单整体商品售后成功，订单状态流转为已取消
                    cereShopOrder.setState(IntegerEnum.ORDER_STOP.getCode());
                }
                cereShopOrderService.updateState(cereShopOrder);
                //查询售后单商品数据,更新对应商品库存
                List<CereProductSku> skus = cereOrderAfterDAO.findAfterSkus(afterId);
                if (!EmptyUtils.isEmpty(skus)) {
                    skus.forEach(sku -> {
                        int stockNumber = cereProductSkuService.findStockNumber(sku.getSkuId());
                        if (!EmptyUtils.isEmpty(redisTemplate.opsForValue().get(String.valueOf(sku.getSkuId())))) {
                            stockNumber = (int) redisTemplate.opsForValue().get(String.valueOf(sku.getSkuId()));
                        }
                        //更新redis商品库存
                        redisTemplate.opsForValue().set(String.valueOf(sku.getSkuId()), stockNumber + sku.getStockNumber());
                    });
                    cereShopOrderService.updateBatchStock(skus);
                }
                //新增对账单数据
                CereOrderReconciliation reconciliation = new CereOrderReconciliation();
                reconciliation.setOrderId(cereOrderAfter.getOrderId());
                reconciliation.setMoney(cereOrderAfter.getPrice());
                reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
                reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
                reconciliation.setCreateTime(time);
                cereOrderReconciliationService.insert(reconciliation);
                //新增退款流水
                CerePayLog cerePayLog = new CerePayLog();
                cerePayLog.setCreateTime(time);
                cerePayLog.setOrderFormid(orderNo);
                cerePayLog.setOutTradeNo(orderNo);
                cerePayLog.setOutRefundNo(refundNo);
                cerePayLog.setTransactionId(transactionId);
                cerePayLog.setRefundFee(cereOrderAfter.getPrice());
                cerePayLog.setPaymentMode(paymentMode);
                cerePayLog.setShopId(payLog.getShopId());
                cerePayLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
                cerePayLog.setTotalFee(payLog.getTotalFee());
                cerePayLog.setUserId(payLog.getUserId());
                cerePayLog.setRemark(payLog.getOrderFormid() + "订单退款" + cereOrderAfter.getPrice() + "元");
                cerePayLogService.insert(cerePayLog);
                //扣减成长值
                ShopOrder shopOrder = cereShopOrderService.getById(payLog.getShopId(), payLog.getOrderId());
                if (shopOrder != null) {
                    cereBuyerUserService.updateGrowth(shopOrder.getBuyerUserId(), payLog.getTotalFee().setScale(0, RoundingMode.UP).negate().intValue());
                }
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundError(String outRefundNo) {
        String time = TimeUtils.yyMMddHHmmss();
        //根据订单id查询支付流水
        PayLog payLog = cerePayLogService.findByOutRefundNo(outRefundNo);
        if (payLog != null) {
            String afterId = "";
            //截取最新售后单id
            if (payLog.getAfter().contains(",")) {
                String[] split = payLog.getAfter().split(",");
                afterId = split[split.length - 1];
            } else {
                afterId = payLog.getAfter();
            }
            //修改售后单状态为退款失败
            CereOrderAfter cereOrderAfter = new CereOrderAfter();
            cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_ERRPR.getCode());
            cereOrderAfter.setAfterId(Long.parseLong(afterId));
            cereOrderAfter.setUpdateTime(time);
            cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        }
    }

    @Override
    public List<Integer> selectAfterStateList(Long orderId) {
        LambdaQueryWrapper<CereOrderAfter> wrapper = Wrappers.<CereOrderAfter>lambdaQuery()
                .eq(CereOrderAfter::getOrderId, orderId);
        List<CereOrderAfter> orderAfterList = cereOrderAfterDAO.selectList(wrapper);
        return orderAfterList.stream().map(CereOrderAfter::getAfterState).collect(Collectors.toList());
    }

}
