package com.jhzz.myblog.domain.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/2
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Data
public class LoginParam {
    @NotNull
    private String account;
    @NotNull
    private String password;
    /**
     * 验证码
     */
    @NotNull
    private String captchaKey;
    /**
     * 验证码对应id
     */
    @NotNull
    private String uuid;

    public boolean checkAllParams(){
        return StrUtil.isAllNotBlank(account,password,captchaKey,uuid);
    }
}
