package com.jhzz.myblog.domain.vo;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/3
 * \* Time: 12:00
 * \* Description:
 * \
 */
@Data
public class ArticleUpdateVo {
    private Long id;
    private Long bodyId;
    private String title;
    private String content;
    private String cover;
    private String summary;
}
