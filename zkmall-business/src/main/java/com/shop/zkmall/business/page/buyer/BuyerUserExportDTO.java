/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.buyer;

import com.shop.zkmall.commons.poi.export.ExcelResources;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class BuyerUserExportDTO {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @ExcelResources(order = 0, title = "客户id", cloumn = 15)
    private Long buyerUserId;

    /**
     * 客户昵称
     */
    @Schema(description = "客户昵称")
    @ExcelResources(order = 1, title = "客户昵称", cloumn = 15)
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelResources(order = 2, title = "手机号", cloumn = 15)
    private String phone;

    /**
     * 消费次数
     */
    @Schema(description = "消费次数")
    @ExcelResources(order = 3, title = "消费次数", cloumn = 15)
    private Integer frequency;

    /**
     * 累计消费金额
     */
    @Schema(description = "累计消费金额")
    @ExcelResources(order = 4, title = "累计消费金额", cloumn = 15)
    private BigDecimal total;

    /**
     * 上次消费时间
     */
    @Schema(description = "上次消费时间")
    @ExcelResources(order = 5, title = "上次消费时间", cloumn = 15)
    private String lastTime;

    /**
     * 成为客户时间
     */
    @Schema(description = "成为客户时间")
    @ExcelResources(order = 6, title = "成为客户时间", cloumn = 15)
    private String time;

    /**
     * 客户关联标签
     */
    @Schema(description = "客户关联标签")
    @ExcelResources(order = 7, title = "客户关联标签", cloumn = 15)
    private String labelName;

}
