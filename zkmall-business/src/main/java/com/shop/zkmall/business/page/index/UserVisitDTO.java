/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.index;

import com.shop.zkmall.commons.poi.export.ExcelResources;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class UserVisitDTO {

    @ExcelResources(order = 0, title = "日期", cloumn = 15)
    private String date;

    @ExcelResources(order = 1, title = "用户id", cloumn = 15)
    private Long buyerUserId;

    @ExcelResources(order = 2, title = "昵称", cloumn = 15)
    private String nickName;

    @ExcelResources(order = 3, title = "手机号", cloumn = 20)
    private String phone;

    @ExcelResources(order = 4, title = "注册来源", cloumn = 20)
    private String terminal;

    @ExcelResources(order = 5, title = "访问次数", cloumn = 15)
    private Integer visitCount;

    @ExcelResources(order = 6, title = "注册日期", cloumn = 20)
    private String registerDate;

    @ExcelResources(order = 7, title = "是否复购", cloumn = 15)
    private String repurchase;

}
