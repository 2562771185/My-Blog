package com.jhzz.myblog.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/8
 * \* Time: 14:17
 * \* Description:
 * \
 */
@Data
public class UserUpdateVo {
    @NotBlank(message = "用户名为空")
    private String account;
    private String avatar;
    private String bannerImg;
    private String nickname;
}
