/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.city;

import com.shop.zkmall.commons.domain.city.CereCityManage;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 城市返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "City", description = "城市返回数据实体类")
public class City extends CereCityManage {

    /**
     * 子级数据
     */
    @Schema(description = "子级数据")
    private List<City> childs;
}
