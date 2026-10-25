/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenAppQrcodeCreateModel;
import com.alipay.api.request.AlipayOpenAppQrcodeCreateRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOpenAppQrcodeCreateResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlipayUtil {

    private final AlipayClient alipayClient;

    public AlipayUserInfoShareResponse getBuyerId(String authCode) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            String accessToken = oauthTokenResponse.getAccessToken();
            AlipayUserInfoShareRequest infoShareRequest = new AlipayUserInfoShareRequest();
            //AlipayUserInfoShareResponse response = new AlipayUserInfoShareResponse();
            //response.setUserId(oauthTokenResponse.getUserId());
            //return response;
            return alipayClient.execute(infoShareRequest, accessToken);
        } catch (AlipayApiException e) {
            //处理异常
            log.error("getBuyerId failed: auth_code = {}", authCode, e);
        }
        return null;
    }

    public String generateQrCode(String url, String queryParam) {
        AlipayOpenAppQrcodeCreateRequest request = new AlipayOpenAppQrcodeCreateRequest();
        AlipayOpenAppQrcodeCreateModel model = new AlipayOpenAppQrcodeCreateModel();
        model.setUrlParam(url);
        model.setQueryParam(queryParam);
        model.setDescribe("支付宝小程序分享");
        request.setBizModel(model);
        try {
            AlipayOpenAppQrcodeCreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getQrCodeUrl();
            } else if ("isv.insufficient-isv-permissions".equals(response.getSubCode())) {
                throw new CoBusinessException(CoReturnFormat.ALIPAY_QRCODE_INSUF_PER);
            } else {
                throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
            }
        } catch (AlipayApiException e) {
            //处理异常
            log.error("generateQrCode failed: url = {}, queryParam = {}", url, queryParam, e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
    }

}
