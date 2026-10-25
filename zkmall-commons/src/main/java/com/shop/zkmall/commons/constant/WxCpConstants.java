/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.constant;

/**
 * 企业微信相关接口
 *
 * @author zkmall zkmallvip@163.com
 */
public class WxCpConstants {

    //public static final String QY_API = "https://qyapi.weixin.qq.com";
    public static final String QY_API = "https://ceres.zkthink.com/qyapi";

    public static final String API_KF_LIST = QY_API + "/cgi-bin/kf/account/list";

    public static final String API_KF_ADD = QY_API + "/cgi-bin/kf/account/add";

    public static final String API_KF_UPDATE = QY_API + "/cgi-bin/kf/account/update";

    public static final String API_KF_DEL = QY_API + "/cgi-bin/kf/account/del";

    public static final String API_KF_SERVICER_LIST = QY_API + "/cgi-bin/kf/servicer/list";

    //public static final String API_USER_BATCH_GET = QY_API + "/cgi-bin/kf/customer/batchget?";

    public static final String API_KF_SERVICER_ADD = QY_API + "/cgi-bin/kf/servicer/add";

    public static final String API_KF_SERVICER_DEL = QY_API + "/cgi-bin/kf/servicer/del";

    public static final String API_KF_URL = "https://work.weixin.qq.com/kf/";

    public static final String API_ADD_CONTACT_WAY = QY_API + "/cgi-bin/kf/add_contact_way";

    public static final String SUITE_TICKET_KEY = "kf_suite_ticket";

    public static final String AUTH_PAGE_PREFIX = "https://work.weixin.qq.com/kf/third/auth/page";

    public static final String GET_IP_LIST = QY_API + "/cgi-bin/get_api_domain_ip";

    public static final String MSG_CURSOR = "kf_msg_cursor";

}
