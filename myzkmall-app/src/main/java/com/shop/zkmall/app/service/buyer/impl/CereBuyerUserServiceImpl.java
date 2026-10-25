/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.buyer.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shop.zkmall.app.dao.buyer.CereBuyerUserDAO;
import com.shop.zkmall.app.dao.order.CereShopOrderDAO;
import com.shop.zkmall.app.page.buyer.MyUser;
import com.shop.zkmall.app.page.login.BuyerUser;
import com.shop.zkmall.app.page.login.TokenInfoBo;
import com.shop.zkmall.app.page.order.OrderCountDTO;
import com.shop.zkmall.app.page.order.Orders;
import com.shop.zkmall.app.param.buyer.UserParam;
import com.shop.zkmall.app.param.index.LoginParam;
import com.shop.zkmall.app.param.index.LoginPhoneParam;
import com.shop.zkmall.app.param.index.UpdateAliPhoneParam;
import com.shop.zkmall.app.param.index.UpdateWxPhoneParam;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.app.service.buyer.*;
import com.shop.zkmall.app.service.cart.CereShopCartService;
import com.shop.zkmall.app.service.collect.CereBuyerCollectService;
import com.shop.zkmall.app.service.collect.CereBuyerFootprintService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.app.service.shop.CereShopVisitService;
import com.shop.zkmall.app.utils.AlipayUtil;
import com.shop.zkmall.app.utils.WechatUtil;
import com.shop.zkmall.commons.config.AlipayConfig;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBuyerUserServiceImpl implements CereBuyerUserService {

    private final CereBuyerUserDAO cereBuyerUserDAO;

    private final CereBuyerCollectService cereBuyerCollectService;

    private final CereBuyerCommentLikeService cereBuyerCommentLikeService;

    private final CereBuyerFootprintService cereBuyerFootprintService;

    private final CereBuyerLabelService cereBuyerLabelService;

    private final CereBuyerReceiveService cereBuyerReceiveService;

    private final CereBuyerSearchService cereBuyerSearchService;

    private final CereNoticeService cereNoticeService;

    private final CereShopCommentService cereShopCommentService;

    private final CereShopVisitService cereShopVisitService;

    private final CereShopCartService cereShopCartService;

    private final UserRedisService userRedisService;

    private final CereShopOrderDAO cereShopOrderDAO;

    private final CereOrderAfterService cereOrderAfterService;

    private final AlipayConfig alipayConfig;

    private final AlipayUtil alipayUtil;

    @Value("${defaultHeadImg}")
    private String defaultHeadImg;

    @Override
    public CereBuyerUser findByToken(String token) {
        Long buyerUserId = userRedisService.getBuyerUserIdByToken(token);
        if (buyerUserId != null) {
            CereBuyerUser user = cereBuyerUserDAO.selectByPrimaryKey(buyerUserId);
            user.setToken(token);
            return user;
        }
        return null;
    }

    @Override
    public CereBuyerUser selectByBuyerUserId(Long buyerUserId) {
        CereBuyerUser cereBuyerUser = cereBuyerUserDAO.selectByPrimaryKey(buyerUserId);
        if (cereBuyerUser != null && cereBuyerUser.getWechatName() == null) {
            cereBuyerUser.setWechatName(cereBuyerUser.getName());
        }
        return cereBuyerUser;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser wxLogin(LoginParam param) {
        BuyerUser user;
        //根据微信code获取unionId
        //log.info("code: " + com.shop.zkmall.admin.param.getCode());
        JSONObject wx = WechatUtil.getOpenId(param.getCode());
        String openid = wx.get("openid").toString();
        //查询该微信号是否已注册
        if (!EmptyUtils.isEmpty(openid)) {
            user = cereBuyerUserDAO.findByOpenid(openid);
            if (user == null) {
                //未注册
                user = new BuyerUser();
            } else {
                if (IntegerEnum.YES.getCode().equals(user.getIfBlack())) {
                    throw new CoBusinessException(CoReturnFormat.HAVE_BLACK);
                }
                if (!EmptyUtils.isEmpty(param.getHeadImage())) {
                    //更新微信头像
                    CereBuyerUser cereBuyerUser = new CereBuyerUser();
                    cereBuyerUser.setBuyerUserId(user.getBuyerUserId());
                    cereBuyerUser.setHeadImage(param.getHeadImage());
                    cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
                }
                updateLastLoginIp(user.getBuyerUserId(), param.getIp());
            }
            if (EmptyUtils.isEmpty(user.getPhone())) {
                user.setIfFirst(IntegerEnum.YES.getCode());
            } else {
                user.setIfFirst(IntegerEnum.NO.getCode());
                //设置token
                assembleToken(user);
            }
            user.setWechatOpenId(openid);
            return user;
        } else {
            throw new CoBusinessException(CoReturnFormat.WECHAT_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser alipayLogin(LoginParam param) {
        BuyerUser user;
        String time = TimeUtils.yyMMddHHmmss();
        AlipayUserInfoShareResponse userInfoResponse = alipayUtil.getBuyerId(param.getCode());
        if (userInfoResponse == null || StringUtils.isBlank(userInfoResponse.getUserId())) {
            throw new CoBusinessException(CoReturnFormat.ALI_APPLET_AUTH_ERROR);
        }
        user = cereBuyerUserDAO.findByAliUserId(userInfoResponse.getUserId());
        if (user == null) {
            //未注册
            user = new BuyerUser();
            user.setIfFirst(IntegerEnum.NO.getCode());
            user.setWechatName(userInfoResponse.getNickName());

            //新增用户
            CereBuyerUser buyerUser = new CereBuyerUser();
            //buyerUser.setWechatOpenId(com.shop.zkmall.admin.param.getWechatOpenId());
            buyerUser.setAliUserId(userInfoResponse.getUserId());
            buyerUser.setCreateTime(time);
            buyerUser.setPhone(param.getPhone());
            buyerUser.setPassword(EncryptUtil.encrypt("123456"));
            String name = "MJ" + RandomStringUtil.getRandomCode(4, 0);
            if (StringUtils.isNotBlank(userInfoResponse.getNickName())) {
                name = userInfoResponse.getNickName();
            }
            buyerUser.setName(name);
            buyerUser.setState(IntegerEnum.YES.getCode());
            buyerUser.setTerminal(IntegerEnum.TERMINAL_4.getCode());
            //设置头像
            if (!EmptyUtils.isEmpty(userInfoResponse.getAvatar())) {
                buyerUser.setHeadImage(userInfoResponse.getAvatar());
            } else {
                buyerUser.setHeadImage(defaultHeadImg);
            }
            user.setHeadImage(buyerUser.getHeadImage());
            buyerUser.setChannelCode(param.getChannelCode());
            cereBuyerUserDAO.insert(buyerUser);
            user.setBuyerUserId(buyerUser.getBuyerUserId());
            user.setAliUserId(userInfoResponse.getUserId());
            //user.setToken(buyerUser.getToken());
        } else {
            if (IntegerEnum.YES.getCode().equals(user.getIfBlack())) {
                throw new CoBusinessException(CoReturnFormat.HAVE_BLACK);
            }
            if (!EmptyUtils.isEmpty(param.getHeadImage())) {
                //更新微信头像
                CereBuyerUser cereBuyerUser = new CereBuyerUser();
                cereBuyerUser.setBuyerUserId(user.getBuyerUserId());
                cereBuyerUser.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
            }
            updateLastLoginIp(user.getBuyerUserId(), param.getIp());
        }
        if (StringUtils.isBlank(user.getPhone())) {
            user.setIfFirst(IntegerEnum.YES.getCode());
        }

        assembleToken(user);

        user.setAliUserId(userInfoResponse.getUserId());
        return user;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser wxAppLogin(LoginPhoneParam param) {
        BuyerUser user;
        //查询该微信号是否已注册
        if (!EmptyUtils.isEmpty(param.getWechatOpenId())) {
            user = cereBuyerUserDAO.findByOpenid(param.getWechatOpenId());
            if (user == null) {
                //未注册
                user = new BuyerUser();
                user.setIfFirst(IntegerEnum.YES.getCode());
            } else {
                user.setIfFirst(IntegerEnum.NO.getCode());
                assembleToken(user);
                updateLastLoginIp(user.getBuyerUserId(), param.getIp());
            }
            return user;
        } else {
            throw new CoBusinessException(CoReturnFormat.WECHAT_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser setWxPhone(LoginPhoneParam param) {
        Map<String, Object> map = WechatUtil.getPhoneNumber(param);
        if (EmptyUtils.isEmpty(map)) {
            throw new CoBusinessException(CoReturnFormat.WECHAT_ERROR);
        }
        String phone = "";
        Object phoneNumber = map.get("com.shop.zkmall.admin.param");
        String jsonString = JSONObject.toJSONString(phoneNumber);
        JSONObject obj = JSONObject.parseObject(jsonString);
        if (!EmptyUtils.isEmpty(jsonString)) {
            phone = obj.get("phoneNumber").toString();
        }
        BuyerUser result = new BuyerUser();
        result.setPhone(phone);
        String time = TimeUtils.yyMMddHHmmss();
        //校验手机号是否绑定其他微信
        CereBuyerUser cereBuyerUser = cereBuyerUserDAO.checkPhoneWx(phone);
        if (cereBuyerUser != null) {
            //绑定了微信
            if (!cereBuyerUser.getWechatOpenId().equals(param.getWechatOpenId())) {
                //如果和当前微信openid不一致，提示
                throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY_WX);
            } else {
                //如果一致,直接返回用户信息
                result.setBuyerUserId(cereBuyerUser.getBuyerUserId());
                result.setHeadImage(cereBuyerUser.getHeadImage());
                result.setWechatOpenId(param.getWechatOpenId());
                result.setWechatName(cereBuyerUser.getWechatName());

                assembleToken(result);

                return result;
            }
        }
        //查询手机号已注册用户数据
        BuyerUser byPhone = cereBuyerUserDAO.findByPhone(phone);
        //查询微信已注册用户数据
        CereBuyerUser user = cereBuyerUserDAO.getByOpenid(param.getWechatOpenId());
        if (byPhone != null) {
            if (user != null) {
                //删除手机号注册用户
                cereBuyerUserDAO.deleteByPrimaryKey(byPhone.getBuyerUserId());
                //更新微信注册用户绑定手机号
                user.setPhone(byPhone.getPhone());
                user.setUpdateTime(time);
                user.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(user);
                result.setBuyerUserId(user.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
                //result.setToken(user.getToken());
                result.setPhone(phone);
                //同步更新手机号注册的业务数据转移到微信注册用户
                transferUserData(byPhone.getBuyerUserId(), user.getBuyerUserId());
            } else {
                //更新手机号已注册用户openid
                CereBuyerUser buyerUser = new CereBuyerUser();
                buyerUser.setBuyerUserId(byPhone.getBuyerUserId());
                buyerUser.setWechatOpenId(param.getWechatOpenId());
                buyerUser.setUpdateTime(time);
                //设置头像
                buyerUser.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(buyerUser);
                result = byPhone;
            }
        } else {
            //未绑定其他微信,更新手机号
            if (user != null) {
                user.setPhone(phone);
                user.setUpdateTime(time);
                user.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(user);
                result.setBuyerUserId(user.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
                //result.setToken(user.getToken());
            } else {
                //新增用户
                CereBuyerUser buyerUser = new CereBuyerUser();
                buyerUser.setWechatOpenId(param.getWechatOpenId());
                buyerUser.setCreateTime(time);
                buyerUser.setPhone(phone);
                buyerUser.setPassword(EncryptUtil.encrypt("123456"));
                buyerUser.setName("MJ" + RandomStringUtil.getRandomCode(4, 0));
                buyerUser.setState(IntegerEnum.YES.getCode());
                buyerUser.setTerminal(IntegerEnum.TERMINAL_2.getCode());
                //设置头像
                if (!EmptyUtils.isEmpty(param.getHeadImage())) {
                    buyerUser.setHeadImage(param.getHeadImage());
                } else {
                    buyerUser.setHeadImage(defaultHeadImg);
                }
                buyerUser.setChannelCode(param.getChannelCode());
                cereBuyerUserDAO.insert(buyerUser);
                result.setBuyerUserId(buyerUser.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
            }
        }
        assembleToken(result);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser updateWxPhone(UpdateWxPhoneParam param) {
        BuyerUser result = new BuyerUser();
        result.setPhone(param.getPhone());
        String time = TimeUtils.yyMMddHHmmss();
        //校验手机号是否绑定其他微信
        CereBuyerUser cereBuyerUser = cereBuyerUserDAO.checkPhoneWx(param.getPhone());
        if (cereBuyerUser != null) {
            throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY_WX);
        }
        //查询手机号已注册用户数据
        BuyerUser byPhone = cereBuyerUserDAO.findByPhone(param.getPhone());
        //查询微信已注册用户数据
        CereBuyerUser user = cereBuyerUserDAO.getByOpenid(param.getWechatOpenId());
        if (byPhone != null) {
            if (user != null) {
                //删除手机号注册用户
                cereBuyerUserDAO.deleteByPrimaryKey(byPhone.getBuyerUserId());
                //更新微信注册用户绑定手机号
                user.setPhone(byPhone.getPhone());
                user.setUpdateTime(time);
                user.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(user);
                result.setBuyerUserId(user.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
                //result.setToken(user.getToken());
                result.setPhone(param.getPhone());
                //迁移用户数据
                transferUserData(byPhone.getBuyerUserId(), user.getBuyerUserId());
            } else {
                //更新手机号已注册用户openid
                CereBuyerUser buyerUser = new CereBuyerUser();
                buyerUser.setBuyerUserId(byPhone.getBuyerUserId());
                buyerUser.setWechatOpenId(param.getWechatOpenId());
                buyerUser.setUpdateTime(time);
                //设置头像
                buyerUser.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(buyerUser);
                result = byPhone;
            }
        } else {
            //未绑定其他微信,更新手机号
            if (user != null) {
                user.setPhone(param.getPhone());
                user.setUpdateTime(time);
                user.setHeadImage(param.getHeadImage());
                cereBuyerUserDAO.updateByPrimaryKeySelective(user);
                result.setBuyerUserId(user.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
                //result.setToken(user.getToken());
            } else {
                //新增用户
                CereBuyerUser buyerUser = new CereBuyerUser();
                buyerUser.setWechatOpenId(param.getWechatOpenId());
                buyerUser.setCreateTime(time);
                buyerUser.setPhone(param.getPhone());
                //buyerUser.setToken(RandomStringUtil.getRandomCode(32,0));
                buyerUser.setPassword(EncryptUtil.encrypt("123456"));
                buyerUser.setName("MJ" + RandomStringUtil.getRandomCode(4, 0));
                buyerUser.setState(IntegerEnum.YES.getCode());
                buyerUser.setTerminal(IntegerEnum.TERMINAL_2.getCode());
                //设置头像
                if (!EmptyUtils.isEmpty(param.getHeadImage())) {
                    buyerUser.setHeadImage(param.getHeadImage());
                } else {
                    buyerUser.setHeadImage(defaultHeadImg);
                }
                buyerUser.setRegisterIp(param.getIp());
                buyerUser.setChannelCode(param.getChannelCode());
                cereBuyerUserDAO.insert(buyerUser);
                result.setBuyerUserId(buyerUser.getBuyerUserId());
                result.setWechatOpenId(param.getWechatOpenId());
                //result.setToken(buyerUser.getToken());
            }
        }

        assembleToken(result);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public BuyerUser updateAliPhone(UpdateAliPhoneParam param) {
        String phone = param.getPhone();
        if (StringUtils.isNotBlank(phone) && param.getEncrypted() != null && param.getEncrypted()) {
            try {
                //phone = "15622728105";
                JSONObject obj = PayUtil.decryptedData(phone, alipayConfig.getAlipayPublicKey(), alipayConfig.getDecryptKey());
                log.info("decryptedData: {}", obj.toJSONString());
                if ("10000".equals(obj.getString("code"))) {
                    phone = obj.getString("mobile");
                } else {
                    throw new CoBusinessException(CoReturnFormat.VERIFY_PHONE_ERROR);
                }
            } catch (Exception e) {
                log.error("updateAliPhone failed: {}", e.getMessage(), e);
                throw new CoBusinessException(CoReturnFormat.VERIFY_PHONE_ERROR);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();

        CereBuyerUser existUser = cereBuyerUserDAO.checkPhoneAli(phone, param.getBuyerUserId());
        if (existUser != null) {
            throw new CoBusinessException(CoReturnFormat.OTHER_ALI_USER_BIND_PHONE);
        }

        try {
            //查询手机号已注册用户数据
            CereBuyerUser user = cereBuyerUserDAO.selectByPrimaryKey(param.getBuyerUserId());
            CereBuyerUser byPhone = cereBuyerUserDAO.selectByPhone(phone);
            if (byPhone != null && !byPhone.getBuyerUserId().equals(param.getBuyerUserId())) {
                //删除手机号注册用户
                cereBuyerUserDAO.deleteByPrimaryKey(byPhone.getBuyerUserId());
                //迁移用户数据
                transferUserData(byPhone.getBuyerUserId(), param.getBuyerUserId());
                //同步微信相关的信息
                //如果当前新账户还没有微信信息，同步过来
                if (StringUtils.isBlank(user.getWechatOpenId())) {
                    user.setWechatOpenId(byPhone.getWechatOpenId());
                }
                if (StringUtils.isBlank(user.getWechatUnionId())) {
                    user.setWechatUnionId(byPhone.getWechatUnionId());
                }
                if (StringUtils.isBlank(user.getWechatName())) {
                    user.setWechatName(byPhone.getWechatName());
                }
                if (StringUtils.isBlank(user.getWechatNumber())) {
                    user.setWechatNumber(byPhone.getWechatNumber());
                }
            }
            user.setPhone(phone);
            user.setUpdateTime(time);
            cereBuyerUserDAO.updateByPrimaryKeySelective(user);
            BuyerUser result = cereBuyerUserDAO.findByPhone(phone);

            assembleToken(result);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CoBusinessException(CoReturnFormat.VERIFY_PHONE_ERROR);
        }
    }

    @Override
    public void updateGrowth(Long buyerUserId, Integer growth) {
        CereBuyerUser cereBuyerUser = cereBuyerUserDAO.selectByPrimaryKey(buyerUserId);
        if (cereBuyerUser != null) {
            //退款的时候，成长值为负，不用降会员等级
            if (growth < 0) {
                cereBuyerUserDAO.updateGrowth(buyerUserId, growth);
            } else {
                cereBuyerUserDAO.updateByPrimaryKey(cereBuyerUser);
            }
        }
    }

    @Override
    public void delete(CereBuyerUser user) {
        // 查订单，1.账户近期不存在交易:您的账户无未完成订单、无已完成但未满15天订单。
        Integer[] states = new Integer[]{1, 2, 3};
        List<Orders> list = cereShopOrderDAO.getAllInState(states, user.getBuyerUserId());
        if (!list.isEmpty()) {
            throw new CoBusinessException("您的账户存在待付款/已付款未完成订单");
        }

        // 已完成订单，但未满15天
        /*OrderGetAllParam orderGetAllParam = new OrderGetAllParam();
        orderGetAllParam.setBuyerUserId(user.getBuyerUserId());
        orderGetAllParam.setState("4");*/
        List<CereShopOrder> completedList = cereShopOrderDAO.selectList(Wrappers.<CereShopOrder>lambdaQuery().eq(CereShopOrder::getBuyerUserId, user.getBuyerUserId())
                .eq(CereShopOrder::getState, IntegerEnum.ORDER_FINISH.getCode()));
        // 校验时间
        for (CereShopOrder order : completedList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parse = simpleDateFormat.parse(order.getCreateTime());
                long createTime = parse.getTime();
                if (createTime + (24 * 60 * 60 * 1000 * 15) > new Date().getTime()) {
                    throw new CoBusinessException("您的账户存在已完成订单，但是未满15天");
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
        //2.账户相关财产权益已结清:您的账户不存在可提现的余额。

        // 删除账户
        int i = cereBuyerUserDAO.deleteByPrimaryKey(user.getBuyerUserId());
        if (i <= 0) {
            throw new CoBusinessException("数据异常");
        }
    }

    @Override
    public void updateLastLoginIp(Long buyerUserId, String ip) {
        CereBuyerUser user = new CereBuyerUser();
        user.setBuyerUserId(buyerUserId);
        user.setLastLoginIp(ip);
        cereBuyerUserDAO.updateById(user);
    }

    /**
     * 迁移用户数据
     */
    private void transferUserData(Long srcUserId, Long targetUserId) {
        //同步更新手机号注册的业务数据转移到微信注册用户
        //收藏数据转移
        cereBuyerCollectService.updateBuyerData(targetUserId, srcUserId);
        //是否点赞数据转移
        cereBuyerCommentLikeService.updateBuyerData(targetUserId, srcUserId);
        //浏览足迹数据转移
        cereBuyerFootprintService.updateBuyerData(targetUserId, srcUserId);
        //关联平台标签数据转移
        cereBuyerLabelService.updateBuyerData(targetUserId, srcUserId);
        //收货地址数据转移
        cereBuyerReceiveService.updateBuyerData(targetUserId, srcUserId);
        //搜索记录数据转移
        cereBuyerSearchService.updateBuyerData(targetUserId, srcUserId);
        //消息数据转移
        cereNoticeService.updateBuyerData(targetUserId, srcUserId);
        //回答数据转移
        //提问数据转移
        //购物车数据转移
        cereShopCartService.updateBuyerData(targetUserId, srcUserId);
        //评论数据转移
        cereShopCommentService.updateBuyerData(targetUserId, srcUserId);
        //订单数据转移
        CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
        cereShopOrderService.updateBuyerData(targetUserId, srcUserId);
        //店铺浏览数据转移
        cereShopVisitService.updateBuyerData(targetUserId, srcUserId);
    }

    @Override
    public BuyerUser login(LoginParam param) {
        return cereBuyerUserDAO.findByPhone(param.getPhone());
    }

    @Override
    public void insert(CereBuyerUser buyerUser) {
        cereBuyerUserDAO.insert(buyerUser);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CereBuyerUser buyerUser, CereBuyerUser user) {
        cereBuyerUserDAO.updateByPrimaryKeySelective(buyerUser);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updatePassword(UserParam param, CereBuyerUser user) {
        CereBuyerUser cereBuyerUser = new CereBuyerUser();
        cereBuyerUser.setBuyerUserId(user.getBuyerUserId());
        cereBuyerUser.setPassword(EncryptUtil.encrypt(param.getNewPassword()));
        cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
    }

    @Override
    public MyUser getUser(Long buyerUserId) {
        MyUser user = cereBuyerUserDAO.getUser(buyerUserId);
        if (user != null) {
            //查询未读消息数量
            int notRead = cereNoticeService.getNotRead(buyerUserId, user.getCreateTime());
            user.setNotRead(notRead);
            //查询订单数量
            OrderCountDTO dto = cereShopOrderDAO.selectOrderCount(buyerUserId);
            if (dto != null) {
                user.setWaitPayOrderCount(dto.getWaitPayOrderCount());
                user.setWaitSendOrderCount(dto.getWaitSendOrderCount());
                user.setWaitReceiveOrderCount(dto.getWaitReceiveOrderCount());
                user.setTotalOrderCount(dto.getTotalOrderCount());
            }
            //收藏数量
            user.setCollectCount(cereBuyerCollectService.findAllCollectCount(buyerUserId));
            user.setCommentCount(cereShopCommentService.findCommentCount(buyerUserId));
            user.setVisitCount(cereBuyerFootprintService.findFootprintCount(buyerUserId));
            user.setAfterCount(cereOrderAfterService.findAfterCount(buyerUserId));

            //卡券数量(商家券)
            user.setShopCouponCount(0);

            //优惠券数量(平台券)
            user.setPlatformCouponCount(0);

        }
        return user;
    }

    @Override
    public CerePlatformCanvas getCanvas(CerePlatformCanvas canvas) {
        return cereBuyerUserDAO.getCanvas(canvas);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updatePhone(UserParam param, CereBuyerUser user) {
        BuyerUser byPhone = cereBuyerUserDAO.findByPhone(param.getPhone());
        if (byPhone != null) {
            throw new CoBusinessException(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        CereBuyerUser cereBuyerUser = new CereBuyerUser();
        cereBuyerUser.setBuyerUserId(user.getBuyerUserId());
        cereBuyerUser.setPhone(param.getPhone());
        cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void relievePhone(UserParam param, CereBuyerUser user) {
        CereBuyerUser cereBuyerUser = new CereBuyerUser();
        cereBuyerUser.setBuyerUserId(user.getBuyerUserId());
        cereBuyerUserDAO.relievePhone(cereBuyerUser);
    }

    /**
     * 设置token信息
     */
    private void assembleToken(BuyerUser user) {
        //存储token到redis
        TokenInfoBo tokenInfoBo = userRedisService.createToken(user.getPhone(), user.getBuyerUserId());
        user.setToken(tokenInfoBo.getToken());
        user.setRefreshToken(tokenInfoBo.getRefreshToken());
    }
}
