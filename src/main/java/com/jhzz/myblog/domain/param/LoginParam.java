package com.jhzz.myblog.domain.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captchaKey;
    /**
     * 验证码对应id
     */
    @NotBlank(message = "验证码id不能为空")
    private String uuid;

}
