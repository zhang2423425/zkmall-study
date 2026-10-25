/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * cere_privacy_verify_setting表对应的实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CerePrivacyVerifySetting {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * project
     */
    private Long project;

    /**
     * 电话号码
     */
    private String phone;

}
