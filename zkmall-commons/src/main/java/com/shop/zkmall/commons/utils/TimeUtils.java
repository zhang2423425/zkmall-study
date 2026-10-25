/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期工具类
 *
 * @author zkmall zkmallvip@163.com
 */
public class TimeUtils {


    /**
     * 通过时间秒毫秒数判断两个时间的间隔(天)
     */
    public static int differentDaysByMillisecond(String startTime, String endTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(startTime);
        Date date2 = sdf.parse(endTime);
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取今天日期  yyyy-MM-dd
     */
    public static String today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 获取昨天日期  yyyy-MM-dd
     */
    public static String yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);
    }

    /**
     * 获取今天日期  yyyyMMdd
     */
    public static String todayTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 获取今天日期  yyyy-MM-dd HH:mm:ss
     */
    public static String yyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 判断当前时间是否在某个时间段内
     */
    public static boolean isBelong(String start, String end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;
        Date beginTime;
        Date endTime;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            throw new IllegalArgumentException("解析日期错误", e);
        }

        return belongCalendar(now, beginTime, endTime);
    }

    /**
     * 判断当前时间是否在时间段内
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    /**
     * 获取次日指定时间
     */
    public static String getTomorrow(int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }


    /**
     * 获取当前时间指定分钟后的时间
     */
    public static String getMinuteAfter(int time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, time);
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期几天后的时间
     */
    public static String getMoreDayAfter(String date, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.DATE, day);
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期几小时后的时间
     */
    public static String getMoreHourAfter(String date, int hour) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.HOUR, hour);
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期几年后的时间
     */
    public static String getMoreYearAfter(String date, int year) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.YEAR, year);
        return format.format(calendar.getTime());
    }


    /**
     * 将指定的日期字符串转换成默认格式的日期
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期转化错误");
        }

        return date;
    }

    /**
     * 根据指定两个时间获取间距时间戳（毫秒级）
     */
    public static Long getCountDownByTime(String startTime, String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = format.parse(startTime);
        Date end = format.parse(endTime);
        long time = end.getTime() - start.getTime();
        if (time < 1) {
            time = 1L;
        }
        return time;
    }

    /**
     * 比较第一个参数日期是否大于或等于第二个参数日期
     */
    public static boolean compareTo(String startTime, String endTime) throws Exception {
        if (!EmptyUtils.isEmpty(startTime) && !EmptyUtils.isEmpty(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bt = format.parse(startTime);
            Date et = format.parse(endTime);
            return bt.after(et) || bt.equals(et);
        }
        return false;
    }

    /**
     * 获取前者时间之后N年的时间
     */
    public static String rollYear(String date, int year) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = format.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.YEAR, year);
        return format.format(cal.getTime());
    }

    /**
     * 获取当前日期前7天的时间
     */
    public static List<String> getRecentSevenDay() {
        String[] arr = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c;
        for (int i = 0; i < 7; i++) {
            c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1 * i);
            arr[6 - i] = sdf.format(c.getTime());

        }
        return Arrays.asList(arr);
    }

    /**
     * 取到 hours 以前时间
     */
    public static String headDate(String date, int hours) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(format.parse(date));
        cal.add(Calendar.HOUR_OF_DAY, -hours);
        return format.format(cal.getTime());
    }
}
