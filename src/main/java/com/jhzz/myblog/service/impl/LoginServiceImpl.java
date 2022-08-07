package com.jhzz.myblog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.exception.BlogException;
import com.jhzz.myblog.mapper.SysUserMapper;
import com.jhzz.myblog.service.LoginService;
import com.jhzz.myblog.util.JwtUtil;
import com.jhzz.myblog.util.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/6
 * \* Time: 16:08
 * \* Description:
 * \
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(LoginParam loginParam) {
        /**
         * 校验验证码，是否正确，是否过期
         */
        String uuid = loginParam.getUuid();
        String key = loginParam.getCaptchaKey();
        if (StrUtil.isAllNotBlank(uuid, key)) {
            String code = redisCache.getCacheObject(Constant.CAPTCHA_CODE_KEY + uuid);
            //删除掉已经使用的验证码
            redisCache.deleteObject(Constant.CAPTCHA_CODE_KEY + uuid);
            if (!StrUtil.equalsIgnoreCase(code, key)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR, AppHttpCodeEnum.CODE_ERROR.getMsg());
            }
        }
        /**
         * 验证用户名和密码
         */
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, loginParam.getAccount()));
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(user)) {
            throw new BlogException(500,"登录失败");
        }
        //todo 解密密码验证
        String hex = DigestUtils.md5Hex(loginParam.getPassword() + Constant.SLAT);
        if (!StrUtil.equals(hex,loginParam.getPassword())){
            return ResponseResult.errorResult(500,"用户名或密码错误!");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        String account = user.getAccount();

        String jwt = JwtUtil.createJWT(account);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", jwt);
        //把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject(Constant.LOGIN_USER + account, user,2, TimeUnit.HOURS);
        return ResponseResult.okResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //删除redis中的token todo

        return null;
    }

    @Override
    public ResponseResult checkToken(String token) {
        log.info("token:{}",token);
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String userAccount = claims.getSubject();
            if (StrUtil.isBlank(userAccount)){
                //解析token失败
                throw new BlogException(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(),AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
            }
            SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, userAccount));
            if (user == null){
                throw new BlogException(AppHttpCodeEnum.NOT_USER.getCode(),AppHttpCodeEnum.NOT_USER.getMsg());
            }
            HashMap<String, Object> map = new HashMap<>(3);
            map.put("account",user.getAccount());
            map.put("nickname",user.getNickname());
            map.put("avatar",user.getAvatar());
            return ResponseResult.okResult(map);
        } catch (Exception e) {
            log.error("解析token出错！");
            e.printStackTrace();
        }
        return ResponseResult.errorResult();
    }
}
