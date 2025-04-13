package com.xmz.aidog.model.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/13
 * Time: 21:00
 * Description: 已登陆用户视图类
 * LoginUserVO 是“前后端解耦 + 安全脱敏 + 接口规范 + 维护便利”的完美实践
 */
@Data
public class LoginUserVo implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
}
