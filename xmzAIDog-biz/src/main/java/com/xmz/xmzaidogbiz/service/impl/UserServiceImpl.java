package com.xmz.xmzaidogbiz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmz.xmzaidogbiz.Constant.UserConstant;
import com.xmz.xmzaidogbiz.exception.BusinessException;
import com.xmz.xmzaidogbiz.mapper.UserMapper;
import com.xmz.xmzaidogbiz.model.VO.LoginUserVo;
import com.xmz.xmzaidogbiz.model.dto.user.UserInfoRequset;
import com.xmz.xmzaidogbiz.model.dto.user.UserLoginRequest;
import com.xmz.xmzaidogbiz.model.dto.user.UserRegisterRequest;
import com.xmz.xmzaidogbiz.model.entity.User;
import com.xmz.xmzaidogbiz.model.enums.ErrorCode;
import com.xmz.xmzaidogbiz.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Administrator
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-04-10 12:59:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "xmz";

    @Override
    public Long register(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        String useraccount = userRegisterRequest.getUseraccount();
        String userpassword = userRegisterRequest.getUserpassword();
        String checkpassword = userRegisterRequest.getCheckpassword();
        if (StringUtils.isAnyBlank(useraccount, userpassword, checkpassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (useraccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能小于4位数");
        }
        if (userpassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能小于8位数");
        }
        if (!userpassword.equals(checkpassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和确认密码不相同");
        }

        /**
         * String.intern() 会返回字符串常量池中的唯一引用
         * 使用synchronized 保证在多线程环境下，不同线程在注册相同账号（useraccount）时不会并发执行，避免数据库中插入重复账号的情况。
         */
        synchronized (useraccount.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("useraccount", useraccount);
            Long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "当前用户已存在");
            }
            String encryptpassword = DigestUtils.md5DigestAsHex((SALT + userpassword).getBytes());
            User user = new User();
            user.setUserAccount(useraccount);
            user.setUserPassword(encryptpassword);
            boolean save = this.save(user);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库异常");
            }
            return user.getId();
        }

    }

    @Override
    public LoginUserVo userLogin(UserLoginRequest userLoginRequest, HttpServletRequest httpRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String useraccount = userLoginRequest.getUseraccount();
        String userpassword = userLoginRequest.getUserpassword();
        if (StringUtils.isAnyBlank(useraccount, userpassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("useraccount", useraccount);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"用户账号不存在");
        }
        String encryptpassword = DigestUtils.md5DigestAsHex((SALT + userpassword).getBytes());
        if (!encryptpassword.equals(user.getUserPassword())) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }
        // 使用session 记录登录状态
        httpRequest.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return this.User2LoginUserVo(user);
    }


    @Override
    public LoginUserVo getLoginUser(HttpServletRequest httpServletRequest) {
        User loginuser = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return this.User2LoginUserVo(loginuser);
    }


    @Override
    public Boolean userlogout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVo uploadAvatar(MultipartFile file,HttpServletRequest httpServletRequest) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!".jpg".equalsIgnoreCase(suffix) && !".png".equalsIgnoreCase(suffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"只支持 JPG 或 PNG 格式的图片");
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"头像文件不能大于2M");
        }
        User user = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"登录已过期");
        }
        Long id = user.getId();
        String avatar;
        try {
             avatar = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        user.setUserAvatar(avatar);
        int update = this.baseMapper.update(user, queryWrapper);
        if (update != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"头像上传失败");
        }
        return this.User2LoginUserVo(user);
    }

    @Override
    public LoginUserVo updateUserInfo(UserInfoRequset userInfoRequset, HttpServletRequest request) {
        if (userInfoRequset == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userInfoRequset.getUsername();
        String email = userInfoRequset.getEmail();
        String phone = userInfoRequset.getPhone();
        if (StringUtils.isAnyBlank(username, email, phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空");
        }
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        Long id = user.getId();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        user.setUserName(username);
        user.setEmail(email);
        user.setPhone(phone);
        int update = this.baseMapper.update(user, queryWrapper);
        if (update != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return this.User2LoginUserVo(user);
    }

    /**
     * 将User对象转成LoginUserVo
     * @param user
     * @return
     */
    public LoginUserVo User2LoginUserVo(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(user,loginUserVo);
        return loginUserVo;
    }


}




