/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.poi;

import com.shop.zkmall.commons.domain.label.PlatformLabel;
import com.shop.zkmall.commons.domain.shop.ShopDataDetail;
import com.shop.zkmall.commons.domain.shop.ShopDetail;
import com.shop.zkmall.commons.utils.EmptyUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public class ExcelUtils {

    public static XSSFWorkbook createPlatformLabelExcel(List<String> titles, List<PlatformLabel> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            PlatformLabel label = list.get(i);
            //获取行
            XSSFRow sheetRow = sheet.createRow(i + 1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(label.getLabelName());
            sheetRow.createCell(1).setCellValue(label.getUsers());
            if (label.getLabelType().equals(1)) {
                sheetRow.createCell(2).setCellValue("手动标签");
            } else {
                sheetRow.createCell(2).setCellValue("自动标签");
            }
            String condition = "";
            if (!EmptyUtils.isEmpty(label.getConditions())) {
                for (String str : label.getConditions()) {
                    condition += str + "\r\n";
                }
            }
            sheetRow.createCell(3).setCellValue(condition);
            for (int n = 0; n < 4; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }

    public static XSSFWorkbook createShopDetailExcel(List<String> titles, List<ShopDetail> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            ShopDetail detail = list.get(i);
            //获取行
            XSSFRow sheetRow = sheet.createRow(i + 1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(detail.getShopName());
            sheetRow.createCell(1).setCellValue(detail.getShopCode());
            sheetRow.createCell(2).setCellValue(detail.getProducts());
            sheetRow.createCell(3).setCellValue(detail.getPersons());
            sheetRow.createCell(4).setCellValue(detail.getOrders());
            sheetRow.createCell(5).setCellValue(detail.getCustomers());
            sheetRow.createCell(6).setCellValue(String.valueOf(detail.getPrice()));
            sheetRow.createCell(7).setCellValue(String.valueOf(detail.getTotal()));
            for (int n = 0; n < 8; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }

    public static XSSFWorkbook createSeckillShopDetailExcel(List<String> titles, List<ShopDataDetail> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            ShopDataDetail detail = list.get(i);
            //获取行
            XSSFRow sheetRow = sheet.createRow(i + 1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(detail.getShopName());
            sheetRow.createCell(1).setCellValue(detail.getShopCode());
            sheetRow.createCell(2).setCellValue(detail.getProducts());
            sheetRow.createCell(3).setCellValue(detail.getVisit());
            sheetRow.createCell(4).setCellValue(detail.getOrders());
            sheetRow.createCell(5).setCellValue(detail.getFinish());
            sheetRow.createCell(6).setCellValue(String.valueOf(detail.getTotal()));
            for (int n = 0; n < 7; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }
}
