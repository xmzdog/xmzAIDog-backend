package com.xmz.aidog.service;

import com.xmz.aidog.model.dto.user.UserRegisterRequest;
import com.xmz.aidog.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-04-10 12:59:56
*/
public interface UserService extends IService<User> {
    Long register(UserRegisterRequest userRegisterRequest);
}
