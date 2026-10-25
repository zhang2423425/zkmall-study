/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.zkmall.app.dao.after.CereOrderAfterDAO;
import com.shop.zkmall.app.page.after.AfterDetail;
import com.shop.zkmall.app.page.after.AfterHistory;
import com.shop.zkmall.app.page.after.Afters;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.param.after.AfterGetAllParam;
import com.shop.zkmall.app.param.after.AfterGetByIdParam;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.app.param.after.PlatformParam;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.app.service.after.*;
import com.shop.zkmall.app.service.log.CerePlatformLogService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.utils.RedisUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.after.CerePlatformAfter;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.commons.utils.SpringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderAfterServiceImpl implements CereOrderAfterService {

    private final CereOrderAfterDAO cereOrderAfterDAO;

    private final CereAfterProductService cereAfterProductService;

    private final CereAfterProductAttributeService cereAfterProductAttributeService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CerePlatformAfterService cerePlatformAfterService;

    private final CereAfterDileverService cereAfterDileverService;

    private final CereRedisKeyService cereRedisKeyService;

    private final RedisUtil redisUtil;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void submit(AfterParam param, CereBuyerUser user) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单信息
        CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
        CereShopOrder cereShopOrder = cereShopOrderService.findById(param.getOrderId());
        if (cereShopOrder != null) {
            if (param.getPrice() != null &&
                    param.getPrice().compareTo(cereShopOrder.getPrice()) > 0) {
                throw new CoBusinessException("退款金额不能超过实际支付金额!");
            }
            //设置协商历史描述
            String describe = "";
            if (IntegerEnum.AFTER_REFUND.getCode().equals(param.getAfterType())) {
                describe = "买家发起了仅退款申请,退款商品:";
            } else {
                describe = "买家发起了退货退款申请,退款商品:";
            }
            //将商品数量放到map中
            Map<Long, Integer> numberMap = param.getSkus().stream()
                    .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));
            //新增售后单数据
            CereOrderAfter cereOrderAfter = new CereOrderAfter();
            BeanUtils.copyProperties(param, cereOrderAfter);
            cereOrderAfter.setAfterFormid("SH" + RandomStringUtil.getRandomCode(15, 0));
            cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY.getCode());
            cereOrderAfter.setShopId(cereShopOrder.getShopId());
            cereOrderAfter.setCreateTime(time);
            cereOrderAfter.setUpdateTime(time);

            //查询该订单是否有售后中的商品
            List<CereAfterProduct> afterProductList = cereAfterProductService.findByOrderIdForCheck(param.getOrderId());
            List<Long> oldSkuIdList = afterProductList.stream().map(CereAfterProduct::getSkuId).collect(Collectors.toList());
            for (Long skuId : numberMap.keySet()) {
                if (oldSkuIdList.contains(skuId)) {
                    throw new CoBusinessException("商品售后中或售后结束，请勿重复申请");
                }
            }

            //查询规格信息数据
            List<CereAfterProduct> skus = cereAfterProductService.findSkuBySkus(param);
            //计算售后金额
            cereOrderAfter.setPrice(param.getPrice());
            cereOrderAfter.setReturnReason(param.getReturnReason());
            cereOrderAfterDAO.insert(cereOrderAfter);
            if (!EmptyUtils.isEmpty(numberMap)) {
                //插入售后商品明细数据
                if (!EmptyUtils.isEmpty(skus)) {
                    List<CereAfterProductAttribute> attributes = new ArrayList<>();
                    for (CereAfterProduct afterProduct : skus) {
                        describe += afterProduct.getProductName() + ",";
                        afterProduct.setAfterId(cereOrderAfter.getAfterId());
                        afterProduct.setNumber(numberMap.get(afterProduct.getSkuId()));
                        cereAfterProductService.insert(afterProduct);
                        //查询规格值属性数据
                        List<CereAfterProductAttribute> list = cereAfterProductService.findValuesBySkuId(afterProduct.getSkuId());
                        if (!EmptyUtils.isEmpty(list)) {
                            list.forEach(a -> {
                                a.setAfterProductId(afterProduct.getAfterProductId());
                                attributes.add(a);
                            });
                        }
                    }
                    //插入规格值属性数据
                    cereAfterProductAttributeService.insertBatch(attributes);
                }
            }
            if (EmptyUtils.isEmpty(param.getReturnReason())) {
                param.setReturnReason("无");
            }
            if (IntegerEnum.YES.getCode().equals(param.getGoodsState())) {
                describe += "货物状态：收到货,原因：" + param.getReturnReason() + ",金额：" + param.getPrice() + "元";
            } else {
                describe += "货物状态：未收到货,原因：" + param.getReturnReason() + ",金额：" + param.getPrice() + "元";
            }
            /*if(!EmptyUtils.isEmpty(stringRedisService.get(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId()))){
                //暂停自动确认收货定时任务
                stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId());
            }*/
            redisUtil.removeFromQueue(param.getOrderId(), StringEnum.ORDER_CONFIRM_DILEVERY.getCode());


            //同步更新订单售后单状态为售后中
            /*cereShopOrder.setUpdateTime(time);
            cereShopOrder.setOrderId(com.shop.zkmall.admin.param.getOrderId());
            cereShopOrder.setAfterState(IntegerEnum.AFTER_STAY.getCode());
            cereShopOrderService.update(cereShopOrder);*/

            //新增延时任务,如果3天后未处理自动关闭售后单
            /*stringRedisService.set(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId(),1,
                    TimeUtils.getCountDownByTime(time,TimeUtils.getMoreDayAfter(time,3)));
            //新增延时任务记录
            cereRedisKeyService.add(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId(),TimeUtils.getMoreDayAfter(time,3));*/

            redisUtil.addDelayQueue(StringEnum.AFTER_CANCEL.getCode(), cereOrderAfter.getAfterId(), TimeUtils.getMoreDayAfter(time, 3));

            //新增日志
            cerePlatformLogService.addLog(user, "售后管理", "客户端操作",
                    describe, cereOrderAfter.getAfterId(), time);
        }
    }

    @Override
    public BigDecimal getReturnPrice(AfterParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单信息
        CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
        CereShopOrder cereShopOrder = cereShopOrderService.findById(param.getOrderId());
        if (cereShopOrder == null) {
            throw new CoBusinessException(CoReturnFormat.AFTER_NOT_RETURN);
        }
        //改价过的商品，这两个价格可能不一样，页面不允许拆开售后
        if (!cereShopOrder.getOrderPrice().equals(cereShopOrder.getPrice())) {
            return cereShopOrder.getPrice();
        }
        //查询规格信息数据
        List<CereAfterProduct> skus = cereAfterProductService.findSkuBySkus(param);
        //计算售后金额
        if (param.getIsAllSelect().equals(IntegerEnum.NO.getCode())) {
            //拆单
            //退款价格
            BigDecimal total = BigDecimal.ZERO;
            //将商品数量放到map中
            Map<Long, Integer> numberMap = param.getSkus().stream()
                    .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));
            //获取商品叠加价格
            for (CereAfterProduct sku : skus) {
                //计算商品占比
                BigDecimal skuTotal = sku.getProductPrice().multiply(new BigDecimal(numberMap.get(sku.getSkuId())));
                total = total.add(skuTotal);
            }
            //退款订单占比
            BigDecimal refundedOrdersDivide = total.divide(cereShopOrder.getOrderPrice(), 4, RoundingMode.DOWN);
            if (!EmptyUtils.isEmptyBigdecimal(cereShopOrder.getDiscountPrice())) {
                //有优惠情况
                BigDecimal refundedDiscountPrice = cereShopOrder.getDiscountPrice().multiply(refundedOrdersDivide).setScale(2, RoundingMode.UP);
                total = total.subtract(refundedDiscountPrice);
            }
            //实际退款金额=订单总金额-优惠
//                if(!EmptyUtils.isEmptyBigdecimal(cereShopOrder.getLogisticsPrice())){
//                    //有运费情况
//                    if(com.shop.zkmall.admin.param.getAfterType()==IntegerEnum.YES.getCode()) {
//                        BigDecimal refundedLogisticsPrice = cereShopOrder.getLogisticsPrice().multiply(refundedOrdersDivide).setScale(2, RoundingMode.UP);
//                        total = total.add(refundedLogisticsPrice);
//                    }
//                }
            return total;
        } else {
            //不拆单
            //判断是否为待收货状态或
            if (param.getAfterType().equals(IntegerEnum.YES.getCode())) {
                return cereShopOrder.getPrice();
            } else {
                if (cereShopOrder.getLogisticsPrice() != null) {
                    return cereShopOrder.getPrice().subtract(cereShopOrder.getLogisticsPrice());
                } else {
                    return cereShopOrder.getPrice();
                }
            }
        }


    }

    @Override
    public Page<Afters> getAll(AfterGetAllParam param, CereBuyerUser user) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Afters> list;
        if (StringUtils.isNotBlank(param.getKeyword())) {
            list = cereOrderAfterDAO.getAllWithKeyword(param.getState(), user.getBuyerUserId(), param.getKeyword());
        } else {
            list = cereOrderAfterDAO.getAll(param.getState(), user.getBuyerUserId());
            if (!EmptyUtils.isEmpty(list)) {
                list.forEach(afters -> {
                    //查询售后商品明细数据
                    List<CartSku> skus = cereAfterProductService.findSkusByAfterId(afters.getAfterId());
                    if (!EmptyUtils.isEmpty(skus)) {
                        skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
                    }
                    afters.setSkus(skus);
                });
            }
        }
        PageInfo<Afters> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public AfterDetail getById(Long afterId) throws Exception {
        AfterDetail detail = cereOrderAfterDAO.getById(afterId);
        if (detail != null) {
            if (!EmptyUtils.isEmpty(detail.getImage())) {
                detail.setImages(EmptyUtils.getImages(detail.getImage()));
            }
            //查询自动关闭结束时间
            String end = cereRedisKeyService.findByKey(StringEnum.AFTER_CANCEL.getCode() + "-" + detail.getAfterId());
            if (!EmptyUtils.isEmpty(end)) {
                //计算自动关闭剩余时间
                detail.setTime(TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(), end));
            }
            //查询售后商品明细数据
            List<CartSku> skus = cereAfterProductService.findSkusByAfterId(detail.getAfterId());
            if (!EmptyUtils.isEmpty(skus)) {
                skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
            }
            detail.setSkus(skus);
            //查询协商历史
            List<AfterHistory> histories = cereOrderAfterDAO.findHistories(afterId);
            detail.setIsPlaformState(0);
            histories.forEach(s -> {
                if ("申请平台介入".equals(s.getTitle())) {
                    detail.setIsPlaformState(1);
                }
            });
            detail.setAfterHistory(histories);
        }
        return detail;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void returnRefund(AfterGetByIdParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验售后但状态是否为审核中或者审核不通过
        CereOrderAfter after = cereOrderAfterDAO.checkStateStayAndNo(param.getAfterId());
        if (after == null) {
            throw new CoBusinessException(CoReturnFormat.AFTER_NOT_RETURN);
        }
        //修改售后单状态为已关闭
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_CANCEL.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);

        //清空自动关闭售后延时任务和任务记录
        /*stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId());
        //新增延时任务记录
        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId());*/

        redisUtil.removeFromQueue(cereOrderAfter.getAfterId(), StringEnum.AFTER_CANCEL.getCode());

        //更新订单状态
        /*CereShopOrder order = new CereShopOrder();
        order.setId(cereOrderAfter.getOrderId());
        order.setAfterState(IntegerEnum.AFTER_CANCEL.getCode());
        cereShopOrderService.update(order);*/

        //重新启动订单自动确认收货定时任务
        String end = cereRedisKeyService.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + param.getOrderId());
        if (!EmptyUtils.isEmpty(end)) {
            //stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(), 1, TimeUtils.getCountDownByTime(time,end));
            redisUtil.deleteAndAddDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), end);
        }

        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "客户端操作",
                "买家撤销了售后申请", cereOrderAfter.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void returnGoods(AfterGetByIdParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验售后但状态是否为审核通过且售后类型为退货退款
        CereOrderAfter after = cereOrderAfterDAO.checkStateStayAndNoAndType(param.getAfterId());
        if (after == null) {
            throw new CoBusinessException(CoReturnFormat.AFTER_NOT_RETURN);
        }
        //修改售后单状态为已关闭
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_CANCEL.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);

        //清空自动关闭售后延时任务和任务记录
        /*stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId());
        //新增延时任务记录
        cereRedisKeyService.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+cereOrderAfter.getAfterId());*/

        redisUtil.removeFromQueue(cereOrderAfter.getAfterId(), StringEnum.AFTER_CANCEL.getCode());


        //更新订单状态
        /*CereShopOrder order = new CereShopOrder();
        order.setId(cereOrderAfter.getOrderId());
        order.setAfterState(IntegerEnum.AFTER_CANCEL.getCode());
        cereShopOrderService.update(order);*/

        //重新启动订单自动确认收货定时任务
        String end = cereRedisKeyService.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + param.getOrderId());
        if (!EmptyUtils.isEmpty(end)) {
            //stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(), 1, TimeUtils.getCountDownByTime(time,end));
            redisUtil.deleteAndAddDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), end);
        }

        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "客户端操作",
                "买家撤销退货", cereOrderAfter.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void platform(PlatformParam param, CereBuyerUser user) {
        String time = TimeUtils.yyMMddHHmmss();
        //校验售后但状态是否为审核不通过
        CereOrderAfter after = cereOrderAfterDAO.checkState(param.getAfterId(), IntegerEnum.AFTER_STAY_NO.getCode(), null);
        if (after == null) {
            after = cereOrderAfterDAO.checkState(param.getAfterId(), IntegerEnum.AFTER_RETURN_SUCCESS_NOT_REFUND.getCode(), null);
            if (after == null) {
                throw new CoBusinessException(CoReturnFormat.AFTER_NOT_RETURN);
            }
        }
        //修改售后单状态为评审中
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_PLATFORM_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        CerePlatformAfter cerePlatformAfter = new CerePlatformAfter();
        cerePlatformAfter.setOrderId(param.getOrderId());
        cerePlatformAfter.setReason(param.getReason());
        cerePlatformAfter.setCreateTime(time);
        cerePlatformAfter.setImage(param.getImage());
        cerePlatformAfter.setState(IntegerEnum.PLATFORM_AFTER_STAY.getCode());
        cerePlatformAfterService.insert(cerePlatformAfter);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "客户端操作", "申请平台介入", param.getAfterId(), time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CereOrderAfter cereOrderAfter) {
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
    }

    @Override
    public Long getPostSaleNumByMonth(Integer month, Long buyerUserId) {
        Date time = DateUtil.offsetMonth(new Date(), -month);
        return cereOrderAfterDAO.getPostSaleNumByMonth(time, buyerUserId);
    }

    @Override
    public void deleteAfterById(Long id) {
        cereOrderAfterDAO.deleteByPrimaryKey(id);
        cereAfterProductService.deleteByAfterId(id);
        cereAfterDileverService.deleteByAfterId(id);
    }

    @Override
    public List<Integer> selectAfterStateList(Long orderId) {
        LambdaQueryWrapper<CereOrderAfter> wrapper = Wrappers.<CereOrderAfter>lambdaQuery()
                .eq(CereOrderAfter::getOrderId, orderId);
        List<CereOrderAfter> orderAfterList = cereOrderAfterDAO.selectList(wrapper);
        return orderAfterList.stream().map(CereOrderAfter::getAfterState).collect(Collectors.toList());
    }

    @Override
    public int findAfterCount(Long buyerUserId) {
        return cereOrderAfterDAO.findAfterCount(buyerUserId);
    }

    @Override
    public List<CereOrderAfter> selectByOrderIdList(List<Long> orderIdList) {
        return cereOrderAfterDAO.selectList(Wrappers.<CereOrderAfter>lambdaQuery().in(CereOrderAfter::getOrderId, orderIdList));
    }
}
