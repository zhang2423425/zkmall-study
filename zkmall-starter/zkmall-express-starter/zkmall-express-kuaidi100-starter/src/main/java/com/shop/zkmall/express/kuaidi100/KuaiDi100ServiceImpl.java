/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.express.kuaidi100;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.shop.zkmall.commons.domain.express.HttpResult;
import com.shop.zkmall.commons.domain.express.QueryTrackData;
import com.shop.zkmall.commons.domain.express.QueryTrackParam;
import com.shop.zkmall.commons.domain.express.QueryTrackReq;
import com.shop.zkmall.commons.domain.express.QueryTrackResp;
import com.shop.zkmall.commons.domain.express.ShippingDTO;
import com.shop.zkmall.commons.domain.express.ShippingTrace;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.express.IExpressService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 快递100
 *
 * @author zkmall zkmallvip@163.com
 */
@RequiredArgsConstructor
public class KuaiDi100ServiceImpl implements IExpressService {

    private final KuaiDi100Properties kuaiDi100Properties;

    @Override
    public List<ShippingTrace> findTraces(String code, String formid) {
        QueryTrackReq queryTrackReq = new QueryTrackReq();
        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(code);
        queryTrackParam.setNum(formid);
        queryTrackReq.setParam(queryTrackParam);
        queryTrackReq.setCustomer(kuaiDi100Properties.getCustomer());
        queryTrackReq.setSign(DigestUtils.md5Hex(new Gson().toJson(queryTrackParam) + kuaiDi100Properties.getKey() + kuaiDi100Properties.getCustomer()).toUpperCase());
        HttpResult httpResult = HttpUtils.doPost(kuaiDi100Properties.getQueryUrl(), queryTrackReq);
        if (httpResult.getStatus() == 200 && StringUtils.isNotBlank(httpResult.getBody())) {
            QueryTrackResp queryTrackResp = JSON.parseObject(httpResult.getBody(), QueryTrackResp.class);
            ShippingDTO shippingDTO = new ShippingDTO();
            shippingDTO.setShipperCode(code);
            shippingDTO.setLogisticCode(formid);
            if (queryTrackResp != null && queryTrackResp.getData() != null) {
                List<ShippingTrace> shippingTraceList = new ArrayList<>();
                for (QueryTrackData queryTrackData : queryTrackResp.getData()) {
                    ShippingTrace trace = new ShippingTrace();
                    trace.setAcceptTime(queryTrackData.getTime());
                    trace.setAcceptStation(queryTrackData.getContext());
                    shippingTraceList.add(trace);
                }
                shippingDTO.setTraces(shippingTraceList);
                return shippingDTO.getTraces();
            }
        }
        return new ShippingDTO().getTraces();
    }
}
