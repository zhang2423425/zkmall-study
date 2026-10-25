package com.shop.zkmall.express;

import com.shop.zkmall.commons.domain.express.ShippingTrace;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface IExpressService {

    /**
     * 获取物流轨迹
     *
     * @param code   快递公司编码
     * @param formid 订单号
     * @return 物流轨迹信息
     * @throws Exception Exception
     */
    List<ShippingTrace> findTraces(String code, String formid) throws Exception;

}
