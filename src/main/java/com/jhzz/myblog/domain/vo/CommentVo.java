package com.jhzz.myblog.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/1
 * \* Time: 17:19
 * \* Description:
 * \
 */
@Data
public class CommentVo implements Serializable {

    private Long id;
    private String content;
    private Long createDate;
    private Integer articleId;
    private Long authorId;
    private Long parentId;
    private String level;
    private String authorAvatar;
    private String authorNickname;
    private List<CommentVo> children;



    private static final long serialVersionUID = 1L;

}
