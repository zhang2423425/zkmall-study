/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.express;

import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class QueryTrackData {
    /**
     * 时间，原始格式
     */
    private String time;
    /**
     * 物流轨迹节点内容
     */
    private String context;
    /**
     * 格式化后时间
     */
    private String ftime;
    /**
     * 行政区域的编码
     */
    private String areaCode;
    /**
     * 行政区域的名称
     */
    private String areaName;
    /**
     * 签收状态 (0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投)
     */
    private String status;
}
