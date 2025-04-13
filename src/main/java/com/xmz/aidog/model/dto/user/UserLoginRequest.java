package com.xmz.aidog.model.dto.user;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/13
 * Time: 21:04
 * Description: No Description
 */
@Data
public class UserLoginRequest {
    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;
}
