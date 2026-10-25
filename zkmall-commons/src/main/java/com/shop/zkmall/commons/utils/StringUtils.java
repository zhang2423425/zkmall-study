/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

/**
 * @author zkmall zkmallvip@163.com
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 对电话号码打码
     */
    public static String encodePhone(String phone) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(phone)) {
            if (phone.length() <= 1) {
                return "*";
            } else if (phone.length() <= 3) {
                String head = phone.substring(0, 1);
                for (int i = 0; i < 3 - phone.length(); i++) {
                    head = head + "*";
                }
                return head;
            } else if (phone.length() < 8) {
                String head = phone.substring(0, 3);
                for (int i = 0; i < phone.length() - 3; i++) {
                    head = head + "*";
                }
                return head;
            } else {
                String head = phone.substring(0, 3);
                for (int i = 0; i < 4; i++) {
                    head = head + "*";
                }
                return head + phone.substring(7);
            }
        }
        return phone;
    }

    /**
     * 对信息打码
     */
    public static String encodeInfo(String info) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(info)) {
            if (info.length() <= 1) {
                return "*";
            } else if (info.length() == 2) {
                String head = info.substring(0, 1);
                head = head + "*";
                return head;
            } else {
                String head = info.substring(0, 1);
                for (int i = 0; i < info.length() - 2; i++) {
                    head = head + "*";
                }
                return head + info.substring(info.length() - 1);
            }
        }
        return info;
    }

    /**
     * 做脱敏处理
     *
     * @param str      原字符串
     * @param startNum 开头保留位数
     * @param endNum   结尾保留位数
     * @return 处理后结果
     */
    public static String showStartAndEnd(String str, int startNum, int endNum) {
//        System.out.println(str);
        String s1 = org.apache.commons.lang.StringUtils.right(str, endNum);
        String s2 = org.apache.commons.lang.StringUtils.leftPad(s1, org.apache.commons.lang.StringUtils.length(str), "*");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < startNum; i++) {
            sb.append("*");
        }
        String s3 = org.apache.commons.lang.StringUtils.removeStart(s2, sb.toString());
        String s4 = org.apache.commons.lang.StringUtils.left(str, startNum).concat(s3);
//        System.out.println(s4);
        return s4;
    }
}
