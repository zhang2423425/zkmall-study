/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 添加客户
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "UserSaveParam", description = "添加客户请求")
public class UserSaveParam {

    /**
     * 手机号
     */
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private String birthday;

    /**
     * 标签id数组
     */
    @Schema(description = "标签id数组")
    private List<Long> ids;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
