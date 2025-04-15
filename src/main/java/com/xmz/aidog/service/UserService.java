package com.xmz.aidog.service;

import com.xmz.aidog.model.VO.LoginUserVo;
import com.xmz.aidog.model.dto.user.UserInfoRequset;
import com.xmz.aidog.model.dto.user.UserLoginRequest;
import com.xmz.aidog.model.dto.user.UserRegisterRequest;
import com.xmz.aidog.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-04-10 12:59:56
*/
public interface UserService extends IService<User> {
    Long register(UserRegisterRequest userRegisterRequest);

    LoginUserVo userLogin(UserLoginRequest userLoginRequest , HttpServletRequest request);

    LoginUserVo getLoginUser(HttpServletRequest httpServletRequest);

    Boolean userlogout(HttpServletRequest httpServletRequest);

    LoginUserVo uploadAvatar(MultipartFile file,HttpServletRequest httpServletRequest);

    LoginUserVo updateUserInfo(UserInfoRequset userInfoRequset, HttpServletRequest request);
}
