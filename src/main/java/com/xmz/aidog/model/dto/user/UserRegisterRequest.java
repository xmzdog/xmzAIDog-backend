package com.xmz.aidog.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 17:13
 * Description: 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;

    /**
     * 确认密码
     */
    private String checkpassword;
}
