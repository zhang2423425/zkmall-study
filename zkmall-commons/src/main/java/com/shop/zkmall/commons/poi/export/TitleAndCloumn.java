/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.poi.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleAndCloumn {

    /**
     * 标题
     */
    private String title;

    /**
     * 列宽
     */
    private int cloumn;

}