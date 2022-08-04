package com.jhzz.myblog.domain.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

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

    private String account;
    private String password;
    /**
     * 验证码
     */
    private String captchaKey;
    /**
     * 验证码对应id
     */
    private String uuid;
    public boolean checkAllParams(){
        return StrUtil.isAllNotBlank(account,password,captchaKey,uuid);
    }
}
