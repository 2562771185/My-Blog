package com.jhzz.myblog.domain.vo;

import com.jhzz.myblog.domain.ArticleBody;
import com.jhzz.myblog.domain.SysUser;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/3
 * \* Time: 11:45
 * \* Description:
 * \
 */
@Data
public class ArticleEditVo {
    //文章主体
    private ArticleBody articleBody;
    //文章标题
    private String title;
    private String articleCover;
    //文章作者信息
    private String authorAvatar;
    private String nickname;
    private String account;

    /**
     * 简介
     */
    private String summary;

}
