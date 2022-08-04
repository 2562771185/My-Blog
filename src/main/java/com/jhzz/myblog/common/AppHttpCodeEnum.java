package com.jhzz.myblog.common;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/5/9
 * \* Time: 16:42
 * \* Description:
 * \
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    NOT_USER(599,"用户不存在"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    CODE_ERROR(555,"验证码错误"),
    PARAM_ERROR(566,"入参错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"), CONTENT_NOT_NULL(506, "评论内容为空"),
    FILE_TYPE_ERROR(506,"文件类型错误" ), USERNAME_NOT_NULL(508, "用户名为空"),
    PASSWORD_NOT_NULL(509,"密码为空" ), EMAIL_NOT_NULL(510, "邮箱为空"),
    NICKNAME_NOT_NULL(511,"昵称为空" ), NICKNAME_EXIST(512, "昵称已存在");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
