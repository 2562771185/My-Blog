package com.jhzz.myblog.common;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 18:02
 * \* Description:
 * \
 */
public class Constant {
    /**
     * 默认分页大小
     */
    public static final Long DEFAULT_PAGE_SIZE = 10L;
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "codes:";
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_USER = "bloguser:";

    /**
     * 加密盐
      */
    public static final String SLAT = "345323Jzhz!@$*(";


}
