/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_web_log 请求记录实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CerePlatformWebLog implements Serializable {
    /**
     * 请求记录id
     */
    @Schema(description = "请求记录id")
    @TableId(type = IdType.AUTO)
    private Long webLogId;

    /**
     * 请求用户名
     */
    @Schema(description = "请求用户名")
    private String name;

    /**
     * 请求接口地址
     */
    @Schema(description = "请求接口地址")
    private String url;

    /**
     * 请求接口参数
     */
    @Schema(description = "请求接口参数")
    private String params;

    /**
     * 请求服务类型    1-平台端 2-商家端 3-C端
     */
    @Schema(description = "请求服务类型    1-平台端 2-商家端 3-C端")
    private Integer type;

    /**
     * IP地址
     */
    @Schema(description = "IP地址")
    private String ip;

    /**
     * IP来源
     */
    @Schema(description = "IP来源")
    private String ipSource;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @TableField("`describe`")
    private String describe;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器")
    private String browserName;

    /**
     * 耗时（毫秒）
     */
    @Schema(description = "耗时（毫秒）")
    private Integer time;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

}
