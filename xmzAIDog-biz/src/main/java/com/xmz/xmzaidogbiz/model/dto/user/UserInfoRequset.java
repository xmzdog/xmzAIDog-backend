package com.xmz.xmzaidogbiz.model.dto.user;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/14
 * Time: 0:44
 * Description: 用户账号设置请求体
 */
@Data
public class UserInfoRequset {

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

}
