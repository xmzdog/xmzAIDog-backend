package com.xmz.aidog.controller;

import com.xmz.aidog.common.BaseResponse;
import com.xmz.aidog.model.dto.user.UserRegisterRequest;
import com.xmz.aidog.service.UserService;
import com.xmz.aidog.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 13:21
 * Description: No Description
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册账号
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        Long register = userService.register(userRegisterRequest);
        return ResultUtils.sucess(register);
    }

}
