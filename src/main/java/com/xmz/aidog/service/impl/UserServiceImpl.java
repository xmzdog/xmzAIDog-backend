package com.xmz.aidog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmz.aidog.exception.BusinessException;
import com.xmz.aidog.model.dto.user.UserRegisterRequest;
import com.xmz.aidog.model.entity.User;
import com.xmz.aidog.model.enums.ErrorCode;
import com.xmz.aidog.service.UserService;
import com.xmz.aidog.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

        synchronized (useraccount.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("useraccount", useraccount);
            Long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "当前用户已存在");
            }
            String encryptpassword = DigestUtils.md5DigestAsHex((SALT + userpassword).getBytes());
            User user = new User();
            user.setUseraccount(useraccount);
            user.setUserpassword(encryptpassword);
            boolean save = this.save(user);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库异常");
            }
            return user.getId();
        }

    }
}




