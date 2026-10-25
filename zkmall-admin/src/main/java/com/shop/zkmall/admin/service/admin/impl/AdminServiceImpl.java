/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.admin.impl;

import cn.hutool.core.date.DateUtil;
import com.shop.zkmall.admin.dao.admin.AdminDAO;
import com.shop.zkmall.admin.param.admin.IndexCharts;
import com.shop.zkmall.admin.param.admin.IndexStats;
import com.shop.zkmall.admin.param.admin.StatsAmountByDay;
import com.shop.zkmall.admin.param.admin.StatsByDay;
import com.shop.zkmall.admin.service.admin.AdminService;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP);

    @Override
    public IndexStats indexStats() {
        IndexStats stats = new IndexStats();
        String yesterday = TimeUtils.yesterday();
        String today = TimeUtils.today();
        String tomorrow = TimeUtils.getTomorrow(0);

        // 算上今天14天
        LocalDate twoWeekAgo = LocalDate.now().minusDays(13);
        Date startDate = Date.from(twoWeekAgo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String startDateTime = DateUtil.format(startDate, "yyyy-MM-dd HH:mm:ss");

        // 上周时间
        String lastWeekAgoDateStr = FORMATTER.format(twoWeekAgo.plusDays(7));

        // 查询新用户
        List<StatsByDay> twoWeekUserCount = adminDAO.selectUserCount(startDateTime, tomorrow);
        Map<String, String> userCountData = setStatsData(twoWeekUserCount, lastWeekAgoDateStr, yesterday, today);
        if (userCountData != null) {
            stats.setYesterdayNewUser(Integer.parseInt(userCountData.get("yesterdayData")));
            stats.setTodayNewUser(Integer.parseInt(userCountData.get("todayData")));
            stats.setWeekRelativeRatioNewUser(new BigDecimal(userCountData.get("relativeRate")));
        }

        // 查询访问量
        List<StatsByDay> twoWeekVisitCount = adminDAO.selectVisitCount(startDateTime, tomorrow);
        Map<String, String> visitCountData = setStatsData(twoWeekVisitCount, lastWeekAgoDateStr, yesterday, today);
        if (visitCountData != null) {
            stats.setYesterdayVisitCount(Integer.parseInt(visitCountData.get("yesterdayData")));
            stats.setTodayVisitCount(Integer.parseInt(visitCountData.get("todayData")));
            stats.setWeekRelativeRatioVisitCount(new BigDecimal(visitCountData.get("relativeRate")));
        }

        // 查询访客数
        List<StatsByDay> twoWeekVisitUser = adminDAO.selectVisitUserCount(startDateTime, tomorrow);
        Map<String, String> visitUserData = setStatsData(twoWeekVisitUser, lastWeekAgoDateStr, yesterday, today);
        if (visitUserData != null) {
            stats.setYesterdayVisitUser(Integer.parseInt(visitUserData.get("yesterdayData")));
            stats.setTodayVisitUser(Integer.parseInt(visitUserData.get("todayData")));
            stats.setWeekRelativeRatioVisitUser(new BigDecimal(visitUserData.get("relativeRate")));
        }

        // 查询店铺数
        List<StatsByDay> twoWeekShopCheck = adminDAO.selectShopCount(startDateTime, tomorrow);
        Map<String, String> shopCheckData = setStatsData(twoWeekShopCheck, lastWeekAgoDateStr, yesterday, today);
        if (shopCheckData != null) {
            stats.setYesterdayShopCount(Integer.parseInt(shopCheckData.get("yesterdayData")));
            stats.setTodayShopCount(Integer.parseInt(shopCheckData.get("todayData")));
            stats.setWeekRelativeRatioShopCount(new BigDecimal(shopCheckData.get("relativeRate")));
        }

        return stats;
    }

    /**
     * 计算统计数据
     */
    public Map<String, String> setStatsData(List<StatsByDay> twoWeekList, String lastWeekAgoDateStr, String yesterday, String today) {
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(twoWeekList)) {
            int lastWeekSum = 0;
            int thisWeekSum = 0;
            map.put("yesterdayData", "0");
            map.put("todayData", "0");
            for (StatsByDay byDay : twoWeekList) {
                if (byDay.getStatsDate().compareTo(lastWeekAgoDateStr) < 0) {
                    lastWeekSum += byDay.getTotalCount();
                } else {
                    thisWeekSum += byDay.getTotalCount();
                }
                if (byDay.getStatsDate().equals(yesterday)) {
                    map.put("yesterdayData", byDay.getTotalCount().toString());
                } else if (byDay.getStatsDate().equals(today)) {
                    map.put("todayData", byDay.getTotalCount().toString());
                }
            }
            if (lastWeekSum != 0) {
                BigDecimal dividend = new BigDecimal((thisWeekSum - lastWeekSum) * 100);
                BigDecimal divisor = new BigDecimal(lastWeekSum);
                BigDecimal rate = dividend.divide(divisor, 2, RoundingMode.HALF_UP);
                map.put("relativeRate", rate.toString());
            } else {
                map.put("relativeRate", ONE_HUNDRED.toString());
            }
            return map;
        }
        return null;
    }

    @Override
    public IndexCharts indexCharts() {
        IndexCharts charts = new IndexCharts();

        String todayDate = TimeUtils.today();
        String todayTime = TimeUtils.todayTime();
        String tomorrowTime = TimeUtils.getTomorrow(0);
        String oneWeekAgoDate = LocalDate.now().minusDays(6).format(FORMATTER);
        String oneWeekAgoTime = oneWeekAgoDate + " 00:00:00";
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1).plusDays(1);
        String oneMonthAgoDate = oneMonthAgo.format(FORMATTER);
        String oneMonthAgoTime = oneMonthAgoDate + " 00:00:00";

        LocalDate curMonthStart = YearMonth.now().atDay(1);
        LocalDate lastYearCurMonthStart = curMonthStart.minusYears(1);
        LocalDate lastYearNextMonthStart = lastYearCurMonthStart.plusMonths(1);

        String curMonthStartTime = curMonthStart.format(FORMATTER) + " 00:00:00";
        String nextMonthStartTime = curMonthStart.plusMonths(1).format(FORMATTER) + " 00:00:00";
        String lastYearCurMonthStartTime = lastYearCurMonthStart.format(FORMATTER) + " 00:00:00";
        String lastYearNextMonthStartTime = lastYearNextMonthStart.format(FORMATTER) + " 00:00:00";

        List<StatsAmountByDay> orderAmountList = adminDAO.selectOrderAmountList(oneMonthAgoTime, tomorrowTime);
        Map<String, StatsAmountByDay> orderAmountMap = orderAmountList.stream().collect(Collectors.toMap(StatsAmountByDay::getStatsDate, Function.identity()));

        // 查询订单金额
        BigDecimal todayOrderAmount = orderAmountList.stream().filter(o -> o.getStatsDate().equals(todayDate))
                .map(StatsAmountByDay::getAmount).findFirst().orElse(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        charts.setTodayOrderAmount(todayOrderAmount);
        List<StatsAmountByDay> fullList = new ArrayList<>();
        LocalDate tmpDate = oneMonthAgo;
        while (tmpDate.isBefore(LocalDate.now().plusDays(1))) {
            String tmpDateStr = tmpDate.format(FORMATTER);
            StatsAmountByDay day = orderAmountMap.get(tmpDateStr);
            if (day == null) {
                day = new StatsAmountByDay();
                day.setStatsDate(tmpDateStr);
                day.setAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            }
            fullList.add(day);
            tmpDate = tmpDate.plusDays(1);
        }
        charts.setOrderAmountList(fullList);

        // 查询订单量
        List<StatsByDay> orderCountList = adminDAO.selectOrderCountList(oneMonthAgoTime, tomorrowTime);
        Map<String, StatsByDay> orderCountMap = orderCountList.stream().collect(Collectors.toMap(StatsByDay::getStatsDate, Function.identity()));

        Integer lastYearTodayOrderCount = adminDAO.selectOrderCountSum(todayTime, tomorrowTime);
        Integer curMonthOrderCount = adminDAO.selectOrderCountSum(curMonthStartTime, nextMonthStartTime);
        Integer lastYearCurMonthOrderCount = adminDAO.selectOrderCountSum(lastYearCurMonthStartTime, lastYearNextMonthStartTime);

        int todayOrderCount = orderCountList.stream().filter(o -> o.getStatsDate().equals(todayDate)).mapToInt(StatsByDay::getTotalCount).findFirst().orElse(0);
        charts.setTodayOrderCount(todayOrderCount);

        List<StatsByDay> orderCountFullList = generateFullCountList(oneMonthAgo, orderCountMap);
        charts.setOrderCountList(orderCountFullList);
        if (lastYearTodayOrderCount != 0) {
            BigDecimal dividend = new BigDecimal((todayOrderCount - lastYearTodayOrderCount) * 100);
            BigDecimal divisor = new BigDecimal(lastYearTodayOrderCount);
            BigDecimal rate = dividend.divide(divisor, 2, RoundingMode.HALF_UP);
            charts.setDayToDayOrderCountRelativeRate(rate);
        } else {
            charts.setDayToDayOrderCountRelativeRate(ONE_HUNDRED);
        }
        charts.setCurMonthOrderCount(curMonthOrderCount);
        if (lastYearCurMonthOrderCount != 0) {
            BigDecimal dividend = new BigDecimal((curMonthOrderCount - lastYearCurMonthOrderCount) * 100);
            BigDecimal divisor = new BigDecimal(lastYearCurMonthOrderCount);
            BigDecimal rate = dividend.divide(divisor, 2, RoundingMode.HALF_UP);
            charts.setMonthToMonthOrderCountRelativeRate(rate);
        } else {
            charts.setMonthToMonthOrderCountRelativeRate(ONE_HUNDRED);
        }

        // 查询支付人数
        List<StatsByDay> payUserCountList = adminDAO.selectPayUserCountList(oneWeekAgoTime, tomorrowTime);
        Map<String, StatsByDay> payUserCountMap = payUserCountList.stream().collect(Collectors.toMap(StatsByDay::getStatsDate, Function.identity()));

        Integer lastYearTodayPayUserCount = adminDAO.selectPayUserCountSum(todayTime, tomorrowTime);
        Integer curMonthPayUserCount = adminDAO.selectPayUserCountSum(curMonthStartTime, nextMonthStartTime);
        Integer lastYearCurMonthPayUserCount = adminDAO.selectPayUserCountSum(lastYearCurMonthStartTime, lastYearNextMonthStartTime);

        int todayPayUserCount = payUserCountList.stream().filter(o -> o.getStatsDate().equals(todayDate)).mapToInt(StatsByDay::getTotalCount).findFirst().orElse(0);
        charts.setTodayPayUserCount(todayPayUserCount);

        List<StatsByDay> payUserCountFullList = generateFullCountList(oneMonthAgo, payUserCountMap);
        charts.setPayUserCountList(payUserCountFullList);
        if (lastYearTodayPayUserCount != 0) {
            BigDecimal dividend = new BigDecimal((todayPayUserCount - lastYearTodayPayUserCount) * 100);
            BigDecimal divisor = new BigDecimal(lastYearTodayPayUserCount);
            BigDecimal rate = dividend.divide(divisor, 2, RoundingMode.HALF_UP);
            charts.setDayToDayPayUserCountRelativeRate(rate);
        } else {
            charts.setDayToDayPayUserCountRelativeRate(ONE_HUNDRED);
        }
        charts.setCurMonthPayUserCount(curMonthPayUserCount);
        if (lastYearCurMonthPayUserCount != 0) {
            BigDecimal dividend = new BigDecimal((curMonthPayUserCount - lastYearCurMonthPayUserCount) * 100);
            BigDecimal divisor = new BigDecimal(lastYearCurMonthPayUserCount);
            BigDecimal rate = dividend.divide(divisor, 2, RoundingMode.HALF_UP);
            charts.setMonthToMonthPayUserCountRelativeRate(rate);
        } else {
            charts.setMonthToMonthPayUserCountRelativeRate(ONE_HUNDRED);
        }

        return charts;
    }

    /**
     * 生成有完整日期的列表
     */
    private List<StatsByDay> generateFullCountList(LocalDate oneMonthAgo, Map<String, StatsByDay> countMap) {
        LocalDate tmpDate;
        List<StatsByDay> fullList = new ArrayList<>();
        tmpDate = oneMonthAgo;
        while (tmpDate.isBefore(LocalDate.now().plusDays(1))) {
            String tmpDateStr = tmpDate.format(FORMATTER);
            StatsByDay day = countMap.get(tmpDateStr);
            if (day == null) {
                day = new StatsByDay();
                day.setStatsDate(tmpDateStr);
                day.setTotalCount(0);
            }
            fullList.add(day);
            tmpDate = tmpDate.plusDays(1);
        }
        return fullList;
    }

}
