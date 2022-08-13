package com.jhzz.myblog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.RegisterParam;
import com.jhzz.myblog.domain.param.UserUpdateVo;
import com.jhzz.myblog.domain.param.VerifyParam;
import com.jhzz.myblog.exception.BlogException;
import com.jhzz.myblog.service.SysUserService;
import com.jhzz.myblog.mapper.SysUserMapper;
import com.jhzz.myblog.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Huanzhi
 * @description 针对表【ms_sys_user】的数据库操作Service实现
 * @createDate 2022-07-31 18:31:49
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {
    @Autowired
    private RedisCache redisCache;

    @Override
    public SysUser getAuthorInfoByAccount(String account) {
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account));
        return user;
    }

    @Override
    public ResponseResult register(RegisterParam register) {
        /**
         * 校验验证码，是否正确，是否过期
         */
        String uuid = register.getUuid();
        String key = register.getCaptchaKey();
        if (StrUtil.isAllNotBlank(uuid, key)) {
            String code = redisCache.getCacheObject(Constant.CAPTCHA_CODE_KEY + uuid);
            //删除掉已经使用的验证码
            redisCache.deleteObject(Constant.CAPTCHA_CODE_KEY + uuid);
            if (!StrUtil.equalsIgnoreCase(code, key)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR, AppHttpCodeEnum.CODE_ERROR.getMsg());
            }
        }

        // 检查注册参数
//        if (!register.checkAllParams()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_ERROR, AppHttpCodeEnum.PARAM_ERROR.getMsg());
//        }
        //用户名不能重复
        String account = register.getAccount();
        if (StrUtil.isNotBlank(account)) {
            List<SysUser> users = this.list(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account));
            if (users != null && users.size() > 0) {
                return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST, AppHttpCodeEnum.USERNAME_EXIST.getMsg());
            }
        }
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setAdmin(false);
        user.setAvatar("https://ithz.oss-cn-shenzhen.aliyuncs.com/4.gif");
        user.setCreateDate(new DateTime().getTime());
        user.setDeleted(false);
        if (StrUtil.isNotBlank(register.getEmail())) {
            user.setEmail(register.getEmail());
        }
        user.setLastLogin(new DateTime().getTime());
        user.setBannerImg("");
        if (StrUtil.isNotBlank(register.getNickname())) {
            user.setNickname(register.getNickname());
        }
        //加密密码

        if (StrUtil.isNotBlank(register.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(register.getPassword() + Constant.SLAT));
        }
        user.setSalt("");
        user.setStatus("1");
        //保存
        this.save(user);
        return ResponseResult.okResult("注册成功！");
    }

    @Override
    public ResponseResult verification(VerifyParam data) {
        String type = data.getType();
        String account = data.getAccount();
        log.info("account:{}", account);
        log.info("type:{}", type);
        if (!StrUtil.isAllNotBlank(type, account)) {
            return ResponseResult.errorResult(500, "用户名为空!");
        }
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account));
        log.info("user:{}", user);
        if (user == null) {
            return ResponseResult.errorResult(500, "用户名不存在！");
        }
        String email = user.getEmail();
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("email", email);
        map.put("status", "ok");
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult updateInfo(UserUpdateVo vo) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getAccount, vo.getAccount());
        if (StrUtil.isNotBlank(vo.getNickname())) {
            wrapper.set(SysUser::getNickname, vo.getNickname());
        }
        if (StrUtil.isNotBlank(vo.getAvatar())) {
            wrapper.set(SysUser::getAvatar, vo.getAvatar());
        }
        if (StrUtil.isNotBlank(vo.getBannerImg())) {
            wrapper.set(SysUser::getBannerImg, vo.getBannerImg());
        }

        boolean b = this.update(wrapper);
        if (b){
            //更新redis
            SysUser user = this.getAuthorInfoByAccount(vo.getAccount());
            //删除之前的旧数据
            redisCache.deleteObject(Constant.LOGIN_USER + user.getAccount());
            //把完整的用户信息存入redis  userid作为key
            redisCache.setCacheObject(Constant.LOGIN_USER + user.getAccount(), JSON.toJSONString(user), 1, TimeUnit.HOURS);
            log.info("更新redis中的数据成功！");
            return ResponseResult.okResult(200,"更新资料成功！");
        }
        return ResponseResult.errorResult();
    }


}




