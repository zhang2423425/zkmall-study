package com.shop.zkmall.admin.controller;


import cn.hutool.core.util.ObjectUtil;
import com.shop.zkmall.admin.param.user.UserLoginParam;
import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.EncryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "用户登录模块", description = "用户登录模块")
public class AdminController {

    private final HttpServletRequest httpServletRequest;
    private final CerePlatformUserService cerePlatformUserService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final UserRedisService userRedisService;


    @Operation(summary = "根据前端传来的用户密文登录")
    @PostMapping("/login")
    public Result<CerePlatformUser> login(@RequestBody UserLoginParam userLoginParam){

        //解密
        if(userLoginParam.getUsername().length()>15){
            try {
                userLoginParam= userLoginParam.decrypt();
            } catch (Exception e) {
                return new Result<>(CoReturnFormat.PARAM_INVALID) ;
            }
        }
        CerePlatformUser user;
        //有验证码,有手机号
        if(!EmptyUtils.isEmpty(userLoginParam.getCode())){
            if (noDemoAccount(userLoginParam)) {
                //手机号登录,校验验证码
                if (!userLoginParam.getCode().equals("9999")){
                    return new Result<>(CoReturnFormat.CODE_ERROR);
                }
            }else {
                //普通用户
                String code = (String) redisTemplate.opsForValue().get(userLoginParam.getUsername());
                if (!userLoginParam.getCode().equals(code)) {
                    return new Result<>(CoReturnFormat.CODE_ERROR);
                }
            }
            //根据手机号查询账户信息
            user = cerePlatformUserService.findByPhone(userLoginParam.getUsername());
            log.info("user: {}", user);
        }else {
            //无验证码，用用户名和密码登录
            user=cerePlatformUserService.findByUsername(userLoginParam.getUsername());
            if(user!=null){
                //密码不同
                if (!EncryptUtil.decrypt(user.getPassword()).equals(userLoginParam.getPassword())){
                    return new Result<>(CoReturnFormat.USER_OR_PASSWD_ERROR);
                }
            }

        }
        if(user==null){
            return new Result<>(CoReturnFormat.USER_UNREGISTER);
        }
        if(IntegerEnum.NO.getCode().equals(user.getState())){
            return new Result<>(CoReturnFormat.USER_TYPE_STOP);
        }

        String token ;
        if (ObjectUtil.isNotNull(userLoginParam.getRememberMe())&&userLoginParam.getRememberMe()) {
            token=userRedisService.getToken(user.getPlatformUserId());
            if (ObjectUtil.isNull(token)) {
                token=userRedisService.createToken4Remember(userLoginParam.getUsername(),user.getPlatformUserId());
            }
        }else {
            //不记住我，重新创建token
            token=userRedisService.createToken(userLoginParam.getUsername(),user.getPlatformUserId());
        }

        user.setToken(token);
        httpServletRequest.setAttribute("user",user);
        return new Result<>(user);
    }

    private boolean noDemoAccount(UserLoginParam userLoginParam) {
        CerePlatformUser user=cerePlatformUserService.findById(1L);
        return user.getPhone().equals(userLoginParam.getUsername());

    }


}
