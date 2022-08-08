package com.jhzz.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jhzz.myblog.aop.LogAnnotation;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.RegisterParam;
import com.jhzz.myblog.domain.param.UserUpdateVo;
import com.jhzz.myblog.domain.param.VerifyParam;
import com.jhzz.myblog.domain.vo.UserVo;
import com.jhzz.myblog.service.LoginService;
import com.jhzz.myblog.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("根据用户名获取用户信息")
    @GetMapping("/profile")
    public ResponseResult getUserInfo(@RequestParam("account") String account) {
        SysUser info = userService.getAuthorInfoByAccount(account);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(info, userVo);
        return ResponseResult.okResult(userVo);
    }

    @PostMapping("/register")
    @ApiOperation("注册新用户")
    @LogAnnotation(module = "用户", operation = "注册")
    public ResponseResult register(@RequestBody RegisterParam register) {
        return userService.register(register);

    }

    @PostMapping("/login")
    @ApiOperation("登录")
    @LogAnnotation(module = "用户", operation = "登录")
    public ResponseResult login(@RequestBody LoginParam loginParam) {
        return loginService.login(loginParam);
    }

    @GetMapping("/checkToken")
    @ApiOperation("检查token是否合法")
    @LogAnnotation(module = "用户", operation = "检查token")
    public ResponseResult checkToken(@RequestHeader("Authorization") String token) {
        return loginService.checkToken(token);
    }

    @GetMapping("/delToken")
    @ApiOperation("删除token")
    @LogAnnotation(module = "用户", operation = "删除token")
    public ResponseResult delToken(@RequestParam("token") String token) {
        return loginService.delToken(token);
    }

    @GetMapping("/token")
    @ApiOperation("刷新token")
    public ResponseResult refreshToken(@RequestHeader("Authorization") String token) {
        return loginService.refreshToken(token);
    }

    @PostMapping("/verification")
    public ResponseResult verification(@RequestBody VerifyParam data) {
        return userService.verification(data);
    }

    @PostMapping("/updateInfo")
    @ApiOperation("更新用户信息")
    @LogAnnotation(module = "用户", operation = "更新用户资料")
    public ResponseResult updateInfo(@RequestBody UserUpdateVo vo,@RequestHeader("Authorization") String token) {
        ResponseResult checkToken = loginService.checkToken(token);
        if (!checkToken.getCode().equals(200)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        return userService.updateInfo(vo);

    }

}
