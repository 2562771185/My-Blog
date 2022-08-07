package com.jhzz.myblog.service;

import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.VerifyParam;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/6
 * \* Time: 16:08
 * \* Description:
 * \
 */
public interface LoginService {
    ResponseResult login(LoginParam loginParam);

    ResponseResult logout();

    ResponseResult checkToken(String token);

    ResponseResult refreshToken(String token);

    ResponseResult delToken(String token);

}
