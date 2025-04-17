package com.xmz.xmzaidogbiz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xmz.xmzaidogbiz.model.VO.LoginUserVo;
import com.xmz.xmzaidogbiz.model.dto.user.UserInfoRequset;
import com.xmz.xmzaidogbiz.model.dto.user.UserLoginRequest;
import com.xmz.xmzaidogbiz.model.dto.user.UserRegisterRequest;
import com.xmz.xmzaidogbiz.model.entity.User;
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
