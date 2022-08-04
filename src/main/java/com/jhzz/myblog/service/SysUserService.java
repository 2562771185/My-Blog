package com.jhzz.myblog.service;

import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.RegisterParam;

/**
* @author Huanzhi
* @description 针对表【ms_sys_user】的数据库操作Service
* @createDate 2022-07-31 18:31:49
*/
public interface SysUserService extends IService<SysUser> {

    SysUser getAuthorInfoById(String authorId);

    ResponseResult register(RegisterParam register);

    ResponseResult login(LoginParam loginParam);

}
