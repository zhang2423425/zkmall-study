/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.express.kuaidiniao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.commons.domain.express.ShippingTrace;
import com.shop.zkmall.commons.utils.KdniaoTrackQueryAPI;
import com.shop.zkmall.express.IExpressService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@RequiredArgsConstructor
public class KuaiDiNiaoServiceImpl implements IExpressService {

    private final KuaiDiNiaoProperties kuaiDiNiaoProperties;

    @Override
    public List<ShippingTrace> findTraces(String code, String formid) throws Exception {
        String requestData = "{'OrderCode':'','ShipperCode':'" + code + "','LogisticCode':'" + formid + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KdniaoTrackQueryAPI.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", kuaiDiNiaoProperties.getEbusinessId());
        params.put("RequestType", "1002");
        String dataSign = KdniaoTrackQueryAPI.encrypt(requestData, kuaiDiNiaoProperties.getAppKey(), "UTF-8");
        params.put("DataSign", KdniaoTrackQueryAPI.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        String result = KdniaoTrackQueryAPI.sendPost(kuaiDiNiaoProperties.getQueryUrl(), params);
        JSONObject obj = JSON.parseObject(result);
        if (obj != null) {
            List<ShippingTrace> shippingTraceList = new ArrayList<>();
            JSONArray array = obj.getJSONArray("Traces");
            if (array != null && array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    ShippingTrace trace = new ShippingTrace();
                    trace.setAcceptTime(array.getJSONObject(i).getString("AcceptTime"));
                    trace.setAcceptStation(array.getJSONObject(i).getString("AcceptStation"));
                    shippingTraceList.add(trace);
                }
            }
            return shippingTraceList;
        }
        return null;
    }

}
