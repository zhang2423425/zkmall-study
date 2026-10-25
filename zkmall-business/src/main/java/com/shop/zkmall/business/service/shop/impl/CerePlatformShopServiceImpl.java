/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shop.zkmall.business.dao.buyer.CereBuyerUserDAO;
import com.shop.zkmall.business.dao.order.CereShopOrderDAO;
import com.shop.zkmall.business.dao.product.CereProductStatsByDayDAO;
import com.shop.zkmall.business.dao.shop.CerePlatformShopDAO;
import com.shop.zkmall.business.dao.shop.CereShopVisitDAO;
import com.shop.zkmall.business.page.index.*;
import com.shop.zkmall.business.page.shop.Shop;
import com.shop.zkmall.business.page.shop.ShopConversion;
import com.shop.zkmall.business.param.index.IndexParam;
import com.shop.zkmall.business.param.shop.ShopUpdateLogoParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.*;
import com.shop.zkmall.business.utils.EncodeUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.ParamEnum;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductStatsByDay;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopReturn;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformShopServiceImpl implements CerePlatformShopService {

    private final CerePlatformShopDAO cerePlatformShopDAO;

    private final CereShopReturnService cereShopReturnService;

    private final CereShopPersonalService cereShopPersonalService;

    private final CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    private final CereShopEnterpriseService cereShopEnterpriseService;

    private final CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereShopVisitDAO cereShopVisitDAO;

    private final CereBuyerUserDAO cereBuyerUserDAO;

    private final CereShopOrderDAO cereShopOrderDAO;

    private final CereProductStatsByDayDAO cereProductStatsByDayDAO;

    private final EncodeUtil encodeUtil;

    @Override
    public CerePlatformShop selectByPrimaryKey(Long shopId) {
        return cerePlatformShopDAO.selectByPrimaryKey(shopId);
    }

    @Override
    public CerePlatformShop findByShopName(String shopName) {
        return cerePlatformShopDAO.findByShopName(shopName);
    }

    @Override
    public Shop getById(Long shopId) {
        //查询店铺信息
        Shop shop = cerePlatformShopDAO.findShop(shopId);
        if (shop != null) {
            //查询店铺退货地址信息
            CereShopReturn cereShopReturn = cereShopReturnService.findByShopId(shopId);
            shop.setShopReturn(cereShopReturn);
            //查询认证数据
            if (IntegerEnum.getByName("主体类型-个人").equals(shop.getAuthenType())) {
                //个人认证
                shop.setPersonal(cereShopPersonalService.findByShopId(shopId));
            } else if (IntegerEnum.getByName("主体类型-个体工商户").equals(shop.getAuthenType())) {
                //个体工商户户认证
                shop.setIndividualBusinesses(cereShopIndividualBusinessesService.findByShopId(shopId));
            } else if (IntegerEnum.getByName("主体类型-企业").equals(shop.getAuthenType())) {
                //企业认证
                shop.setEnterprise(cereShopEnterpriseService.findByShopId(shopId));
            } else if (IntegerEnum.getByName("主体类型-其他组织").equals(shop.getAuthenType())) {
                //其他组织认证
                shop.setOtherOrganizations(cereShopOtherOrganizationsService.findByShopId(shopId));
            }
        }
        return shop;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopUpdateLogoParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop = new CerePlatformShop();
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setShopLogo(param.getShopLogo());
        cerePlatformShop.setShopName(param.getShopName());
        cerePlatformShop.setShopBrief(param.getShopBrief());
        cerePlatformShop.setShopAdress(param.getShopAdress());
        cerePlatformShop.setShopPhone(param.getShopPhone());
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
        if (!EmptyUtils.isEmpty(param.getShopReturn())) {
            //查询是否有退货地址信息
            CereShopReturn shopReturn = cereShopReturnService.findByShopId(param.getShopId());
            if (shopReturn != null) {
                //更新退货地址
                shopReturn.setReturnAdress(param.getShopReturn().getReturnAdress());
                shopReturn.setReturnPerson(param.getShopReturn().getReturnPerson());
                shopReturn.setReturnPhone(param.getShopReturn().getReturnPhone());
                cereShopReturnService.update(shopReturn);
            } else {
                param.getShopReturn().setShopId(param.getShopId());
                cereShopReturnService.insert(param.getShopReturn());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "店铺管理", "商户端操作", "修改店铺logo", cerePlatformShop.getShopId(), time);
    }

    @Override
    public void updateState(CerePlatformShop cerePlatformShop) {
        cerePlatformShopDAO.updateState(cerePlatformShop);
    }

    @Override
    public Index index(IndexParam param) {
        Index index = new Index();
        //查询今日店铺访问人数
        index.setTotal(cerePlatformShopDAO.getTotal(param.getShopId()));
        //查询今日待处理订单数
        index.setStayOrders(cerePlatformShopDAO.getStayOrders(param.getShopId()));
        //查询今日待处理售后订单数
        index.setStayAfters(cerePlatformShopDAO.getStayAfters(param.getShopId()));
        //查询今日成交金额
        index.setMoney(cerePlatformShopDAO.getMoney(param.getShopId()));
        //根据时间条件获取对应统计时间数据
        List<String> weeks = getVisitWeeks(param.getCondition());
        //查询月度访问人数数据
        VisitWeek visitWeek = new VisitWeek();
        List<Integer> weekDayVisitCountList = new ArrayList<>();
        for (String weekDay : weeks) {
            String startTime = weekDay + " 00:00:00";
            String endTime = weekDay + " 23:59:59";
            Integer visitCount = cerePlatformShopDAO.getVisit(param.getShopId(), startTime, endTime);
            weekDayVisitCountList.add(visitCount);
        }

        //设置总访问人数
        String startTime = weeks.get(0) + " 00:00:00";
        String endTime = weeks.get(weeks.size() - 1) + " 23:59:59";
        index.setCount(cerePlatformShopDAO.getVisit(param.getShopId(), startTime, endTime));

        visitWeek.setTotal(weekDayVisitCountList);
        visitWeek.setTime(weeks);
        index.setVisitWeek(visitWeek);
        //查询订单转化数据
        ShopConversion shopConversion = new ShopConversion();
        //初始化文字描述
        List<String> names = new ArrayList<>();
        List<BigDecimal> rates = new ArrayList<>();
        //查询访问人数
        BigDecimal visits = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_VISIT.getCode());
        //查询加购人数
        BigDecimal carts = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_CART.getCode());
        //查询结算人数
        BigDecimal checks = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_CHECK.getCode());
        //查询调取支付人数
        BigDecimal pays = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_PAY.getCode());
        //查询支付成功人数
        BigDecimal success = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_PAY_SUCCESS.getCode());
        //计算转化率
        BigDecimal hundred = new BigDecimal(100);
        if (!EmptyUtils.isEmptyBigdecimal(visits)) {
            names.add("访问");
            rates.add(hundred);
            if (!EmptyUtils.isEmptyBigdecimal(carts)) {
                names.add("加购");
                rates.add(carts.divide(visits, 2, RoundingMode.HALF_UP).multiply(hundred).setScale(0, RoundingMode.HALF_UP));
            }
            if (!EmptyUtils.isEmptyBigdecimal(checks)) {
                names.add("结算");
                rates.add(checks.divide(visits, 2, RoundingMode.HALF_UP).multiply(hundred).setScale(0, RoundingMode.HALF_UP));
            }
            if (!EmptyUtils.isEmptyBigdecimal(pays)) {
                names.add("调取支付");
                rates.add(pays.divide(visits, 2, RoundingMode.HALF_UP).multiply(hundred).setScale(0, RoundingMode.HALF_UP));
            }
            if (!EmptyUtils.isEmptyBigdecimal(success)) {
                names.add("支付成功");
                rates.add(success.divide(visits, 2, RoundingMode.HALF_UP).multiply(hundred).setScale(0, RoundingMode.HALF_UP));
            }
            shopConversion.setRates(rates);
        }
        shopConversion.setNames(names);
        index.setConversion(shopConversion);
        //计算总的转化数
        BigDecimal rate = carts.add(checks).add(pays).add(success);
        //计算总转化率=（加购人数+结账人数+调用支付人数+支付成功人数）/访问人数
        if (!EmptyUtils.isEmptyBigdecimal(rate) && !EmptyUtils.isEmptyBigdecimal(visits)) {
            index.setRate(rate.divide(visits, 2, RoundingMode.HALF_UP).multiply(hundred).setScale(0, RoundingMode.HALF_UP));
        }
        //根据时间条件查询热卖商品数据(10条)
        List<HotSellProduct> products = cerePlatformShopDAO.getHotProducts(param.getShopId(), param.getCondition());
        index.setHotSellProducts(products);
        return index;
    }

    @Override
    public Index indexTest(IndexParam param) {
        Index index = new Index();
        //查询今日店铺访问人数
        index.setTotal(12);
        //查询今日待处理订单数
        index.setStayOrders(123);
        //查询今日待处理售后订单数
        index.setStayAfters(321);
        //查询今日成交金额
        index.setMoney(new BigDecimal(54665));
        //根据时间条件获取对应统计时间数据
        List<String> weeks = getVisitWeeks(param.getCondition());
        List<Integer> total = new ArrayList<>();
        //查询月度访问人数数据
        if (!EmptyUtils.isEmpty(weeks)) {
            weeks.forEach(a -> total.add(20));
        }
        VisitWeek visitWeek = new VisitWeek();
        visitWeek.setTime(weeks);
        visitWeek.setTotal(total);
        index.setVisitWeek(visitWeek);
        //查询订单转化数据
        ShopConversion shopConversion = new ShopConversion();
        List<String> names = new ArrayList<>();
        names.add("访问");
        names.add("加购");
        names.add("结算");
        names.add("调取支付");
        names.add("支付成功");
        List<BigDecimal> rates = new ArrayList<>();
        rates.add(new BigDecimal(100));
        rates.add(new BigDecimal(5));
        rates.add(new BigDecimal(12));
        rates.add(new BigDecimal(12));
        rates.add(new BigDecimal(32));
        shopConversion.setNames(names);
        shopConversion.setRates(rates);
        //计算转化率
        index.setConversion(shopConversion);
        //根据时间条件查询热卖商品数据(10条)
        List<HotSellProduct> products = new ArrayList<>();
        products.add(new HotSellProduct("牛油果", 23));
        products.add(new HotSellProduct("牛肉粒", 23));
        products.add(new HotSellProduct("坚果", 23));
        products.add(new HotSellProduct("iphone12", 23));
        products.add(new HotSellProduct("小苏打饼干", 23));
        products.add(new HotSellProduct("薯片", 23));
        products.add(new HotSellProduct("辣条", 23));
        products.add(new HotSellProduct("肥宅水", 23));
        products.add(new HotSellProduct("电饭煲", 23));
        products.add(new HotSellProduct("笔记本电脑", 23));
        index.setHotSellProducts(products);
        index.setCount(156);
        index.setRate(new BigDecimal(23));
        return index;
    }

    @Override
    public Integer findPayUsers(Long productId) {
        return cerePlatformShopDAO.findPayUsers(productId);
    }

    @Override
    public List<CereBusinessShop> findIds() {
        return cerePlatformShopDAO.findIds();
    }

    @Override
    public Long findShopRoleId(Long id) {
        return cerePlatformShopDAO.findShopRoleId(id);
    }

    @Override
    public List<CerePlatformShop> selectAll() {
        return cerePlatformShopDAO.selectAll();
    }

    @Override
    public List<UserVisitDTO> selectUserVisitForExport(IndexParam param) {
        List<String> days = getVisitWeeks(param.getCondition());
        List<UserVisitDTO> visitDTOList = new ArrayList<>();
        Map<Long, Integer> purchaseCountMap = new HashMap<>();
        for (String day : days) {
            List<UserVisitDTO> userVisitList = cereShopVisitDAO.selectDistinctVisitUser(param.getShopId(), day + " 00:00:00", day + "23:59:59");
            log.info("userVisitList {}", userVisitList.size());
            if (CollectionUtils.isNotEmpty(userVisitList)) {
                Map<Long, Integer> visitCountMap = userVisitList.stream().collect(Collectors.toMap(UserVisitDTO::getBuyerUserId, UserVisitDTO::getVisitCount));
                List<CereBuyerUser> userList = cereBuyerUserDAO.selectBatchIds(userVisitList
                        .stream().map(UserVisitDTO::getBuyerUserId).collect(Collectors.toList()));
                log.info("userList {}", userList);
                for (CereBuyerUser user : userList) {
                    UserVisitDTO visitDTO = new UserVisitDTO();
                    visitDTO.setDate(day);
                    visitDTO.setBuyerUserId(user.getBuyerUserId());
                    visitDTO.setNickName(encodeUtil.encodeInfo(user.getName()));
                    visitDTO.setPhone(encodeUtil.encodePhone(user.getPhone()));
                    visitDTO.setRegisterDate(user.getCreateTime());
                    Integer purchaseCount = purchaseCountMap.get(user.getBuyerUserId());
                    if (purchaseCount == null) {
                        purchaseCount = cereShopOrderDAO.selectCount(Wrappers.<CereShopOrder>lambdaQuery()
                                .eq(CereShopOrder::getBuyerUserId, user.getBuyerUserId())
                                .eq(CereShopOrder::getPaymentState, IntegerEnum.YES.getCode())).intValue();
                        purchaseCountMap.put(user.getBuyerUserId(), purchaseCount);
                    }
                    visitDTO.setRepurchase(purchaseCount > 1 ? IntegerEnum.YES.getName() : IntegerEnum.NO.getName());
                    visitDTO.setTerminal(IntegerEnum.TERMINAL_MAP.get(user.getTerminal()).getName());
                    visitDTO.setVisitCount(visitCountMap.get(user.getBuyerUserId()));
                    visitDTOList.add(visitDTO);
                }
            }
        }
        return visitDTOList;
    }

    @Override
    public List<OrderConvertDTO> selectOrderConvertForExport(IndexParam param) {
        List<String> days = getVisitWeeks(param.getCondition());
        String startTime = days.get(0) + " 00:00:00";
        String endTime = days.get(days.size() - 1) + " 23:59:59";
        List<OrderConvertDTO> list1 = cereShopOrderDAO.selectWaitToPayStatsByDate(param.getShopId(), startTime, endTime);
        List<OrderConvertDTO> list2 = cereShopOrderDAO.selectPayStatsByDate(param.getShopId(), startTime, endTime);
        List<OrderConvertDTO> list3 = cereShopOrderDAO.selectDeliverStatsByDate(param.getShopId(), startTime, endTime);
        List<OrderConvertDTO> list4 = cereShopOrderDAO.selectAfterSaleStatsByDate(param.getShopId(), startTime, endTime);
        Map<String, Integer> map1 = list1.stream().collect(Collectors.toMap(OrderConvertDTO::getDate, OrderConvertDTO::getWaitToPayCount));
        Map<String, Integer> map2 = list2.stream().collect(Collectors.toMap(OrderConvertDTO::getDate, OrderConvertDTO::getPayCount));
        Map<String, Integer> map3 = list3.stream().collect(Collectors.toMap(OrderConvertDTO::getDate, OrderConvertDTO::getDeliverCount));
        Map<String, Integer> map4 = list4.stream().collect(Collectors.toMap(OrderConvertDTO::getDate, OrderConvertDTO::getAfterSaleCount));
        List<OrderConvertDTO> result = new ArrayList<>();
        for (String day : days) {
            OrderConvertDTO dto = new OrderConvertDTO();
            dto.setDate(day);
            dto.setWaitToPayCount(map1.getOrDefault(day, 0));
            dto.setPayCount(map2.getOrDefault(day, 0));
            dto.setDeliverCount(map3.getOrDefault(day, 0));
            dto.setAfterSaleCount(map4.getOrDefault(day, 0));
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<HotProductDTO> selectHotProductForExport(IndexParam param) {
        List<String> days = getVisitWeeks(param.getCondition());
        String startTime = days.get(0);
        String endTime = days.get(days.size() - 1);
        List<HotProductDTO> result = new ArrayList<>();
        List<CereProductStatsByDay> statsList = cereProductStatsByDayDAO.selectStatsByTime(param.getShopId(), startTime, endTime);
        for (CereProductStatsByDay stat : statsList) {
            HotProductDTO dto = new HotProductDTO();
            BeanUtils.copyProperties(stat, dto);
            dto.setDate(stat.getCreateDate());
            result.add(dto);
        }
        return result;
    }

    @Override
    public CerePlatformShop check(String shopPhone) {
        return cerePlatformShopDAO.check(shopPhone);
    }

    @Override
    public void insert(CerePlatformShop cerePlatformShop) {
        cerePlatformShopDAO.insert(cerePlatformShop);
    }

    @Override
    public CerePlatformShop findByPhone(String shopPhone) {
        return cerePlatformShopDAO.findByPhone(shopPhone);
    }

    @Override
    public void update(CerePlatformShop cerePlatformShop) {
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
    }

    @Override
    public CerePlatformShop checkShopIdByName(String shopName, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByName(shopName, shopId);
    }

    @Override
    public CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByPhone(shopPhone, shopId);
    }

    private List<String> getVisitWeeks(Integer condition) {
        List<String> weeks = new ArrayList<>();
        if (IntegerEnum.CONDITION_TODAY.getCode().equals(condition)) {
            //封装今日时间
            weeks.add(TimeUtils.today());
        } else if (IntegerEnum.CONDITION_YESTERDAY.getCode().equals(condition)) {
            //封装昨日时间
            weeks.add(TimeUtils.yesterday());
        } else if (IntegerEnum.CONDITION_WEEK.getCode().equals(condition)) {
            //封装近7天时间
            weeks = TimeUtils.getRecentSevenDay();
        } else if (IntegerEnum.CONDITION_THIRD.getCode().equals(condition)) {
            //封装过去30天
            String today = TimeUtils.today();
            try {
                for (int i = 29; i > 0; i--) {
                    weeks.add(TimeUtils.getMoreDayAfter(today + " 00:00:00", -1 * i).substring(0, 10));
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
            weeks.add(today);
        }
        return weeks;
    }
}
