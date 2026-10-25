/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.index;

import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.commons.cache.constants.CacheKeyConstants;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.app.page.index.Index;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.login.BuyerUser;
import com.shop.zkmall.app.page.login.TokenInfoBo;
import com.shop.zkmall.app.param.buyer.BuyerGetCodeParam;
import com.shop.zkmall.app.param.check.CheckShopParam;
import com.shop.zkmall.app.param.index.DictGetSelectParam;
import com.shop.zkmall.app.param.index.IndexParam;
import com.shop.zkmall.app.param.index.LoginParam;
import com.shop.zkmall.app.param.index.LoginPhoneParam;
import com.shop.zkmall.app.param.index.RefreshTokenParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.param.index.UpdateAliPhoneParam;
import com.shop.zkmall.app.param.index.UpdateWxPhoneParam;
import com.shop.zkmall.app.redis.service.api.UserRedisService;
import com.shop.zkmall.app.service.buyer.CereBuyerSearchService;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.dict.CerePlatformDictService;
import com.shop.zkmall.app.service.index.IndexService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.app.utils.WechatUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.EncryptUtil;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import com.shop.zkmall.commons.utils.ValidationUtil;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 客户登录
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("app")
@RequiredArgsConstructor
@Tag(name = "客户登录模块", description = "客户登录模块")
public class LoginController {

    private final HttpServletRequest request;

    private final RedisTemplate<String, Object> redisTemplate;

    private final IndexService indexService;

    private final CereBuyerUserService cereBuyerUserService;

    private final CereProductSkuService cereProductSkuService;

    private final CerePlatformShopService cerePlatformShopservice;

    private final CerePlatformDictService cerePlatformDictService;

    private final UserRedisService userRedisService;

    private final CereBuyerSearchService cereBuyerSearchService;

    private final SmsStrategy smsStrategy;

    private final CaptchaService captchaService;

    @Value("${defaultHeadImg}")
    private String defaultHeadImg;

    /**
     * 手机号验证码登录
     *
     * @param param 封装json对象
     */
    @PostMapping("login")
    @NoRepeatSubmit
    @Operation(summary = "手机号验证码登录")
    public Result<BuyerUser> login(@RequestBody LoginParam param) {
        ValidationUtil.checkValidateCode(param.getPhone(), param.getVerificationCode());
        String ip = HttpUtils.getClientIp(request);
        //根据手机号查询客户信息
        BuyerUser personal = cereBuyerUserService.login(param);
        if (IntegerEnum.REGIST.getCode().equals(param.getType())) {
            //注册
            if (personal != null) {
                return new Result<>(CoReturnFormat.USER_AREADY_REGISTER);
            } else {
                //新增用户
                CereBuyerUser cereBuyerUser = new CereBuyerUser();
                cereBuyerUser.setName("MJ" + RandomStringUtil.getRandomCode(5, 0));
                cereBuyerUser.setPhone(param.getPhone());
                cereBuyerUser.setPassword(EncryptUtil.encrypt("123456"));
                cereBuyerUser.setCreateTime(TimeUtils.yyMMddHHmmss());
                cereBuyerUser.setState(IntegerEnum.YES.getCode());
                cereBuyerUser.setIfBlack(IntegerEnum.NO.getCode());
                cereBuyerUser.setTerminal(param.getTerminal() != null ? param.getTerminal() : IntegerEnum.TERMINAL_0.getCode());
                cereBuyerUser.setRegisterIp(ip);
                cereBuyerUser.setLastLoginIp(ip);
                cereBuyerUser.setChannelCode(param.getChannelCode());
                //设置默认头像
                cereBuyerUser.setHeadImage(defaultHeadImg);
                cereBuyerUserService.insert(cereBuyerUser);
                personal = new BuyerUser();
                personal.setWechatName(cereBuyerUser.getName());
                personal.setToken(cereBuyerUser.getToken());
                personal.setPhone(cereBuyerUser.getPhone());
                personal.setBuyerUserId(cereBuyerUser.getBuyerUserId());
                //创建token并存储缓存
                TokenInfoBo tokenInfoBo = userRedisService.createToken(param.getPhone(), personal.getBuyerUserId());
                personal.setToken(tokenInfoBo.getToken());
                personal.setRefreshToken(tokenInfoBo.getRefreshToken());
            }
        } else {
            //登录
            if (personal != null) {
                cereBuyerUserService.updateLastLoginIp(personal.getBuyerUserId(), ip);
                if (IntegerEnum.NO.getCode().equals(personal.getState())) {
                    return new Result<>(CoReturnFormat.USER_TYPE_STOP);
                }
                if (IntegerEnum.YES.getCode().equals(personal.getIfBlack())) {
                    return new Result<>(CoReturnFormat.HAVE_BLACK);
                }
                //创建token并存储缓存
                TokenInfoBo tokenInfoBo = userRedisService.createToken(param.getPhone(), personal.getBuyerUserId());
                personal.setToken(tokenInfoBo.getToken());
                personal.setRefreshToken(tokenInfoBo.getRefreshToken());
            } else {
                return new Result<>(CoReturnFormat.USER_UNREGISTER);
            }
        }
        return new Result<>(personal, CoReturnFormat.SUCCESS);
    }

    /**
     * 刷新token
     *
     * @param param 封装json对象
     */
    @PostMapping("refreshToken")
    @NoRepeatSubmit
    @Operation(summary = "刷新token")
    public Result<BuyerUser> refreshToken(@RequestBody RefreshTokenParam param) {
        TokenInfoBo tokenInfoBo = userRedisService.refreshToken(param.getRefreshToken());
        CereBuyerUser cereBuyerUser = cereBuyerUserService.selectByBuyerUserId(tokenInfoBo.getBuyerUserId());
        BuyerUser buyerUser = new BuyerUser();
        BeanUtils.copyProperties(cereBuyerUser, buyerUser);

        buyerUser.setToken(tokenInfoBo.getToken());
        buyerUser.setRefreshToken(tokenInfoBo.getRefreshToken());
        return new Result<>(buyerUser, CoReturnFormat.SUCCESS);
    }

    /**
     * 获取微信手机号绑定
     *
     * @param param 封装json对象
     */
    @PostMapping("setWxPhone")
    @NoRepeatSubmit
    @Operation(summary = "获取微信手机号绑定")
    public Result<BuyerUser> setWxPhone(@RequestBody LoginPhoneParam param) {
        BuyerUser user = cereBuyerUserService.setWxPhone(param);
        return new Result<>(user, CoReturnFormat.SUCCESS);
    }

    /**
     * 获取微信加密秘钥
     *
     * @param param 封装json对象
     */
    @PostMapping("getSessionKey")
    @NoRepeatSubmit
    @Operation(summary = "获取微信加密秘钥")
    public Result<BuyerUser> getSessionKey(@RequestBody LoginParam param) {
        //传入code后然后获取openid和session_key的，把他们封装到json里面
        JSONObject json = WechatUtil.getSessionKeyOropenid(param.getCode());
        String sessionKey = json.getString("session_key");
        String openid = json.get("openid").toString();
        BuyerUser buyerUser = new BuyerUser();
        buyerUser.setSessionKey(sessionKey);
        buyerUser.setWechatOpenId(openid);
        return new Result<>(buyerUser, CoReturnFormat.SUCCESS);
    }

    /**
     * 微信授权登录
     *
     * @param param 封装json对象
     */
    @PostMapping("wxLogin")
    @NoRepeatSubmit
    @Operation(summary = "微信授权登录")
    public Result<BuyerUser> wxLogin(@RequestBody LoginParam param) {
        param.setIp(HttpUtils.getClientIp(request));
        BuyerUser personal = cereBuyerUserService.wxLogin(param);
        return new Result<>(personal, CoReturnFormat.SUCCESS);
    }

    /**
     * APP微信授权登录
     *
     * @param param 封装json对象
     */
    @PostMapping("wxAppLogin")
    @NoRepeatSubmit
    @Operation(summary = "APP微信授权登录")
    public Result<BuyerUser> wxAppLogin(@RequestBody LoginPhoneParam param) {
        param.setIp(HttpUtils.getClientIp(request));
        BuyerUser personal = cereBuyerUserService.wxAppLogin(param);
        return new Result<>(personal, CoReturnFormat.SUCCESS);
    }

    /**
     * 支付宝小程序授权登录
     *
     * @param param 封装json对象
     */
    @PostMapping("alipayLogin")
    @NoRepeatSubmit
    @Operation(summary = "支付宝小程序授权登录")
    public Result<BuyerUser> alipayLogin(@RequestBody LoginParam param) {
        param.setIp(HttpUtils.getClientIp(request));
        BuyerUser personal = cereBuyerUserService.alipayLogin(param);
        return new Result<>(personal, CoReturnFormat.SUCCESS);
    }

    /**
     * 手机号合并微信openid绑定
     *
     * @param param 封装json对象
     */
    @PostMapping("updateWxPhone")
    @NoRepeatSubmit
    @Operation(summary = "手机号合并微信openid绑定")
    public Result<BuyerUser> updateWxPhone(@RequestBody UpdateWxPhoneParam param) {
        ValidationUtil.checkValidateCode(param.getPhone(), param.getVerificationCode());
        param.setIp(HttpUtils.getClientIp(request));
        BuyerUser user = cereBuyerUserService.updateWxPhone(param);
        return new Result<>(user, CoReturnFormat.SUCCESS);
    }

    /**
     * 支付宝小程序验证手机号
     *
     * @param param 封装json对象
     */
    @PostMapping("updateAliPhone")
    @NoRepeatSubmit
    @Operation(summary = "支付宝小程序验证手机号")
    public Result<BuyerUser> updateAliPhone(@RequestBody UpdateAliPhoneParam param) {
        param.setIp(HttpUtils.getClientIp(request));
        BuyerUser buyerUser = cereBuyerUserService.updateAliPhone(param);
        return new Result<>(buyerUser, CoReturnFormat.SUCCESS);
    }

    /**
     * 首页数据查询
     */
    @GetMapping("index")
    @Operation(summary = "首页数据查询")
    public Result<Index> index(IndexParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Index index = indexService.index(param, user);
        return new Result<>(index, CoReturnFormat.SUCCESS);
    }

    /**
     * 历史搜索查询
     */
    @GetMapping("getHistory")
    @Operation(summary = "历史搜索查询")
    public Result<List<CereBuyerSearch>> getHistory(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        List<CereBuyerSearch> list = indexService.getHistory(user);
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * 商品搜索查询
     */
    @GetMapping("getSearchProducts")
    @Operation(summary = "商品搜索查询")
    public Result<Page<Product>> getSearchProducts(SearchParam param, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)) {
            //根据token查询用户信息
            user = cereBuyerUserService.findByToken(token);
        }
        Page<Product> page = indexService.getSearchProducts(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }

    /**
     * 删除搜索记录
     */
    @RequestMapping(value = "deleteSearch", method = {RequestMethod.DELETE, RequestMethod.POST})
    @Operation(summary = "删除搜索记录")
    public Result<String> deleteSearch(@RequestBody SearchParam param) {
        indexService.deleteSearch(param);
        return new Result<>(CoReturnFormat.SUCCESS);
    }

    @RequestMapping(value = "selectHotSearch")
    @Operation(summary = "查询热搜词")
    public Result<List<String>> selectHotSearch() {
        return new Result<>(cereBuyerSearchService.selectHotSearch(), CoReturnFormat.SUCCESS);
    }


    /**
     * 获取短信验证码
     */
    @GetMapping("getCode")
    @Operation(summary = "获取短信验证码")
    @NoRepeatSubmit
    public Result<String> getCode(@Validated BuyerGetCodeParam user) {
        String ip = HttpUtils.getClientIp(request);
        if (StringUtils.length(user.getCode()) > 4) {
            CaptchaVO captchaVO = new CaptchaVO();
            captchaVO.setCaptchaVerification(user.getCode());
            ResponseModel verification = captchaService.verification(captchaVO);
            if (!verification.isSuccess()) {
                return Result.fail(CoReturnFormat.SEND_CODE_ERR, "验证码错误!");
            }
        } else {
            String key = StringUtils.isNotBlank(user.getCaptchaKey()) ? CacheKeyConstants.CAPTCHA_PREFIX + user.getCaptchaKey() : CacheKeyConstants.CAPTCHA_PREFIX + ip;
            String verifyCode = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.equalsAnyIgnoreCase(verifyCode, user.getCode())) {
                return Result.fail(CoReturnFormat.SEND_CODE_ERR, "验证码错误!");
            }
            redisTemplate.delete(key);
        }
        //获取验证码
        String code = RandomStringUtil.getRandom();
        //用于短信发送
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        String sendCodeKey = CacheKeyConstants.SEND_SMS_CODE_PREFIX + user.getPhone();
        if (redisTemplate.hasKey(sendCodeKey)) {
            log.warn("手机号{}重复获取短信验证码.", user.getPhone());
            return Result.fail(CoReturnFormat.SEND_CODE_ERR, "一分钟内禁止重复获取,请稍后再试!");
        }
        redisTemplate.opsForValue().set(sendCodeKey, code, Duration.ofMinutes(1));
        //验证码存到redis中(5分钟失效)
        redisTemplate.opsForValue().set(user.getPhone(), code, Duration.ofMinutes(5));
        log.info("成功发送验证码到redis中，key：{}，value:{}",user.getPhone(),code);
        //发送短信给用户
        try {
            smsStrategy.sendNotice(user.getPhone(), map);
        } catch (Exception e) {
            log.error("getCode fail: phone = {}", user.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result<>(CoReturnFormat.SUCCESS);
    }

    /**
     * 初始化商品库存
     */
    @GetMapping("stock")
    @Operation(summary = "初始化商品库存")
    public Result<String> stock() {
        List<CereProductSku> skus = cereProductSkuService.findAll();
        if (!EmptyUtils.isEmpty(skus)) {
            skus.forEach(sku -> redisTemplate.opsForValue().set(String.valueOf(sku.getSkuId()), sku.getStockNumber()));
        }
        return new Result<>(CoReturnFormat.SUCCESS);
    }

    /**
     * 查询是否申请
     */
    @GetMapping("check")
    @Operation(summary = "查询是否申请")
    public Result<CerePlatformShop> check(CheckShopParam param) {
        ValidationUtil.checkValidateCode(param.getShopPhone(), param.getCode());
        CerePlatformShop shop = cerePlatformShopservice.check(param.getShopPhone());
        return new Result<>(shop, CoReturnFormat.SUCCESS);
    }


    /**
     * 字典下拉数据查询
     */
    @GetMapping(value = "getSelect")
    @Operation(summary = "字典下拉数据查询")
    public Result<List<CerePlatformDict>> getSelect(DictGetSelectParam param) {
        List<CerePlatformDict> list = cerePlatformDictService.getSelect(param.getDictName());
        return new Result<>(list);
    }

    @GetMapping(value = "getSwaggerConfig")
    @Operation(summary = "字典下拉数据查询")
    public JSONObject getSwaggerConfig() {
        return JSONObject.parseObject("{\"configUrl\":\"/v3/api-docs/swagger-config\",\"oauth2RedirectUrl\":\"http://localhost/api/swagger-ui/oauth2-redirect.html\",\"operationsSorter\":\"alpha\",\"tagsSorter\":\"alpha\",\"urls\":[{\"url\":\"/api/v3/api-docs/default\",\"name\":\"default\"}],\"validatorUrl\":\"\"}");
    }

    @GetMapping(value = "alipayPc")
    @Operation(summary = "字典下拉数据查询")
    public JSONObject alipayPc() {
        return new JSONObject();
    }

}
