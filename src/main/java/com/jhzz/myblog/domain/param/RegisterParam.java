package com.jhzz.myblog.domain.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/2
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Data
public class RegisterParam {
    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String email;
    /**
     * 验证码
     */
    private String captchaKey;
    /**
     * 验证码对应id
     */
    private String uuid;
}
