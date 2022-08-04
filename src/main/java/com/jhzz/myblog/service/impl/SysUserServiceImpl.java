package com.jhzz.myblog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.LoginParam;
import com.jhzz.myblog.domain.param.RegisterParam;
import com.jhzz.myblog.exception.BlogException;
import com.jhzz.myblog.service.SysUserService;
import com.jhzz.myblog.mapper.SysUserMapper;
import com.jhzz.myblog.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Huanzhi
 * @description 针对表【ms_sys_user】的数据库操作Service实现
 * @createDate 2022-07-31 18:31:49
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {
    @Autowired
    private RedisCache redisCache;

    @Override
    public SysUser getAuthorInfoById(String account) {
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
            //redisCache.deleteObject(Constant.CAPTCHA_CODE_KEY + uuid);
            if (!StrUtil.equalsIgnoreCase(code, key)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR, AppHttpCodeEnum.CODE_ERROR.getMsg());
            }
        }

        // 检查注册参数
        boolean checkAllParams = register.checkAllParams();
        if (!checkAllParams) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_ERROR, AppHttpCodeEnum.PARAM_ERROR.getMsg());
        }
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
        user.setMobilePhoneNumber("");
        if (StrUtil.isNotBlank(register.getNickname())) {
            user.setNickname(register.getNickname());
        }
        //todo 加密密码
        if (StrUtil.isNotBlank(register.getNickname())) {
            user.setPassword(register.getPassword());
        }
        user.setSalt("");
        user.setStatus("1");
        //保存
        this.save(user);
        return ResponseResult.okResult("注册成功！");
    }

    @Override
    public ResponseResult login(LoginParam loginParam) {
        if (!loginParam.checkAllParams()) {
            throw new BlogException(AppHttpCodeEnum.PARAM_ERROR.getCode(), AppHttpCodeEnum.PARAM_ERROR.getMsg());
        }
        /**
         * 对比数据库信息
         */
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, loginParam.getAccount()));
        if (user != null && StrUtil.equals(loginParam.getPassword(), user.getPassword())) {
            //todo 返回token
            String token = "fasfasdfasd23423fasdf";
            HashMap<String, Object> map = new HashMap<>(1);
            map.put("token", token);
            map.put("userAvatar", user.getAvatar());
            map.put("nickname", user.getNickname());
            map.put("account", user.getAccount());
            return ResponseResult.okResult(map);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), AppHttpCodeEnum.LOGIN_ERROR.getMsg());

    }
}




