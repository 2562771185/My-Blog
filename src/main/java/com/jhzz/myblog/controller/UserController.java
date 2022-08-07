package com.jhzz.myblog.controller;

import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.RegisterParam;
import com.jhzz.myblog.service.LoginService;
import com.jhzz.myblog.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 19:06
 * \* Description:
 * \
 */
@Api
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private LoginService loginService;
    @GetMapping("/profile")
    public ResponseResult getUserInfo(@RequestParam("account")String account){
        SysUser info = userService.getAuthorInfoById(account);
        return ResponseResult.okResult(info);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterParam register){
        return userService.register(register);

    }
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
    @GetMapping("/checkToken")
    public ResponseResult checkToken(@RequestParam("token") String token){
        return loginService.checkToken(token);
    }

}
