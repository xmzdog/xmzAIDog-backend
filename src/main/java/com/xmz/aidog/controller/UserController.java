package com.xmz.aidog.controller;

import com.xmz.aidog.common.BaseResponse;
import com.xmz.aidog.model.VO.LoginUserVo;
import com.xmz.aidog.model.dto.user.UserLoginRequest;
import com.xmz.aidog.model.dto.user.UserRegisterRequest;
import com.xmz.aidog.service.UserService;
import com.xmz.aidog.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    /**
     *
     * @param userLoginRequest  用户登录请求体
     * @param httpServletRequest
     * @return
     * 这个方法中的 HttpServletRequest request 是 Java Web 开发中用于获取HTTP 请求信息的对象，它代表了客户端发来的请求，包含了请求的全部信息，比如：
     * 请求头（headers）
     * 请求参数（parameters）
     * 会话信息（session）
     * Cookie
     * 请求路径
     * 请求方式（GET/POST）
     * 在登录逻辑里，request 主要用于获取或操作 Session 对象，实现登录用户的“记住状态”。
     * Web 应用是 无状态的，每个请求都是独立的。你登录一次以后，服务器并不会记得你是谁。
     * 所以我们需要通过 Session 或 Token 来维持登录状态：
     * 技术方案	原理
     * Session 登录	登录成功后，服务器生成 Session，并把用户信息存入 Session 中，客户端用 Cookie 带上 JSESSIONID。
     * Token 登录（如 JWT）	登录后生成 token，前端每次请求时带上，后端解析 token 获取用户信息。
     */
    @PostMapping("login")
    public BaseResponse<LoginUserVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        LoginUserVo loginUserVo = userService.userLogin(userLoginRequest, httpServletRequest);
        return ResultUtils.sucess(loginUserVo);
    }

    /**
     * 获取当前登录用户
     * @param httpServletRequest
     * @return
     */
    @GetMapping("getloginuser")
    public BaseResponse<LoginUserVo> getLoginUser(HttpServletRequest httpServletRequest){
        LoginUserVo loginUserVo = userService.getLoginUser(httpServletRequest);
        return ResultUtils.sucess(loginUserVo);
    }

}
