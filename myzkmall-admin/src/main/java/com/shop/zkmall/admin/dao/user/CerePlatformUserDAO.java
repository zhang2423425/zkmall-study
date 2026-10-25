package com.shop.zkmall.admin.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.param.user.UserGetAllParam;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformUserDAO extends BaseMapper<CerePlatformUser> {
    int deleteByPrimaryKey(Long platformUserId);

    int insertSelective(CerePlatformUser record);

    CerePlatformUser selectByPrimaryKey(Long platformUserId);

    int updateByPrimaryKeySelective(CerePlatformUser record);

    int updateByPrimaryKey(CerePlatformUser record);

    CerePlatformUser findByUserName(@org.apache.ibatis.annotations.Param("userName") String userName);

    CerePlatformUser findByPhone(@org.apache.ibatis.annotations.Param("phone") String phone);

    PlatformUser getById(@Param("platformUserId") Long platformUserId);

    List<PlatformUser> getAll(UserGetAllParam userParam);

    CerePlatformUser findByToken(@org.apache.ibatis.annotations.Param("token") String token);

    void updateToken(CerePlatformUser cerePlatformUser);

    CerePlatformUser checkUserNameOrPhone(@org.apache.ibatis.annotations.Param("username") String username, @org.apache.ibatis.annotations.Param("phone") String phone, @Param("platformUserId") Long platformUserId);

}
