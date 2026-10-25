/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.constant;

import com.shop.zkmall.commons.utils.EmptyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 格式化返回客户端数据格式（json）
 *
 * @author zkmall zkmallvip@163.com
 */
public class CoReturnFormat {

    public static Map<String, String> messageMap = new HashMap<String, String>();


    //初始化状态码与文字说明,后期可以放入到数据库中，根据返回码，自定义返回信息
    static {

        put("00000", "success");

        put("400", "Bad Request!");
        put("401", "NotAuthorization");
        put("405", "Method Not Allowed");
        put("406", "Not Acceptable");
        put("500", "Internal Server Error");
        put("99999", "系统开小差,请稍后再试!");

        put(CoReturnFormat.SYS_ERROR, "系统异常,请联系管理员!");
        put(CoReturnFormat.SUCCESS, "成功");
        put(CoReturnFormat.REPEAT_REQUEST, "重复请求,请稍后再试");
        put(CoReturnFormat.NOT_FILE, "文件为空,请重新选择文件");
        put(CoReturnFormat.TOKEN_IS_NULL, "token为空");
        put(CoReturnFormat.TOKEN_APPROVE_ERROR, "token认证失败");
        put(CoReturnFormat.USER_NOT_LOGIN, "请先登录");
        put(CoReturnFormat.USER_LOGIN, "用户已登录");
        put(CoReturnFormat.USERNAME_OR_PHONE_HAVE, "该用户名或手机号已注册");
        put(CoReturnFormat.REFRESH_TOKEN_APPROVE_ERROR, "refreshToken认证失败");

        put(CoReturnFormat.SYS_RUNTIME_ERROR, "[服务器]运行时异常");
        put(CoReturnFormat.SYS_NULL_ERROR, "[服务器]空值异常");
        put(CoReturnFormat.SYS_CONVERT_ERROR, "[服务器]数据类型转换异常");
        put(CoReturnFormat.SYS_IO_ERROR, "[服务器]IO异常");
        put(CoReturnFormat.SYS_METHOD_UNKNOWN, "[服务器]未知方法异常");
        put(CoReturnFormat.SYS_ARRAY_OUT_INDEX, "[服务器]数组越界异常");
        put(CoReturnFormat.SYS_NET_ERROR, "[服务器]网络异常");
        put(CoReturnFormat.SYS_TOKEN_AUTH_FAILED, "[服务器]Token认证失败");
        put(CoReturnFormat.SYS_TOKEN_TIMEOUT, "[服务器] Token 失效");
        put(CoReturnFormat.SYS_TOKEN_NO_PARAM, "[服务器] Token参数缺失");
        put(CoReturnFormat.SYS_DATE_PARSE_EXCEPTION, "[服务器] 日期格式解析异常");
        put(CoReturnFormat.CODE_ERROR, "验证码有误");
        put(CoReturnFormat.CAPTCHA_ERROR, "图形验证码不正确");

        //============== 基础参数 ===============================
        put(CoReturnFormat.PARAM_MISSING, "必填参数不能为空");
        put(CoReturnFormat.PARAM_INVALID, "参数不合法");
        put(CoReturnFormat.PARAM_MISSING_ID, "参数ID缺失");
        put(CoReturnFormat.LOGIC_ERROR_DATABASE, "[逻辑错误]数据库数据异常");


        //============== 用户账户 ===============================
        put(CoReturnFormat.USER_UNREGISTER, "用户未注册");
        put(CoReturnFormat.USER_AREADY_REGISTER, "用户已注册");
        put(CoReturnFormat.USER_OR_PASSWD_ERROR, "用户名或密码错误");
        put(CoReturnFormat.USER_DELETE, "用户已删除");
        put(CoReturnFormat.SYS_HINT, "系统提示信息  ");
        put(CoReturnFormat.USER_TYPE_STOP, "账户已停用");
        put(CoReturnFormat.USER_LEAVE, "账户已离职");
        put(CoReturnFormat.START_USER, "启用状态下的员工才能停用");
        put(CoReturnFormat.STOP_USER, "停用状态下的员工才能启用");
        put(CoReturnFormat.HAVE_STOP_USER, "存在停用的账号无法进行该操作");
        put(CoReturnFormat.OLD_PASSWORD_ERROR, "原密码错误");
        put(CoReturnFormat.PHONE_ALREADY_BUSINESS, "该手机号已绑定商户,无法注册");
        put(CoReturnFormat.PHONE_ALREADY_SYS, "该手机已绑定内部账号,无法注册");
        put(CoReturnFormat.USER_CHECK_ERROR, "用户校验失败,请重试");
        put(CoReturnFormat.PASSWORD_NOT_AGREEN, "两次输入不一致");
        put(CoReturnFormat.WECHAT_ERROR, "微信授权失败,请重试");
        put(CoReturnFormat.HAVE_BLACK, "您已被拉入黑名单");
        put(CoReturnFormat.BANK_ALREADY, "银行卡已重复");
        put(CoReturnFormat.ALI_APPLET_AUTH_ERROR, "支付宝小程序授权失败");
        put(CoReturnFormat.VERIFY_PHONE_ERROR, "验证手机号失败");
        put(CoReturnFormat.OTHER_ALI_USER_BIND_PHONE, "该手机号已被其他支付宝账户绑定");
        put(CoReturnFormat.ORDER_RISK, "您的行为异常，无法提交订单");

        //================================= 权限和角色==========================
        put(CoReturnFormat.SORT_ALREADY, "排序值已存在,请修改后重试");
        put(CoReturnFormat.DICT_NAME_ALREADY, "字典名称已存在,请修改后重试");
        put(CoReturnFormat.DICT_NOT_OPERATION, "初始化字典数据无法进行该操作");
        put(CoReturnFormat.DEMON_ACCOUNT_NOT_PERMITTED, "演示账号不允许修改数据");
        put(CoReturnFormat.ALIPAY_QRCODE_INSUF_PER, "该商户尚未开通支付宝小程序二维码权限");


        //================================= 商家 ================================
        put(CoReturnFormat.SHOP_NAME_ALREADY, "商家名称已存在");
        put(CoReturnFormat.SHOP_PHONE_ALREADY, "手机号已被注册");
        put(CoReturnFormat.CLASSIFY_BOND_PRODUCT, "商品类别已绑定商品数据,无法删除");
        put(CoReturnFormat.RULE_NOT_SMALLER, "填写内容不能小于上一级内容");
        put(CoReturnFormat.SHOP_CHECK_STAY, "入驻申请审核中");
        put(CoReturnFormat.SHOP_CHECK_STOP, "入驻申请已被拒绝");
        put(CoReturnFormat.PHONE_ALREADY, "该手机号客户已存在");
        put(CoReturnFormat.CROWD_NAME_ALREADY, "该人群名称已存在");
        put(CoReturnFormat.GROUP_WORK_NOT_STOP, "拼团活动正在进行中,请勿停止");
        put(CoReturnFormat.PHONE_ALREADY_WX, "该手机号已绑定其他微信用户");
        put(CoReturnFormat.MERCHANT_NOT_EXIST, "商家不存在");
        put(CoReturnFormat.PHONE_ALREADY_BIND_SHOP, "手机号已绑定店铺");
        put(CoReturnFormat.PHONE_NOT_BANK_SHOP, "手机号与店铺绑定手机号不一致,请修改后重试");
        put(CoReturnFormat.SHOP_CHECK_TIME_OVERDUE, "不能选择以往日期,入驻时间直接过期");
        put(CoReturnFormat.LIVE_PRODUCT_EXISTS, "直播间商品已存在");
        put(CoReturnFormat.LIVE_PRODUCT_RE_EXAMINE_LIMIT, "该商品3次审核未通过，请稍后重试");
        put(CoReturnFormat.CHANNEL_COUPON_NOT_MATCHED, "创建失败，该券为专属商品券，无法匹配该商品");
        put(CoReturnFormat.LIVE_PRODUCT_NAME_VALID, "商品名称必须为3-14个汉字（一个字等于两个英文字符或特殊字符）");
        put(CoReturnFormat.LIVE_PRODUCT_PRICE_VALID, "请输入0.01-1000 0000.00元的金额");
        put(CoReturnFormat.LIVE_TITLE_VALID, "直播标题必须为3-17个字（一个字等于两个英文字符或特殊字符）");
        put(CoReturnFormat.LIVE_ANCHOR_NICKNAME_VALID, "主播昵称必须为2-15个字（一个字等于两个英文字符或特殊字符）");
        put(CoReturnFormat.LIVE_START_TIME_VALID, "直播开始时间必须在当前30分钟之后");
        put(CoReturnFormat.LIVE_RE_EXAMINE_LIMIT, "该直播间3次审核未通过，请稍后再试");
        put(CoReturnFormat.LIVE_UPDATE_FORBID, "直播开始之后不允许再修改直播间信息");
        put(CoReturnFormat.CARD_TITLE_LENGTH_VALID, "卡券标题长度必须为1到21个字数的范围内，数字/字母/汉字都算占用一个字数");
        put(CoReturnFormat.CARD_NOTICE_LENGTH_VALID, "卡券使用须知长度必须为1到15个字数的范围内，数字/字母/汉字都算占用一个字数");
        put(CoReturnFormat.LIVE_PRODUCT_NAME_IRREGULAR, "直播间商品名称违规");
        put(CoReturnFormat.LIVE_PRODUCT_PIC_IRREGULAR, "直播间商品图片违规");
        put(CoReturnFormat.LIVE_PRODUCT_MAXIMUM, "每天最多只能提审500次商品");
        put(CoReturnFormat.LIVE_PRODUCT_PIC_OVERSIZE, "直播间商品图片尺寸过大");
        put(CoReturnFormat.LIVE_PRODUCT_UNPUSH, "存在未提交到微信平台的直播间商品");
        put(CoReturnFormat.DELETE_LIVE_PRODUCT_FAIL, "删除微信直播间商品失败");
        put(CoReturnFormat.LIVE_IMPORT_PRODUCT_FAIL, "直播间导入商品失败，请检查是否所有商品都在微信平台审核通过");
        put(CoReturnFormat.LIVE_START_TIME_MAX, "开播时间不能在6个月后");
        put(CoReturnFormat.LIVE_TIME_INTERVAL, "开播时间和结束时间间隔不得短于30分钟，不得超过24小时");
        put(CoReturnFormat.CHANNEL_COUPON_REPEAT, "一张优惠券只能绑定一个商品");
        put(CoReturnFormat.GET_ANCHOR_ROLE_FAIL, "查询主播失败");
        put(CoReturnFormat.SECRET_ERROR, "秘钥错误");
        put(CoReturnFormat.UPDATE_PRODUCT_PRICE_ACTIVITY_ERROR, "更新后的价格和秒杀直降价格有冲突");
        put(CoReturnFormat.CUSTOMER_SERVICE_LIMIT, "每个商家最多添加10个微信客服");


        //================================ 营销活动 ================================
        put(CoReturnFormat.TIME_ERROR, "时间转换出现异常");
        put(CoReturnFormat.ACTIVITY_TIME_ERROR, "活动开始时间必须大于报名结束时间");
        put(CoReturnFormat.ACTIVITY_TIME_CROSS, "该时间段内已有其他同类型活动");
        put(CoReturnFormat.ACTIVITY_PRODUCT_REPEAT, "存在重复参与活动商品,请仔细排查后重试");
        put(CoReturnFormat.PARAM_NUMBER_NOT_NULL, "限制数量不能为空");
        put(CoReturnFormat.HAVE_ONE_TOOL_PRODUCT, "至少要有一个活动商品");
        put(CoReturnFormat.NOW_DATE_LESS_END_TIME, "当前时间必须小于活动结束时间");
        put(CoReturnFormat.HAVE_OTHER_SCENE, "已存在有效的会员日、生日营销活动，如需新建，请终止当前活动");
        put(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT, "存在商品已参与商家拼团活动,请仔细排查后重试");
        put(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT, "存在商品已参与商家秒杀活动,请仔细排查后重试");
        put(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT, "存在商品已参与商家限时折扣活动,请仔细排查后重试");
        put(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT, "存在商品已参与平台限时折扣活动,请仔细排查后重试");
        put(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT, "存在商品已参与平台秒杀活动,请仔细排查后重试");
        put(CoReturnFormat.COMPOSE_PRODUCT_NUMBER, "组合捆绑至少需要2件商品,最多不超过5件商品,请修改后重试");
        put(CoReturnFormat.COMPOSE_NAME_ALREADY, "组合名称已存在,请修改后重试");
        put(CoReturnFormat.SHOP_COMPOSE_PRODUCT_REPEAT, "存在商品已参与其他组合捆绑活动,请仔细排查后重试");
        put(CoReturnFormat.SHOP_PRICE_PRODUCT_REPEAT, "存在商品已参与其他定价捆绑活动,请仔细排查后重试");
        put(CoReturnFormat.PRICE_PRICE_ERROR, "捆绑价不得低于最低商品售价");
        put(CoReturnFormat.SECKILL_PRICE_ERROR, "平台秒杀直降价格高于商品最低售价,请选择其他商品");
        put(CoReturnFormat.ACTIVITY_STOCK_NOT_NULL, "活动库存不能为空");
        put(CoReturnFormat.SCENE_NOT_START, "已过期的活动不允许启动");
        put(CoReturnFormat.SCENE_ALREADY_MESSAGE, "已存在有效的生日营销活动，如需新建，请终止当前活动");
        put(CoReturnFormat.ACTIVITY_NOT_EXISTS, "活动不存在");
        put(CoReturnFormat.EDIT_BEFORE_ACTIVITY_START, "活动开始前才可以编辑");
        put(CoReturnFormat.ACTIVITY_STATE_ERROR, "活动状态异常");
        put(CoReturnFormat.ACTIVITY_START_TIME_BEFORE_NOW, "开始时间不能小于当前时间");
        put(CoReturnFormat.ACTIVITY_TIME_OVER_ONE_YEAR, "用券时间不能超过1年哦~");


        //================================快递======================================
        put(CoReturnFormat.NOT_EXPRESS_FORMID, "无法识别快递单号");
        put(CoReturnFormat.NOT_RESULT, "查询无结果，请隔段时间再查");
        put(CoReturnFormat.NOT_MATCH_LOGISTICS, "收货地址与快递不匹配");

        //================================支付====================================
        put(CoReturnFormat.BUSINESS_BALANCE_NOT, "商户可用退款余额不足");
        put(CoReturnFormat.OPENID_IS_NULL, "客户未绑定微信,无法使用微信支付");
        put(CoReturnFormat.PAY_MONEY_NOT_ZERO, "支付金额不能为0");
        put(CoReturnFormat.APPLY_REFUND_FAILED, "退款申请失败");
        put(CoReturnFormat.GENERATE_COLLECTION_CODE_FAILED, "生成收款码失败");
        put(CoReturnFormat.PAY_ORDER_ERROR, "订单预支付失败");


        //=================================商品===================================
        put(CoReturnFormat.PRODUCT_STOCK_ERROR, "商品已不可售，看看其他的吧");
        put(CoReturnFormat.HAVE_INVALID_PRODUCT, "存在无效商品，请重新选择");
        put(CoReturnFormat.SENSITIVE_ERROR, "你的内容存在敏感词，请重新编辑后再次发布");
        put(CoReturnFormat.CLASSIFY_NAME_NULL, "请输入分类名称");
        put(CoReturnFormat.PRODUCT_LIMIT_ERROR, "已超出最大限制购买数量");
        put(CoReturnFormat.PRODUCT_WITH_ACTIVITY, "商品已关联活动数据,无法删除");


        //================================分销====================================
        put(CoReturnFormat.DISTRIBUTO_NOT_RELATIONSHIP, "分销员之间不允许建立关系");
        put(CoReturnFormat.RELATION_TIME_ERROR, "保护期时间需要小于有效期");
        put(CoReturnFormat.DISTRIBUTOR_PHONE_ALREADY_ADD, "该手机号已经添加过分销员");
        put(CoReturnFormat.DISTRIBUTOR_NOT_SELF, "邀请人不能是自己");
        put(CoReturnFormat.USER_NOT_BUYER, "该用户未注册成买家");
        put(CoReturnFormat.USER_ALREADY_STOP, "该用户已停用");
        put(CoReturnFormat.USER_ALREADY_BLACK, "该用户已被拉入黑名单");
        put(CoReturnFormat.NOT_HAVE_DISTRIBUTOR_LEVEL, "未设置分销员等级");
        put(CoReturnFormat.NOT_ROBBING, "当前关系设置不允许抢客");
        put(CoReturnFormat.PHONE_NOT_DISTRIBUTOR, "手机号已申请过分销员");
        put(CoReturnFormat.LEVEL_EXISTS, "已存在相同编号的分销员等级");
        put(CoReturnFormat.DISTRIBUTOR_ERROR, "申请失败");


        //================================文件上传====================================
        put(CoReturnFormat.MP4_FILE_NOT_2M, "视频文件不能超过2M");

        //================================= 关键词==========================
        put(CoReturnFormat.KEY_WORD_ALREADY, "关键词已存在");

        //================================= 售后 ==========================
        put(CoReturnFormat.AFTER_NOT_RETURN, "该售后单状态已变更,无法进行该操作");

        //================================= 订单 ==========================
        put(CoReturnFormat.ORDER_NOT_TAKE, "该订单状态已变更,无法进行该操作");
        put(CoReturnFormat.ALREADY_COMMENTED, "您已评价过该商品不能再次评价");

        //================================= 财务 ==========================
        put(CoReturnFormat.BALANCE_NOT_ENOUGH, "可提现余额不足,无法提现");


        //================================= 营销活动订单 ==========================
        put(CoReturnFormat.COLLAGE_ALREADY_SUCCESS, "该拼单已成团,订单已自动取消");
        put(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT, "活动商品已售罄,可以选择其他商品抢购");


        //================================ 营销活动 ==================================
        put(CoReturnFormat.COUPON_RECEIVE_FINISH, "优惠券已领完");
        put(CoReturnFormat.HAVE_STAY_OPERATE, "存在进行中的运营计划,无法删除");
        put(CoReturnFormat.COUPON_TAKE_UPPER_LIMIT, "优惠券领取已达最大限制次数,无法继续领取");
        put(CoReturnFormat.COUPON_ALREADY_HAVE, "该优惠券已领取,请勿重复领取");
        put(CoReturnFormat.INVALID_COMPOSE, "该组合捆绑不存在或已失效");
        put(CoReturnFormat.COUPON_EXPIRED, "优惠券已过期");
        put(CoReturnFormat.PLATFORM_COUPON_EXPIRED, "平台优惠券已过期");
        put(CoReturnFormat.SHOP_COUPON_EXPIRED, "商家优惠券已过期");
        put(CoReturnFormat.SHOP_COUPON_ABNORMAL, "商家优惠券状态异常");
        put(CoReturnFormat.CHANNEL_COUPON_SOLD_OUT_OR_END, "优惠券已领完或活动已结束");


        //================================ 平台 ==================================
        put(CoReturnFormat.CANVAS_ALREADY, "该类型画布已存在,请修改");
        put(CoReturnFormat.SYNC_MENU_ING, "后台已有菜单在同步中，请稍后再试");
        put(CoReturnFormat.MEMBER_SHIP_NOT_DELETE, "该会员权益已经关联会员等级,请先删除对应会员等级");
        put(CoReturnFormat.MEMBER_LEVEL_NOT_DELETE, "该会员等级有关联未结束的场景营销活动,无法删除");
        put(CoReturnFormat.MEMBER_LEVEL_MUST_ONE, "默认必须有一条会员等级数据,无法删除");
        put(CoReturnFormat.EXIST_MEMBER_LEVEL, "已存在相同的会员等级名称或成长值");
        put(CoReturnFormat.NEED_ZERO_GROWTH, "至少需要一个成长值为0的会员等级");
        put(CoReturnFormat.CREDIT_SETTING_EXISTS, "已存在该天的积分配置");
        put(CoReturnFormat.POPUP_ADVERT_CONFLICT, "该时间段已有上架的广告");
        put(CoReturnFormat.CREDIT_NOT_ENOUGH, "积分不足");
        put(CoReturnFormat.ACTIVITY_CREATE_CARD_ERROR, "同步卡券失败");
        put(CoReturnFormat.LIVE_CREATE_ROOM_FAIL, "同步创建微信直播间失败");
        put(CoReturnFormat.LIVE_ADD_GOODS_FAIL, "同步创建微信直播间商品失败");
        put(CoReturnFormat.RISK_BLACK_IP_EXISTS, "ip已存在");
        put(CoReturnFormat.AUDIT_REMARK_LENGTH_INVALID, "审核备注不能超过32个字符");
        put(CoReturnFormat.SEND_CODE_ERR, "验证码超过获取次数，请稍后重试");
        put(CoReturnFormat.ADMIN_PHONE_NOT_SET, "没有配置管理员手机号");
        put(CoReturnFormat.ROLE_BIND_USER, "该角色已绑定用户无法删除");
        //================================ 种草 ==================================
        put(CoReturnFormat.RECOMMEND_TYPE_REPEAT, "内容分类名称不可重复！");
    }

    /**
     * 获取业务返回码信息
     */
    public static String getMessage(String code) {
//    	String message = "业务编码未定义: " + code;
        String message = code;
        String obj = messageMap.get(code);
        return obj == null ? message : obj;
    }

    /**
     * 返回码放入map中
     */
    public static void putAll(Map<String, String> m) {
        messageMap.putAll(m);
    }

    /**
     * 放入到map中
     */
    public static void put(String key, String value) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(value)) {
            return;
        }
        messageMap.put(key, value);
    }


    //===================================================
    /**
     * 成功
     */
    public static final String SUCCESS = "200";
    /**
     * 重复请求,请稍后再试
     */
    public static final String REPEAT_REQUEST = "00001";
    /**
     * 文件为空,请重新选择文件
     */
    public static final String NOT_FILE = "00002";
    /**
     * token为空
     */
    public static final String TOKEN_IS_NULL = "20003";
    /**
     * token认证失败
     */
    public static final String TOKEN_APPROVE_ERROR = "20004";
    /**
     * 请先登录
     */
    public static final String USER_NOT_LOGIN = "20005";
    /**
     * 用户已登录
     */
    public static final String USER_LOGIN = "20006";
    /**
     * 该用户名或手机号已注册
     */
    public static final String USERNAME_OR_PHONE_HAVE = "20007";
    /**
     * refreshToken认证失败
     */
    public static final String REFRESH_TOKEN_APPROVE_ERROR = "20008";


    //===================================================
    /**
     * 系统异常,请联系管理员!
     */
    public static final String SYS_ERROR = "99999";
    /**
     * 系统提示信息
     */
    public static final String SYS_HINT = "8888";
    /**
     * [服务器]运行时异常
     */
    public static final String SYS_RUNTIME_ERROR = "10000";
    /**
     * [服务器]空值异常
     */
    public static final String SYS_NULL_ERROR = "10001";
    /**
     * [服务器]数据类型转换异常
     */
    public static final String SYS_CONVERT_ERROR = "10002";
    /**
     * [服务器]IO异常
     */
    public static final String SYS_IO_ERROR = "10003";
    /**
     * [服务器]未知方法异常
     */
    public static final String SYS_METHOD_UNKNOWN = "10004";
    /**
     * [服务器]数组越界异常
     */
    public static final String SYS_ARRAY_OUT_INDEX = "10005";
    /**
     * [服务器]网络异常
     */
    public static final String SYS_NET_ERROR = "10006";
    /**
     * [服务器] Token认证失败
     */
    public static final String SYS_TOKEN_AUTH_FAILED = "10007";
    /**
     * [服务器] Token 失效
     */
    public static final String SYS_TOKEN_TIMEOUT = "10008";
    /**
     * [服务器] Token参数缺失
     */
    public static final String SYS_TOKEN_NO_PARAM = "10009";

    public static final String SYS_DATE_PARSE_EXCEPTION = "10010";
    /**
     * 必填参数不能为空
     */
    public static final String PARAM_MISSING = "11001";
    /**
     * 参数不合法
     */
    public static final String PARAM_INVALID = "11002";
    /**
     * 缺少参数Id
     */
    public static final String PARAM_MISSING_ID = "11003";
    /**
     * 数据库查询数据逻辑异常
     */
    public static final String LOGIC_ERROR_DATABASE = "11004";
    /**
     * 验证码有误
     */
    public static final String CODE_ERROR = "11005";
    /**
     * 原密码错误
     */
    public static final String OLD_PASSWORD_ERROR = "11006";
    /**
     * 图形验证码不正确
     */
    public static final String CAPTCHA_ERROR = "11007";

    //============ 登录 ===============
    /**
     * 用户未注册
     */
    public static final String USER_UNREGISTER = "21001";
    /**
     * 用户已注册
     */
    public static final String USER_AREADY_REGISTER = "21002";
    /**
     * 用户名或者密码错误
     */
    public static final String USER_OR_PASSWD_ERROR = "21003";
    /**
     * 用户状已删除
     */
    public static final String USER_DELETE = "21004";
    /**
     * 账户已停用
     */
    public static final String USER_TYPE_STOP = "21005";
    /**
     * 账户已离职
     */
    public static final String USER_LEAVE = "21006";
    /**
     * 启用状态下的员工才能停用
     */
    public static final String START_USER = "21007";
    /**
     * 停用状态下的员工才能启用
     */
    public static final String STOP_USER = "21008";
    /**
     * 存在停用的账号无法进行该操作
     */
    public static final String HAVE_STOP_USER = "21009";
    /**
     * 该手机号已绑定商户,无法注册
     */
    public static final String PHONE_ALREADY_BUSINESS = "21010";
    /**
     * 该手机已绑定内部账号,无法注册
     */
    public static final String PHONE_ALREADY_SYS = "21011";
    /**
     * 用户校验失败,请重试
     */
    public static final String USER_CHECK_ERROR = "21012";
    /**
     * 两次输入不一致
     */
    public static final String PASSWORD_NOT_AGREEN = "21013";
    /**
     * 微信授权失败,请重试
     */
    public static final String WECHAT_ERROR = "21014";
    /**
     * 您已被拉入黑名单
     */
    public static final String HAVE_BLACK = "21015";
    /**
     * 银行卡已重复
     */
    public static final String BANK_ALREADY = "21016";
    /**
     * 支付宝小程序授权失败
     */
    public static final String ALI_APPLET_AUTH_ERROR = "21017";
    /**
     * 验证手机号失败
     */
    public static final String VERIFY_PHONE_ERROR = "21018";
    /**
     * 该手机号已被其他支付宝账号绑定
     */
    public static final String OTHER_ALI_USER_BIND_PHONE = "21019";
    /**
     * 您的行为异常，无法提交订单
     */
    public static final String ORDER_RISK = "21020";


    //================================= 权限和角色==========================
    /**
     * 排序值已存在,请修改后重试
     */
    @Deprecated
    public static final String SORT_ALREADY = "30001";
    /**
     * 字典名称已存在,请修改后重试
     */
    public static final String DICT_NAME_ALREADY = "30002";
    /**
     * 初始化字典数据无法进行该操作
     */
    public static final String DICT_NOT_OPERATION = "30003";
    /**
     * 演示账号不允许修改数据
     */
    public static final String DEMON_ACCOUNT_NOT_PERMITTED = "30004";
    /**
     * 该商户尚未开通支付宝小程序二维码权限
     */
    public static final String ALIPAY_QRCODE_INSUF_PER = "30005";


    //================================= 商家 ================================
    /**
     * 商家名称已存在
     */
    public static final String SHOP_NAME_ALREADY = "40001";
    /**
     * 手机号已被注册
     */
    public static final String SHOP_PHONE_ALREADY = "40002";
    /**
     * 商品类别已绑定商品数据,无法删除
     */
    public static final String CLASSIFY_BOND_PRODUCT = "40003";
    /**
     * 填写内容不能小于上一级内容
     */
    public static final String RULE_NOT_SMALLER = "40004";
    /**
     * 入驻申请审核中
     */
    public static final String SHOP_CHECK_STAY = "40005";
    /**
     * 入驻申请已被拒绝
     */
    public static final String SHOP_CHECK_STOP = "40006";
    /**
     * 该手机号客户已存在
     */
    public static final String PHONE_ALREADY = "40007";
    /**
     * 该人群名称已存在
     */
    public static final String CROWD_NAME_ALREADY = "40008";
    /**
     * 拼团活动正在进行中,请勿停止
     */
    public static final String GROUP_WORK_NOT_STOP = "40009";
    /**
     * 该手机号已绑定其他微信用户
     */
    public static final String PHONE_ALREADY_WX = "40010";
    /**
     * 商家不存在
     */
    public static final String MERCHANT_NOT_EXIST = "40011";
    /**
     * 手机号已绑定店铺
     */
    public static final String PHONE_ALREADY_BIND_SHOP = "40012";
    /**
     * 手机号与店铺绑定手机号不一致,请修改后重试
     */
    public static final String PHONE_NOT_BANK_SHOP = "40013";
    /**
     * 不能选择以往日期,入驻时间直接过期
     */
    public static final String SHOP_CHECK_TIME_OVERDUE = "40014";
    /**
     * 直播间商品已存在
     */
    public static final String LIVE_PRODUCT_EXISTS = "40015";
    /**
     * 直播间商品一天最多只能重复审核3次
     */
    public static final String LIVE_PRODUCT_RE_EXAMINE_LIMIT = "40016";
    /**
     * 创建失败，该券为专属商品券，无法匹配该商品
     */
    public static final String CHANNEL_COUPON_NOT_MATCHED = "40017";
    /**
     * 商品名称必须为3-14个汉字（一个字等于两个英文字符或特殊字符）
     */
    public static final String LIVE_PRODUCT_NAME_VALID = "40018";
    /**
     * 请输入0.01-1000 0000.00元的金额
     */
    public static final String LIVE_PRODUCT_PRICE_VALID = "40019";
    /**
     * 直播标题必须为3-17个字（一个字等于两个英文字符或特殊字符）
     */
    public static final String LIVE_TITLE_VALID = "40020";
    /**
     * 主播昵称必须为2-15个字（一个字等于两个英文字符或特殊字符）
     */
    public static final String LIVE_ANCHOR_NICKNAME_VALID = "40021";
    /**
     * 直播开始时间必须在当前时间30分钟之后
     */
    public static final String LIVE_START_TIME_VALID = "40022";
    /**
     * 直播间一天最多只能重复审核3次
     */
    public static final String LIVE_RE_EXAMINE_LIMIT = "40023";
    /**
     * 直播开始之后不允许再修改直播间信息
     */
    public static final String LIVE_UPDATE_FORBID = "40024";
    /**
     * 卡券标题长度必须为1到21个字数的范围内，数字/字母/汉字都算占用一个字数
     */
    public static final String CARD_TITLE_LENGTH_VALID = "40025";
    /**
     * 卡券使用须知长度必须为1到15个字数的范围内，数字/字母/汉字都算占用一个字数
     */
    public static final String CARD_NOTICE_LENGTH_VALID = "40026";
    /**
     * 直播间商品名称违规
     */
    public static final String LIVE_PRODUCT_NAME_IRREGULAR = "40027";
    /**
     * 直播间商品图片违规
     */
    public static final String LIVE_PRODUCT_PIC_IRREGULAR = "40028";
    /**
     * 每天只能提审500次商品
     */
    public static final String LIVE_PRODUCT_MAXIMUM = "40029";
    /**
     * 直播间商品图片尺寸过大
     */
    public static final String LIVE_PRODUCT_PIC_OVERSIZE = "40030";
    /**
     * 存在未提交到微信平台的直播间商品
     */
    public static final String LIVE_PRODUCT_UNPUSH = "40031";
    /**
     * 删除微信直播间商品失败
     */
    public static final String DELETE_LIVE_PRODUCT_FAIL = "40032";
    /**
     * 直播间导入商品失败，请检查是否所有商品都在微信平台审核通过
     */
    public static final String LIVE_IMPORT_PRODUCT_FAIL = "40033";
    /**
     * 开播时间不能在6个月后
     */
    public static final String LIVE_START_TIME_MAX = "40034";
    /**
     * 开播时间和结束时间间隔不得短于30分钟，不得超过24小时
     */
    public static final String LIVE_TIME_INTERVAL = "40035";
    /**
     * 一张优惠券只能绑定一个商品
     */
    public static final String CHANNEL_COUPON_REPEAT = "40036";
    /**
     * 查询主播失败
     */
    public static final String GET_ANCHOR_ROLE_FAIL = "40037";
    /**
     * 秘钥错误
     */
    public static final String SECRET_ERROR = "40038";
    /**
     * 更新后的价格和秒杀直降价格有冲突
     */
    public static final String UPDATE_PRODUCT_PRICE_ACTIVITY_ERROR = "40039";
    /**
     * 每个商家最多添加10个微信客服
     */
    public static final String CUSTOMER_SERVICE_LIMIT = "40040";

    //================================= 营销活动 ===============================
    /**
     * 时间转换出现异常
     */
    public static final String TIME_ERROR = "50000";
    /**
     * 活动开始时间必须大于报名结束时间
     */
    public static final String ACTIVITY_TIME_ERROR = "50001";
    /**
     * 活动起始时间存在交叉,请修改后重试
     */
    public static final String ACTIVITY_TIME_CROSS = "50002";
    /**
     * 存在重复参与活动商品,请仔细排查后重试
     */
    public static final String ACTIVITY_PRODUCT_REPEAT = "50003";
    /**
     * 限制数量不能为空
     */
    public static final String PARAM_NUMBER_NOT_NULL = "50004";
    /**
     * 至少要有一个活动商品
     */
    public static final String HAVE_ONE_TOOL_PRODUCT = "50005";
    /**
     * 当前时间必须小于活动结束时间
     */
    public static final String NOW_DATE_LESS_END_TIME = "50006";
    /**
     * 已存在有效的会员日、生日营销活动，如需新建，请终止当前活动
     */
    public static final String HAVE_OTHER_SCENE = "50007";
    /**
     * 存在商品已参与商家拼团活动,请仔细排查后重试
     */
    public static final String GROUP_WORK_PRODUCT_REPEAT = "50008";
    /**
     * 存在商品已参与商家秒杀活动,请仔细排查后重试
     */
    public static final String SHOP_SECKILL_PRODUCT_REPEAT = "50009";
    /**
     * 存在商品已参与商家限时折扣活动,请仔细排查后重试
     */
    public static final String SHOP_DISCOUNT_PRODUCT_REPEAT = "50010";
    /**
     * 存在商品已参与平台限时折扣活动,请仔细排查后重试
     */
    public static final String PLATFORM_DISCOUNT_PRODUCT_REPEAT = "50011";
    /**
     * 存在商品已参与平台秒杀活动,请仔细排查后重试
     */
    public static final String PLATFORM_SECKILL_PRODUCT_REPEAT = "50012";
    /**
     * 组合捆绑至少需要2件商品,最多不超过5件商品,请修改后重试
     */
    public static final String COMPOSE_PRODUCT_NUMBER = "50013";
    /**
     * 组合名称已存在,请修改后重试
     */
    public static final String COMPOSE_NAME_ALREADY = "50014";
    /**
     * 存在商品已参与其他组合捆绑活动,请仔细排查后重试
     */
    public static final String SHOP_COMPOSE_PRODUCT_REPEAT = "50015";
    /**
     * 存在商品已参与其他定价捆绑活动,请仔细排查后重试
     */
    public static final String SHOP_PRICE_PRODUCT_REPEAT = "50016";
    /**
     * 捆绑价不得低于最低商品售价
     */
    public static final String PRICE_PRICE_ERROR = "50017";
    /**
     * 平台秒杀直降价格高于商品最低售价,请选择其他商品
     */
    public static final String SECKILL_PRICE_ERROR = "50018";
    /**
     * 活动库存不能为空
     */
    public static final String ACTIVITY_STOCK_NOT_NULL = "50019";
    /**
     * 已过期的活动不允许启动
     */
    public static final String SCENE_NOT_START = "50020";
    /**
     * 已存在有效的生日营销活动，如需新建，请终止当前活动
     */
    public static final String SCENE_ALREADY_MESSAGE = "50021";
    /**
     * 活动不存在
     */
    public static final String ACTIVITY_NOT_EXISTS = "50022";
    /**
     * 活动开始之前才可以编辑
     */
    public static final String EDIT_BEFORE_ACTIVITY_START = "50023";
    /**
     * 活动状态异常
     */
    public static final String ACTIVITY_STATE_ERROR = "50024";
    /**
     * 开始时间不能小于当前时间
     */
    public static final String ACTIVITY_START_TIME_BEFORE_NOW = "50025";
    /**
     * 用券时间不能超过1年
     */
    public static final String ACTIVITY_TIME_OVER_ONE_YEAR = "50026";


    //================================ 快递 ==================================
    /**
     * 无法识别快递单号
     */
    public static final String NOT_EXPRESS_FORMID = "60001";
    /**
     * 查询无结果，请隔段时间再查
     */
    public static final String NOT_RESULT = "60002";
    /**
     * 收货地址与快递不匹配
     */
    public static final String NOT_MATCH_LOGISTICS = "60003";

    //================================支付====================================
    /**
     * 商户可用退款余额不足
     */
    public static final String BUSINESS_BALANCE_NOT = "70001";
    /**
     * 客户未绑定微信,无法使用微信支付
     */
    public static final String OPENID_IS_NULL = "70002";
    /**
     * 支付金额不能为0
     */
    public static final String PAY_MONEY_NOT_ZERO = "70003";
    /**
     * 退款申请失败
     */
    public static final String APPLY_REFUND_FAILED = "70004";
    /**
     * 生成收款码失败
     */
    public static final String GENERATE_COLLECTION_CODE_FAILED = "70005";
    /**
     * 订单预支付失败
     */
    public static final String PAY_ORDER_ERROR = "70006";


    //================================商品====================================
    /**
     * 商品已不可售，看看其他的吧
     */
    public static final String PRODUCT_STOCK_ERROR = "80001";
    /**
     * 存在无效商品，请重新选择
     */
    public static final String HAVE_INVALID_PRODUCT = "80002";
    /**
     * 你的内容存在敏感词，请重新编辑后再次发布
     */
    public static final String SENSITIVE_ERROR = "80003";
    /**
     * 请输入分类名称
     */
    public static final String CLASSIFY_NAME_NULL = "80004";
    /**
     * 已超出最大限制购买数量
     */
    public static final String PRODUCT_LIMIT_ERROR = "80005";
    /**
     * 商品已关联活动数据,无法删除
     */
    public static final String PRODUCT_WITH_ACTIVITY = "80006";

    //================================分销====================================
    /**
     * 分销员之间不允许建立关系
     */
    public static final String DISTRIBUTO_NOT_RELATIONSHIP = "90001";
    /**
     * 保护期时间需要小于有效期
     */
    public static final String RELATION_TIME_ERROR = "90002";
    /**
     * 该手机号已经添加过分销员
     */
    public static final String DISTRIBUTOR_PHONE_ALREADY_ADD = "90003";
    /**
     * 邀请人不能是自己
     */
    public static final String DISTRIBUTOR_NOT_SELF = "90004";
    /**
     * 该用户未注册成买家
     */
    public static final String USER_NOT_BUYER = "90005";
    /**
     * 该用户已停用
     */
    public static final String USER_ALREADY_STOP = "90006";
    /**
     * 该用户已被拉入黑名单
     */
    public static final String USER_ALREADY_BLACK = "90007";
    /**
     * 未设置分销员等级
     */
    public static final String NOT_HAVE_DISTRIBUTOR_LEVEL = "90008";
    /**
     * 当前关系设置不允许抢客
     */
    public static final String NOT_ROBBING = "90009";
    /**
     * 手机号已申请过分销员
     */
    public static final String PHONE_NOT_DISTRIBUTOR = "90010";
    /**
     * 已存在相同编号的分销员等级
     */
    public static final String LEVEL_EXISTS = "90011";
    /**
     * 申请失败
     */
    public static final String DISTRIBUTOR_ERROR = "90012";


    //================================ 文件上传 ====================================
    /**
     * 视频文件不能超过2M
     */
    public static final String MP4_FILE_NOT_2M = "100001";

    //================================= 关键词 ==========================
    /**
     * 关键词已存在
     */
    public static final String KEY_WORD_ALREADY = "200001";

    //================================= 售后 ==========================
    /**
     * 该售后单状态已变更,无法进行该操作
     */
    public static final String AFTER_NOT_RETURN = "300001";

    //================================= 订单 ==========================
    /**
     * 该订单状态已变更,无法进行该操作
     */
    public static final String ORDER_NOT_TAKE = "400001";
    /**
     * 您已评价过该商品，不能再次进行评价
     */
    public static final String ALREADY_COMMENTED = "400002";

    //================================= 财务 ==========================
    /**
     * 可提现余额不足,无法提现
     */
    public static final String BALANCE_NOT_ENOUGH = "500001";

    //================================= 营销活动订单 ==========================
    /**
     * 该拼单已成团,订单已自动取消
     */
    public static final String COLLAGE_ALREADY_SUCCESS = "600001";
    /**
     * 活动商品已售罄,可以选择其他商品抢购
     */
    public static final String PRODUCT_ALREADY_SELL_OUT = "600002";

    //================================ 营销活动 ==================================
    /**
     * 优惠券已领完
     */
    public static final String COUPON_RECEIVE_FINISH = "700001";
    /**
     * 存在进行中的运营计划,无法删除
     */
    public static final String HAVE_STAY_OPERATE = "700002";
    /**
     * 优惠券领取已达最大限制次数,无法继续领取
     */
    public static final String COUPON_TAKE_UPPER_LIMIT = "700003";
    /**
     * 该优惠券已领取,请勿重复领取
     */
    public static final String COUPON_ALREADY_HAVE = "700004";
    /**
     * 组合捆绑非法
     */
    public static final String INVALID_COMPOSE = "700005";
    /**
     * 优惠券已过期
     */
    public static final String COUPON_EXPIRED = "700006";
    /**
     * 平台优惠券活动已过期
     */
    public static final String PLATFORM_COUPON_EXPIRED = "700007";
    /**
     * 商家优惠券活动已过期
     */
    public static final String SHOP_COUPON_EXPIRED = "700008";
    /**
     * 商家优惠券状态异常
     */
    public static final String SHOP_COUPON_ABNORMAL = "700009";
    /**
     * 优惠券已领完或活动已结束
     */
    public static final String CHANNEL_COUPON_SOLD_OUT_OR_END = "700010";

    //================================ 平台 ==================================
    /**
     * 该类型画布已存在,请修改
     */
    public static final String CANVAS_ALREADY = "800001";
    /**
     * 后台已有菜单在同步中请稍后再试
     */
    public static final String SYNC_MENU_ING = "800002";
    /**
     * 该会员权益已经关联会员等级,请先删除对应会员等级
     */
    public static final String MEMBER_SHIP_NOT_DELETE = "800003";
    /**
     * 该会员等级有关联未结束的场景营销活动,无法删除
     */
    public static final String MEMBER_LEVEL_NOT_DELETE = "800004";
    /**
     * 默认必须有一条会员等级数据,无法删除
     */
    public static final String MEMBER_LEVEL_MUST_ONE = "800005";
    /**
     * 已存在相同的会员等级名称或成长值
     */
    public static final String EXIST_MEMBER_LEVEL = "800006";
    /**
     * 至少需要一个成长值为0的会员等级
     */
    public static final String NEED_ZERO_GROWTH = "800007";
    /**
     * 已存在该天的积分配置
     */
    public static final String CREDIT_SETTING_EXISTS = "800009";
    /**
     * 该时间段已有上架的广告
     */
    public static final String POPUP_ADVERT_CONFLICT = "800010";
    /**
     * 积分不足
     */
    public static final String CREDIT_NOT_ENOUGH = "800011";
    /**
     * 同步卡券失败
     */
    public static final String ACTIVITY_CREATE_CARD_ERROR = "800012";
    /**
     * 同步创建微信直播间失败
     */
    public static final String LIVE_CREATE_ROOM_FAIL = "800013";
    /**
     * 同步创建微信直播间商品失败
     */
    public static final String LIVE_ADD_GOODS_FAIL = "800014";
    /**
     * ip已存在
     */
    public static final String RISK_BLACK_IP_EXISTS = "800015";
    /**
     * 审核备注不能超过32个字符
     */
    public static final String AUDIT_REMARK_LENGTH_INVALID = "800016";
    /**
     * 验证码超过获取次数，请稍后重试
     */
    public static final String SEND_CODE_ERR = "800017";
    /**
     * 没有配置管理员手机号
     */
    public static final String ADMIN_PHONE_NOT_SET = "800018";
    /**
     * 该角色已绑定用户无法删除
     */
    public static final String ROLE_BIND_USER = "800019";

    /**
     * 种草类型不可重复
     */
    public static final String RECOMMEND_TYPE_REPEAT = "900001";

}
