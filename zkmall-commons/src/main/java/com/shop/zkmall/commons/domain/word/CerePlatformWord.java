/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_word 关键词实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CerePlatformWord implements Serializable {
    /**
     * 关键词id
     */
    @TableId(type = IdType.AUTO)
    private Long wordId;

    /**
     * 关键词内容
     */
    @NotBlank(message = "关键词内容不能为空")
    private String keyWord;

    /**
     * 是否启用 1-是 0-否
     */
    private Integer state;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

}
