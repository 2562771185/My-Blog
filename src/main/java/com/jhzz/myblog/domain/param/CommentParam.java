package com.jhzz.myblog.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/7
 * \* Time: 14:37
 * \* Description:
 * \
 */
@Data
public class CommentParam {
    @NotNull
    private String content;
    @NotNull
    private Long authorId;
    @NotNull

    private Long articleId;

    private Long commentId;

}
